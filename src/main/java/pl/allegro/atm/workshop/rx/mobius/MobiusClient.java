package pl.allegro.atm.workshop.rx.mobius;


import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferDetails;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;
import pl.allegro.atm.workshop.rx.mobius.model.OAuthAccessTokenResponse;
import pl.allegro.atm.workshop.rx.mobius.model.OffersFacadeV2Request;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
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

    private String token;

    public AllegroOfferDetails findOffer(String offerId) {
        return findOfferInvocationBuilder(offerId).get(AllegroOfferDetails.class);
    }

    private Invocation.Builder findOfferInvocationBuilder(String offerId) {
        return webTarget
                .path("/v2/allegro/offers")
                .path(offerId)
                .queryParam("access_token", getToken())
                .request(MediaType.APPLICATION_JSON_TYPE);
    }

    public DoGetItemsListCollection searchOffers(String searchString) {
        OffersFacadeV2Request request = new OffersFacadeV2Request();
        request.setSearchString(searchString);
        request.setLimit(5);
        return searchInvocationBuilder()
                .post(
                        Entity.entity(request, MediaType.APPLICATION_JSON_TYPE),
                        DoGetItemsListCollection.class);
    }

    private Invocation.Builder searchInvocationBuilder() {
        return webTarget
                .path("/v2/allegro/offers")
                .queryParam("access_token", getToken())
                .request(MediaType.APPLICATION_JSON_TYPE);
    }


    public synchronized String getToken() {
        if (token == null) {
            token = createToken();
        }
        return token;
    }

    private String createToken() {
        return createTokenInvocationBuilder()
                .get(OAuthAccessTokenResponse.class)
                .getAccess_token();
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
