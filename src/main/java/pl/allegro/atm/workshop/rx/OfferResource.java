package pl.allegro.atm.workshop.rx;

import pl.allegro.atm.workshop.rx.mobius.MobiusClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/offers")
@Produces("application/json")
public class OfferResource {
    @Inject
    private OfferAssembler offerAssembler;

    @Inject
    private MobiusClient mobiusClient;

    @GET
    public List<Offer> search(@QueryParam("q") String searchString) {
        return offerAssembler.convert(
                mobiusClient.searchOffers(searchString));
    }

    @GET
    @Path("{id}")
    public Offer offer(@PathParam("id") String id) {
        return offerAssembler.convert(
                mobiusClient.findOffer(id));
    }
}
