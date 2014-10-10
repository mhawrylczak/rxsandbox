package pl.allegro.atm.workshop.rx;

import pl.allegro.atm.workshop.rx.mobius.MobiusClient;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.List;

@Path("/offers")
@Produces("application/json")
public class OfferResource {
    @Inject
    private OfferAssembler offerAssembler;

    @Inject
    private MobiusClient mobiusClient;

    @GET
    public void search(@QueryParam("q") String searchString, @Suspended final AsyncResponse asyncResponse) {
        Observable<DoGetItemsListCollection> observableOffers =  mobiusClient.searchOffers(searchString);
        Observable<List<Offer>> observableList = observableOffers.map(new Func1<DoGetItemsListCollection, List<Offer>>() {
            @Override
            public List<Offer> call(DoGetItemsListCollection doGetItemsListCollection) {
                return offerAssembler.convert(doGetItemsListCollection);
            }
        });
        observableList.subscribe(new Action1<List<Offer>>() {
            @Override
            public void call(List<Offer> offers) {
                asyncResponse.resume(offers);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                asyncResponse.resume(throwable);
            }
        });
    }

    //TODO asynchronous offer
    @GET
    @Path("{id}")
    public Offer offer(@PathParam("id") String id) {
        return offerAssembler.convert(
                mobiusClient.findOffer(id));
    }
}
