package pl.allegro.atm.workshop.rx;

import pl.allegro.atm.workshop.rx.mobius.MobiusClient;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferDetails;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferV2;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

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
        Observable<DoGetItemsListCollection> oDoGetItemsListCollection = mobiusClient.searchOffers(searchString);
        final Observable<AllegroOfferV2> oAllegroOfferV2 = oDoGetItemsListCollection.flatMap(new Func1<DoGetItemsListCollection, Observable<AllegroOfferV2>>() {
            @Override
            public Observable<AllegroOfferV2> call(DoGetItemsListCollection doGetItemsListCollection) {
                return Observable.from(doGetItemsListCollection.getOffers());
            }
        });

        Observable<Offer> oOffers = oAllegroOfferV2.flatMap( new Func1<AllegroOfferV2, Observable<Offer>>() {
            @Override
            public Observable<Offer> call(AllegroOfferV2 allegroOfferV2) {
                Observable<Long> oViews = mobiusClient.getOfferViews(allegroOfferV2.getId());
                return Observable.zip(Observable.just(allegroOfferV2), oViews, new Func2<AllegroOfferV2, Long, Offer>() {
                    @Override
                    public Offer call(AllegroOfferV2 allegroOfferV2, Long views) {
                        return offerAssembler.convert(allegroOfferV2, views);
                    }
                });
            }
        });
        Observable<List<Offer>> result = oOffers.toList();
        asyncResponse(result, asyncResponse);
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
