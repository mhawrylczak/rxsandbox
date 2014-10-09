package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DoGetItemsListCollection {

    private String searchString;
    private int count;

    private List<AllegroOfferV2> offers;


    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AllegroOfferV2> getOffers() {
        return offers;
    }

    public void setOffers(List<AllegroOfferV2> offers) {
        this.offers = offers;
    }
}
