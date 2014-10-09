package pl.allegro.atm.workshop.rx.mobius.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroOfferV2 {

    private AllegroOfferLocationBase location;
    private AllegroUserWithRating seller;
    private AllegroOfferSource source;
    private int condition;
    private boolean freeShipping;
    private AllegroOfferFeatures features;

    private AllegroOfferPriceBase prices;
    private long secondsLeft;
    private long endingTime;
    private boolean buyNow;
    private boolean auction;
    private boolean allegroStandard;
    private MobiusImage mainImage;
    private AllegroOfferBidsBase bids;

    private String id;
    private String name;

    public AllegroOfferLocationBase getLocation() {
        return location;
    }

    public void setLocation(AllegroOfferLocationBase location) {
        this.location = location;
    }

    public AllegroUserWithRating getSeller() {
        return seller;
    }

    public void setSeller(AllegroUserWithRating seller) {
        this.seller = seller;
    }

    public AllegroOfferSource getSource() {
        return source;
    }

    public void setSource(AllegroOfferSource source) {
        this.source = source;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public AllegroOfferFeatures getFeatures() {
        return features;
    }

    public void setFeatures(AllegroOfferFeatures features) {
        this.features = features;
    }

    public AllegroOfferPriceBase getPrices() {
        return prices;
    }

    public void setPrices(AllegroOfferPriceBase prices) {
        this.prices = prices;
    }

    public long getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(long secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }

    public boolean isBuyNow() {
        return buyNow;
    }

    public void setBuyNow(boolean buyNow) {
        this.buyNow = buyNow;
    }

    public boolean isAuction() {
        return auction;
    }

    public void setAuction(boolean auction) {
        this.auction = auction;
    }

    public boolean isAllegroStandard() {
        return allegroStandard;
    }

    public void setAllegroStandard(boolean allegroStandard) {
        this.allegroStandard = allegroStandard;
    }

    public MobiusImage getMainImage() {
        return mainImage;
    }

    public void setMainImage(MobiusImage mainImage) {
        this.mainImage = mainImage;
    }

    public AllegroOfferBidsBase getBids() {
        return bids;
    }

    public void setBids(AllegroOfferBidsBase bids) {
        this.bids = bids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
