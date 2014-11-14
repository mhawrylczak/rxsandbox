package pl.allegro.atm.workshop.rx;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


/**
 * Created by wladyslaw.hawrylczak on 2014-11-14.
 */
public class ErrorTests {
    private boolean raiseError = false;

    @Test
    public void testRetryWhen() throws InterruptedException {
        raiseError = true;

        TestSubscriber<String> ts = new TestSubscriber<>(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError "+ e);
            }

            @Override
            public void onNext(String o) {
                System.out.println("onNext "+ o);
            }
        });

        createFailingObservable()
                .map(str -> str + str)
                .retryWhen(errors -> errors.flatMap(err -> {
                            if (err instanceof IllegalArgumentException){
                                raiseError = false;
                                return Observable.just(1);
                            }else{
                                return Observable.error(err);
                            }
                        })
                ).subscribe(ts);

        ts.awaitTerminalEvent();
    }

    private Observable<String> createFailingObservable() {
        return Observable.create((Subscriber<? super String> subscriber) -> {
            System.out.println("Emitting 1");
            subscriber.onNext("1");
            System.out.println("Emitting 2");
            subscriber.onNext("2");
            System.out.println("Emitting 3");
            subscriber.onNext("3");
            if (raiseError) {
                System.out.println("Emitting exception");
                subscriber.onError(new IllegalArgumentException("bad token"));
            } else {
                System.out.println("Emitting 4");
                subscriber.onNext("4");
                System.out.println("Emitting End");
                subscriber.onCompleted();
            }

        });
    }


    @Test
    public void testSchedulingNotificationHandler() {
        @SuppressWarnings("unchecked")
        Observer<String> observer = mock(Observer.class);
        int NUM_RETRIES = 2;
        Observable<String> origin = Observable.create(new FuncWithErrors(NUM_RETRIES));
        TestSubscriber<String> subscriber = new TestSubscriber<String>(observer);
        origin.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.observeOn(Schedulers.computation()).map(new Func1<Throwable, Notification>() {
                    @Override
                    public Notification call(Throwable throwable) {
                        return Notification.createOnNext(null);
                    }
                }).startWith(Notification.createOnNext(null));
            }
        }).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        InOrder inOrder = Mockito.inOrder(observer);
        // should show 3 attempts
        inOrder.verify(observer, times(1 + NUM_RETRIES)).onNext("beginningEveryTime");
        // should have no errors
        inOrder.verify(observer, never()).onError(any(Throwable.class));
        // should have a single success
        inOrder.verify(observer, times(1)).onNext("onSuccessOnly");
        // should have a single successful onCompleted
        inOrder.verify(observer, times(1)).onCompleted();
        inOrder.verifyNoMoreInteractions();
    }

    public static class FuncWithErrors implements Observable.OnSubscribe<String> {

        private final int numFailures;
        private final AtomicInteger count = new AtomicInteger(0);

        FuncWithErrors(int count) {
            this.numFailures = count;
        }

        @Override
        public void call(Subscriber<? super String> o) {
            o.onNext("beginningEveryTime");
            if (count.getAndIncrement() < numFailures) {
                o.onError(new RuntimeException("forced failure: " + count.get()));
            } else {
                o.onNext("onSuccessOnly");
                o.onCompleted();
            }
        }
    }
}
