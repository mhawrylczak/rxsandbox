package pl.allegro.atm.workshop.rx;

import pl.allegro.atm.workshop.rx.mobius.MobiusClient;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferDetails;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;
import rx.Observable;
import rx.functions.Func1;

import javax.inject.Inject;
import javax.ws.rs.*;
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
        Observable<DoGetItemsListCollection> observableOffers = mobiusClient.searchOffers(searchString);
        Observable<List<Offer>> observableList = observableOffers.map(new Func1<DoGetItemsListCollection, List<Offer>>() {
            @Override
            public List<Offer> call(DoGetItemsListCollection doGetItemsListCollection) {
                return offerAssembler.convert(doGetItemsListCollection);
            }
        });
        asyncResponse(observableList, asyncResponse);
    }

    @GET
    @Path("{id}")
    public void offer(@PathParam("id") String id, @Suspended final AsyncResponse asyncResponse) {
        Observable<Offer> offerObservable = mobiusClient.findOffer(id).map(new Func1<AllegroOfferDetails, Offer>() {
            @Override
            public Offer call(AllegroOfferDetails offerDetails) {
                return offerAssembler.convert(offerDetails);
            }
        });
        asyncResponse(offerObservable, asyncResponse);
    }
}
