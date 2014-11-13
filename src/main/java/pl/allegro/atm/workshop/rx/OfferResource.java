package pl.allegro.atm.workshop.rx;

import pl.allegro.atm.workshop.rx.mobius.MobiusClient;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferV2;
import rx.Observable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.List;

import static pl.allegro.atm.workshop.rx.RxJerseyHelpers.asyncResponse;

@Path("/offers")
@Produces("application/json")
public class OfferResource {
    @Inject
    private OfferAssembler offerAssembler;

    @Inject
    private MobiusClient mobiusClient;

    @GET
    public void search(@QueryParam("q") String searchString, @Suspended final AsyncResponse asyncResponse) {
        Observable<Offer> oOffers =  mobiusClient
                .searchOffers(searchString)
                .flatMap(itemsListCollection -> Observable.from(itemsListCollection.getOffers()))
                .flatMap(
                        (AllegroOfferV2 allegroOfferV2) ->
                                Observable.zip(
                                        Observable.just(allegroOfferV2),
                                        mobiusClient.getOfferViews(allegroOfferV2.getId()),
                                        offerAssembler::convert)
                );
        asyncResponse(oOffers.toList(), asyncResponse);
    }

    @GET
    @Path("{id}")
    public void offer(@PathParam("id") String id, @Suspended final AsyncResponse asyncResponse) {
        asyncResponse(mobiusClient.findOffer(id).map(offerAssembler::convert), asyncResponse);
    }
}
