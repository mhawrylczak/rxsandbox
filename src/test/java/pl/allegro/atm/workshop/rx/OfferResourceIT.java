package pl.allegro.atm.workshop.rx;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.*;
import pl.allegro.tech.bootstrap.commons.integration.RestServiceStarted;
import pl.allegro.tech.bootstrap.core.spi.ApplicationConfiguration;

import javax.ws.rs.core.GenericType;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class OfferResourceIT {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8089);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @ClassRule
    public static RestServiceStarted service = new RestServiceStarted(
            new ApplicationConfiguration.Builder().withAsyncSupported());

    @BeforeClass
    public static void before(){
        stubFor(get(urlEqualTo("/oauth/auth?grant_type=client_credentials"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBodyFile("auth.json")));
    }



    @Test
    public void testSearch() throws Exception {
        final String searchString = "mobius";
        stubFor(post(urlMatching("/v2/allegro/offers" + ".*"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBodyFile("offers.json")));

        GenericType<List<Offer>> offersListType = new GenericType<List<Offer>>() {};
        List<Offer> offers = service.getWebTarget().path("/offers").queryParam("q", searchString).request().get(offersListType);

        assertThat(offers).isNotEmpty();
    }

    @Test
    public void testOffer() throws Exception {
        final String offerId = "4661307927";
        stubFor(get(urlMatching("/v2/allegro/offers/" + offerId + ".*"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBodyFile("offer.json")));

        Offer offer = service.getWebTarget().path("/offers").path(offerId).request().get(Offer.class);

        assertThat(offer.getId()).isEqualTo(offerId);

    }
}