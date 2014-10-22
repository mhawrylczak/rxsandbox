package pl.allegro.atm.workshop.rx.mobius;


import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.rx.RxClient;
import org.glassfish.jersey.client.rx.RxWebTarget;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Component
public class MobiusClient {

    @Inject
    @Named("rxMobiusWebTarget")
    private RxWebTarget<RxObservableInvoker> webTarget;

    @Inject
    @Named("rxMobiusAuthWebTarget")
    private RxWebTarget<RxObservableInvoker> authWebTarget;

    @Value("${mobiusKeyUser}")
    private String keyUser;

    @Value("${mobiusKeyPassword}")
    private String keyPassword;

    private Subject<String, String> tokenSubject;

    public Observable<Long> getOfferViews(final String offerId){
        return findOffer(offerId).map(new Func1<AllegroOfferDetails, Long>() {
            @Override
            public Long call(AllegroOfferDetails offerDetails) {
                return offerDetails.getViews();
            }
        });
    }

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
        return findOfferInvocationBuilder(offerId, token).get(AllegroOfferDetails.class);
    }

    private RxObservableInvoker findOfferInvocationBuilder(String offerId, String token) {
        return webTarget
                .path("/v2/allegro/offers")
                .path(offerId)
                .queryParam("access_token", token)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx();
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
        return searchInvocationBuilder(token).post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), DoGetItemsListCollection.class);
    }

    private RxObservableInvoker searchInvocationBuilder(String token) {
        return webTarget
                .path("/v2/allegro/offers")
                .queryParam("access_token", token)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx();
    }


    public synchronized Observable<String> getToken() {
        if (tokenSubject == null) {
            tokenSubject = AsyncSubject.create();
            createToken().subscribe(tokenSubject);
        }
        return tokenSubject.asObservable();
    }

    private Observable<String> createToken() {
        return createTokenInvocationBuilder().get(OAuthAccessTokenResponse.class).map(new Func1<OAuthAccessTokenResponse, String>() {
            @Override
            public String call(OAuthAccessTokenResponse oAuthAccessTokenResponse) {
                return oAuthAccessTokenResponse.getAccess_token();
            }
        });
    }

    private RxObservableInvoker createTokenInvocationBuilder() {
        return authWebTarget
                .path("oauth/auth")
                .queryParam("grant_type", "client_credentials")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, keyUser)
                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, keyPassword)
                .rx();
    }

}
