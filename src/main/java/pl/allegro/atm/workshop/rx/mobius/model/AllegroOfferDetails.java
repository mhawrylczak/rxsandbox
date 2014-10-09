package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroOfferDetails {
    private AllegroOfferDescription description;
    private AllegroUserWithRatingAndCompany seller;
    private long views;
    private AllegroPaymentsCollection payments;
    private AllegroShipments shipments;
    private List<MobiusImage> gallery;
    private List<AllegroAttribute> attributes;
    private int status;
    private AllegroOfferFeaturesDetails featuresDetails;
    private Boolean isWatched;
    private AllegroOfferCategoryCollection categories;
    private AllegroOfferLocation location;
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

    public AllegroOfferDescription getDescription() {
        return description;
    }

    public void setDescription(AllegroOfferDescription description) {
        this.description = description;
    }

    public AllegroUserWithRatingAndCompany getSeller() {
        return seller;
    }

    public void setSeller(AllegroUserWithRatingAndCompany seller) {
        this.seller = seller;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public AllegroPaymentsCollection getPayments() {
        return payments;
    }

    public void setPayments(AllegroPaymentsCollection payments) {
        this.payments = payments;
    }

    public AllegroShipments getShipments() {
        return shipments;
    }

    public void setShipments(AllegroShipments shipments) {
        this.shipments = shipments;
    }

    public List<MobiusImage> getGallery() {
        return gallery;
    }

    public void setGallery(List<MobiusImage> gallery) {
        this.gallery = gallery;
    }

    public List<AllegroAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AllegroAttribute> attributes) {
        this.attributes = attributes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AllegroOfferFeaturesDetails getFeaturesDetails() {
        return featuresDetails;
    }

    public void setFeaturesDetails(AllegroOfferFeaturesDetails featuresDetails) {
        this.featuresDetails = featuresDetails;
    }

    public Boolean getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Boolean isWatched) {
        this.isWatched = isWatched;
    }

    public AllegroOfferCategoryCollection getCategories() {
        return categories;
    }

    public void setCategories(AllegroOfferCategoryCollection categories) {
        this.categories = categories;
    }

    public AllegroOfferLocation getLocation() {
        return location;
    }

    public void setLocation(AllegroOfferLocation location) {
        this.location = location;
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
