package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroOfferFeaturesDetails {
    private Boolean ecoPromotion;
    private Boolean mainPagePromotion;
    private Boolean categoryPromotion;
    private Boolean draft;
    private Boolean forGuests;
    private Boolean shop;

    public Boolean getEcoPromotion() {
        return ecoPromotion;
    }

    public void setEcoPromotion(Boolean ecoPromotion) {
        this.ecoPromotion = ecoPromotion;
    }

    public Boolean getMainPagePromotion() {
        return mainPagePromotion;
    }

    public void setMainPagePromotion(Boolean mainPagePromotion) {
        this.mainPagePromotion = mainPagePromotion;
    }

    public Boolean getCategoryPromotion() {
        return categoryPromotion;
    }

    public void setCategoryPromotion(Boolean categoryPromotion) {
        this.categoryPromotion = categoryPromotion;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Boolean getForGuests() {
        return forGuests;
    }

    public void setForGuests(Boolean forGuests) {
        this.forGuests = forGuests;
    }

    public Boolean getShop() {
        return shop;
    }

    public void setShop(Boolean shop) {
        this.shop = shop;
    }
}
