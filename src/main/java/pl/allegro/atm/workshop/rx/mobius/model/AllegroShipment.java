package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroShipment {
    /**
     * Price for more than one item
     */
    private Float costForAnother;

    /**
     * quantity limit in package
     */
    private Integer packageQuantityLimit;
    private boolean freeShipping;
    private AllegroShipmentFulfillment fulfillmentTime;
    private boolean selfPickup;

    public Float getCostForAnother() {
        return costForAnother;
    }

    public void setCostForAnother(Float costForAnother) {
        this.costForAnother = costForAnother;
    }

    public Integer getPackageQuantityLimit() {
        return packageQuantityLimit;
    }

    public void setPackageQuantityLimit(Integer packageQuantityLimit) {
        this.packageQuantityLimit = packageQuantityLimit;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public AllegroShipmentFulfillment getFulfillmentTime() {
        return fulfillmentTime;
    }

    public void setFulfillmentTime(AllegroShipmentFulfillment fulfillmentTime) {
        this.fulfillmentTime = fulfillmentTime;
    }

    public boolean isSelfPickup() {
        return selfPickup;
    }

    public void setSelfPickup(boolean selfPickup) {
        this.selfPickup = selfPickup;
    }
}
