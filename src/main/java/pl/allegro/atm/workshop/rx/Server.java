package pl.allegro.atm.workshop.rx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.tech.bootstrap.core.infrastructure.RestService;
import rx.plugins.DebugHook;
import rx.plugins.DebugNotification;
import rx.plugins.DebugNotificationListener;
import rx.plugins.RxJavaPlugins;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    public static final void main(String[] args) {
        final DebugHook hook = new DebugHook(new DebugNotificationListener<Object>(){
            @Override
            public <T> T onNext(DebugNotification<T> n) {
                return super.onNext(n);
            }

            @Override
            public <T> Object start(DebugNotification<T> n) {
                return super.start(n);
            }

            @Override
            public void complete(Object context) {
                super.complete(context);
            }

            @Override
            public void error(Object context, Throwable e) {
                LOGGER.info("error context {}, err {}", e);
                super.error(context, e);
            }
        });
        RxJavaPlugins.getInstance().registerObservableExecutionHook(hook);
        RestService.main(new String[]{"-async"});
    }
}












