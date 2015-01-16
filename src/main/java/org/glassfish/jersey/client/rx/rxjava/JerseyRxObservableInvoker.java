/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.jersey.client.rx.rxjava;

import java.util.concurrent.ExecutorService;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.rx.spi.AbstractRxInvoker;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;


final class JerseyRxObservableInvoker extends AbstractRxInvoker<Observable> implements RxObservableInvoker {

    JerseyRxObservableInvoker(final Invocation.Builder builder, final ExecutorService executor) {
        super(builder, executor);
    }

    @Override
    public <T> Observable<T> method(final String name, final Entity<?> entity, final Class<T> responseType) {
        if (getExecutorService() == null) {
            // Invoke as async JAX-RS client request.
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(final Subscriber<? super T> subscriber) {
                    final CompositeSubscription parent = new CompositeSubscription();
                    subscriber.add(parent);

                    // return a Subscription that wraps the Future to make sure it can be cancelled
                    parent.add(Subscriptions.from(getBuilder().async().method(name, entity, new InvocationCallback<Response>() {
                        @Override
                        public void completed(final Response response) {
                            if (!subscriber.isUnsubscribed()) {
                                if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                                    try {
                                        final T entity = responseType.isAssignableFrom(response.getClass())
                                                ? responseType.cast(response) : response.readEntity(responseType);

                                        if (!subscriber.isUnsubscribed()) {
                                            subscriber.onNext(entity);
                                        }
                                        if (!subscriber.isUnsubscribed()) {
                                            subscriber.onCompleted();
                                        }
                                    } catch (final Throwable throwable) {
                                        failed(throwable);
                                    }
                                }else{
                                    failed(convertToException(response));
                                }
                            }
                        }

                        @Override
                        public void failed(final Throwable throwable) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(throwable);
                            }
                        }
                    })));
                }
            });
        } else {
            // Invoke as sync JAX-RS client request and subscribe/observe on a scheduler initialized with executor service.
            final Scheduler scheduler = Schedulers.from(getExecutorService());

            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(final Subscriber<? super T> subscriber) {
                    if (!subscriber.isUnsubscribed()) {
                        try {
                            final T response = getBuilder().method(name, entity, responseType);

                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(response);
                            }
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onCompleted();
                            }
                        } catch (final Throwable throwable) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(throwable);
                            }
                        }
                    }
                }
            }).subscribeOn(scheduler).observeOn(scheduler);
        }
    }

    private ProcessingException convertToException(final Response response) {
        try {
            // Buffer and close entity input stream (if any) to prevent
            // leaking connections (see JERSEY-2157).
            response.bufferEntity();

            final WebApplicationException webAppException;
            final int statusCode = response.getStatus();
            final Response.Status status = Response.Status.fromStatusCode(statusCode);

            if (status == null) {
                final Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
                webAppException = createExceptionForFamily(response, statusFamily);
            } else switch (status) {
                case BAD_REQUEST:
                    webAppException = new BadRequestException(response);
                    break;
                case UNAUTHORIZED:
                    webAppException = new NotAuthorizedException(response);
                    break;
                case FORBIDDEN:
                    webAppException = new ForbiddenException(response);
                    break;
                case NOT_FOUND:
                    webAppException = new NotFoundException(response);
                    break;
                case METHOD_NOT_ALLOWED:
                    webAppException = new NotAllowedException(response);
                    break;
                case NOT_ACCEPTABLE:
                    webAppException = new NotAcceptableException(response);
                    break;
                case UNSUPPORTED_MEDIA_TYPE:
                    webAppException = new NotSupportedException(response);
                    break;
                case INTERNAL_SERVER_ERROR:
                    webAppException = new InternalServerErrorException(response);
                    break;
                case SERVICE_UNAVAILABLE:
                    webAppException = new ServiceUnavailableException(response);
                    break;
                default:
                    final Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
                    webAppException = createExceptionForFamily(response, statusFamily);
            }

            return new ProcessingException(webAppException);
        } catch (final Throwable t) {
            return new ProcessingException(LocalizationMessages.RESPONSE_TO_EXCEPTION_CONVERSION_FAILED(), t);
        }
    }

    private WebApplicationException createExceptionForFamily(final Response response, final Response.Status.Family statusFamily) {
        final WebApplicationException webAppException;
        switch (statusFamily) {
            case REDIRECTION:
                webAppException = new RedirectionException(response);
                break;
            case CLIENT_ERROR:
                webAppException = new ClientErrorException(response);
                break;
            case SERVER_ERROR:
                webAppException = new ServerErrorException(response);
                break;
            default:
                webAppException = new WebApplicationException(response);
        }
        return webAppException;
    }

    @Override
    public <T> Observable<T> method(final String name, final Entity<?> entity, final GenericType<T> responseType) {
        if (getExecutorService() == null) {
            // Invoke as async JAX-RS client request.
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(final Subscriber<? super T> subscriber) {
                    final CompositeSubscription parent = new CompositeSubscription();
                    subscriber.add(parent);

                    // return a Subscription that wraps the Future to make sure it can be cancelled
                    parent.add(Subscriptions.from(getBuilder().async().method(name, entity, new InvocationCallback<Response>() {
                        @Override
                        public void completed(final Response response) {
                            if (!subscriber.isUnsubscribed()) {
                                try {
                                    @SuppressWarnings("unchecked")
                                    final Class<T> clazz = (Class<T>) responseType.getRawType();
                                    final T entity = clazz.isAssignableFrom(response.getClass())
                                            ? clazz.cast(response) : response.readEntity(responseType);

                                    if (!subscriber.isUnsubscribed()) {
                                        subscriber.onNext(entity);
                                    }
                                    if (!subscriber.isUnsubscribed()) {
                                        subscriber.onCompleted();
                                    }
                                } catch (final Throwable throwable) {
                                    failed(throwable);
                                }
                            }
                        }

                        @Override
                        public void failed(final Throwable throwable) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(throwable);
                            }
                        }
                    })));
                }
            });
        } else {
            // Invoke as sync JAX-RS client request and subscribe/observe on a scheduler initialized with executor service.
            final Scheduler scheduler = Schedulers.from(getExecutorService());

            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(final Subscriber<? super T> subscriber) {
                    if (!subscriber.isUnsubscribed()) {
                        try {
                            final T response = getBuilder().method(name, entity, responseType);

                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(response);
                            }
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onCompleted();
                            }
                        } catch (final Throwable throwable) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(throwable);
                            }
                        }
                    }
                }
            }).subscribeOn(scheduler).observeOn(scheduler);
        }
    }
}
