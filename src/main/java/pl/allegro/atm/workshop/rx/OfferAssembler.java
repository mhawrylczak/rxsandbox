package pl.allegro.atm.workshop.rx;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferDetails;
import pl.allegro.atm.workshop.rx.mobius.model.AllegroOfferV2;
import pl.allegro.atm.workshop.rx.mobius.model.DoGetItemsListCollection;

import javax.annotation.Nullable;
import java.util.List;

@Component
public class OfferAssembler {

    public Offer convert(AllegroOfferDetails offerDetails) {
        Offer offer = new Offer();
        offer.setId(offerDetails.getId());
        offer.setName(offerDetails.getName());
        offer.setViews(offerDetails.getViews());
        return offer;
    }

    //TODO
    //view property was added to Offer but it is not available in DoGetItemsListCollection
    public List<Offer> convert(DoGetItemsListCollection items) {
        return Lists.transform(items.getOffers(), new Function<AllegroOfferV2, Offer>() {
            @Nullable
            @Override
            public Offer apply(@Nullable AllegroOfferV2 item) {
                if (item != null) {
                    return convert(item);
                }
                return null;
            }
        });
    }

    private Offer convert(AllegroOfferV2 item) {
        Offer offer = new Offer();
        offer.setId(item.getId());
        offer.setName(item.getName());
        return offer;
    }
}
