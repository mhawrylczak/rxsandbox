package pl.allegro.atm.workshop.rx.mobius;


import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.rx.RxWebTarget;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.glassfish.jersey.jetty.connector.JettyConnectorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MobiusClientFactory {

    private final String mobiusUrl;

    public MobiusClientFactory(String mobiusUrl) {
        this.mobiusUrl = mobiusUrl;
    }

    public WebTarget defaultWebTarget(){
        return defaultClient().target(mobiusUrl);
    }

    public RxWebTarget<RxObservableInvoker> rxDefaultWebTarget(){
        return RxObservable.from(defaultWebTarget());
    }

    public Client defaultClient(){
        ClientConfig configuration = new ClientConfig();
        SSLContext sslContext = getSslContext();
        return ClientBuilder.newBuilder().withConfig(configuration).sslContext(sslContext).build();
    }

    public WebTarget authWebTarget(){
        return authClient().target(mobiusUrl);
    }

    public RxWebTarget<RxObservableInvoker> rxAuthWebTarget(){
        return RxObservable.from(authWebTarget());
    }

    public Client authClient(){
        Configuration configuration = new ClientConfig();
        SSLContext sslContext = getSslContext();
        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basicBuilder().build();
        return ClientBuilder.newBuilder().withConfig(configuration).sslContext(sslContext).register(authFeature).build();
    }


    private SSLContext getSslContext() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            try( InputStream is = getClass().getClassLoader().getResourceAsStream("truststore_client.jks") ){
                byte buf[] = new byte[1024];
                int size;
                while(-1 != ( size = is.read(buf))){
                    os.write(buf, 0, size);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SslConfigurator sslConfig = SslConfigurator.newInstance()
                .trustStoreBytes(os.toByteArray())
                .trustStorePassword("123456");

        return sslConfig.createSSLContext();
    }

}
