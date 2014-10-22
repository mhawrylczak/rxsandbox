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

    public Offer convert(AllegroOfferV2 item, long views) {
        Offer offer = new Offer();
        offer.setId(item.getId());
        offer.setName(item.getName());
        offer.setViews(views);
        return offer;
    }
}
