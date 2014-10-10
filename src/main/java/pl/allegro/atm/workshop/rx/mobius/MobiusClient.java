package pl.allegro.atm.workshop.rx.mobius;


import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferDetails;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;
import pl.allegro.atm.workshop.rx.mobius.model.OAuthAccessTokenResponse;
import pl.allegro.atm.workshop.rx.mobius.model.OffersFacadeV2Request;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Component
public class MobiusClient {

    @Inject
    @Named("mobiusWebTarget")
    private WebTarget webTarget;

    @Inject
    @Named("mobiusAuthWebTarget")
    private WebTarget authWebTarget;

    @Value("${mobiusKeyUser}")
    private String keyUser;

    @Value("${mobiusKeyPassword}")
    private String keyPassword;

    private Subject<String, String> tokenSubject;

    public Observable<AllegroOfferDetails> findOffer(final String offerId) {
        Observable<Observable<AllegroOfferDetails>> ooOffer = getToken().map(new Func1<String, Observable<AllegroOfferDetails>>() {
            @Override
            public Observable<AllegroOfferDetails> call(String token) {
                return getAllegroOfferDetailsObservable(offerId, token);
            }
        });
        return Observable.merge(ooOffer);
    }

    private Observable<AllegroOfferDetails> getAllegroOfferDetailsObservable(final String offerId, final String token) {
        return Observable.create(new Observable.OnSubscribe<AllegroOfferDetails>() {
            @Override
            public void call(final Subscriber<? super AllegroOfferDetails> subscriber) {
                findOfferInvocationBuilder(offerId, token).async().get(new InvocationCallback<AllegroOfferDetails>() {
                    @Override
                    public void completed(AllegroOfferDetails o) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(o);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(throwable);
                            subscriber.unsubscribe();
                        }
                    }
                });
            }
        });
    }

    private Invocation.Builder findOfferInvocationBuilder(String offerId, String token) {
        return webTarget
                .path("/v2/allegro/offers")
                .path(offerId)
                .queryParam("access_token", token)
                .request(MediaType.APPLICATION_JSON_TYPE);
    }

    public Observable<DoGetItemsListCollection> searchOffers(String searchString) {
        final OffersFacadeV2Request request = new OffersFacadeV2Request();
        request.setSearchString(searchString);
        request.setLimit(5);

        Observable<Observable<DoGetItemsListCollection>> ooItems = getToken().map(new Func1<String, Observable<DoGetItemsListCollection>>() {
            @Override
            public Observable<DoGetItemsListCollection> call(final String token) {
                return getDoGetItemsListCollectionObservable(token, request);
            }
        });

        return Observable.merge(ooItems);

    }

    private Observable<DoGetItemsListCollection> getDoGetItemsListCollectionObservable(final String token, final OffersFacadeV2Request request) {
        return Observable.create(new Observable.OnSubscribe<DoGetItemsListCollection>() {
            @Override
            public void call(final Subscriber<? super DoGetItemsListCollection> subscriber) {
                searchInvocationBuilder(token).async().post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), new InvocationCallback<DoGetItemsListCollection>() {
                    @Override
                    public void completed(DoGetItemsListCollection o) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(o);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(throwable);
                            subscriber.unsubscribe();
                        }
                    }
                });
            }
        });
    }

    private Invocation.Builder searchInvocationBuilder(String token) {
        return webTarget
                .path("/v2/allegro/offers")
                .queryParam("access_token", token)
                .request(MediaType.APPLICATION_JSON_TYPE);
    }


    public synchronized Observable<String> getToken() {
        if (tokenSubject == null) {
            tokenSubject = AsyncSubject.create();
            createToken().subscribe(tokenSubject);
        }
        return tokenSubject.asObservable();
    }

    private Observable<String> createToken() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                createTokenInvocationBuilder().async().get(new InvocationCallback<OAuthAccessTokenResponse>() {
                    @Override
                    public void completed(OAuthAccessTokenResponse o) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(o.getAccess_token());
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(throwable);
                            subscriber.unsubscribe();
                        }
                    }
                });
            }
        });
    }

    private Invocation.Builder createTokenInvocationBuilder() {
        return authWebTarget
                .path("oauth/auth")
                .queryParam("grant_type", "client_credentials")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, keyUser)
                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, keyPassword);
    }

}
