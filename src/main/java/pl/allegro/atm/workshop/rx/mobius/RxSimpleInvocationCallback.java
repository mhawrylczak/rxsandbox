package pl.allegro.atm.workshop.rx.mobius;

import rx.Subscriber;

import javax.ws.rs.client.InvocationCallback;

class RxSimpleInvocationCallback<T> implements InvocationCallback<T> {
    private final Subscriber<? super T> subscriber;

    public RxSimpleInvocationCallback(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void completed(T t) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(t);
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
}
