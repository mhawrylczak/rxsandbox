package pl.allegro.atm.workshop.rx;

import pl.allegro.atm.workshop.rx.mobius.MobiusClient;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;
import rx.Observable;

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

//TODO
//was:
//    @GET
//    public List<Offer> search(@QueryParam("q") String searchString) {
//
    @GET
    public void search(@QueryParam("q") String searchString, @Suspended final AsyncResponse asyncResponse) {
        Observable<DoGetItemsListCollection> observableOffers =  mobiusClient.searchOffers(searchString);
//now use:
//        asyncResponse.resume(List<Offer>) and asyncResponse.resume(Throwable)
//        offerAssembler.convert
    }

    @GET
    @Path("{id}")
    public Offer offer(@PathParam("id") String id) {
        return offerAssembler.convert(
                mobiusClient.findOffer(id));
    }
}
