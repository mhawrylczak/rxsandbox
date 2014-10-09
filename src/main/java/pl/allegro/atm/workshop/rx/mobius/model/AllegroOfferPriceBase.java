package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroOfferPriceBase {
    private Float bid;
    private Float buyNow;

    public Float getBid() {
        return bid;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }

    public Float getBuyNow() {
        return buyNow;
    }

    public void setBuyNow(Float buyNow) {
        this.buyNow = buyNow;
    }
}
