package pl.allegro.atm.workshop.rx;


import rx.Observable;
import rx.functions.Action1;

import javax.ws.rs.container.AsyncResponse;

public class RxJerseyHelpers {

    private RxJerseyHelpers() {
    }


    public static <T> void asyncResponse(Observable<T> observable, final AsyncResponse response){
        observable.subscribe( new Action1<T>() {
            @Override
            public void call(T t) {
                response.resume(t);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                response.resume(throwable);
            }
        });
    }
}
