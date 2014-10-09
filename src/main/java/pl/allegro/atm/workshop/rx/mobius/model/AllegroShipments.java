package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroShipments {
    private List<AllegroShipment> inAdvance;
    private List<AllegroShipment> onDelivery;
    private String additionalInfo;
    private int fastestDeliveryHours;
    private AllegroCheapestShipment cheapestShipmentsId;
    private boolean abroadShipment;
    private boolean costHandledBySeller;
    private boolean costHandledByBuyer;
    private boolean selfPickUpAvailable;

    public List<AllegroShipment> getInAdvance() {
        return inAdvance;
    }

    public void setInAdvance(List<AllegroShipment> inAdvance) {
        this.inAdvance = inAdvance;
    }

    public List<AllegroShipment> getOnDelivery() {
        return onDelivery;
    }

    public void setOnDelivery(List<AllegroShipment> onDelivery) {
        this.onDelivery = onDelivery;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getFastestDeliveryHours() {
        return fastestDeliveryHours;
    }

    public void setFastestDeliveryHours(int fastestDeliveryHours) {
        this.fastestDeliveryHours = fastestDeliveryHours;
    }

    public AllegroCheapestShipment getCheapestShipmentsId() {
        return cheapestShipmentsId;
    }

    public void setCheapestShipmentsId(AllegroCheapestShipment cheapestShipmentsId) {
        this.cheapestShipmentsId = cheapestShipmentsId;
    }

    public boolean isAbroadShipment() {
        return abroadShipment;
    }

    public void setAbroadShipment(boolean abroadShipment) {
        this.abroadShipment = abroadShipment;
    }

    public boolean isCostHandledBySeller() {
        return costHandledBySeller;
    }

    public void setCostHandledBySeller(boolean costHandledBySeller) {
        this.costHandledBySeller = costHandledBySeller;
    }

    public boolean isCostHandledByBuyer() {
        return costHandledByBuyer;
    }

    public void setCostHandledByBuyer(boolean costHandledByBuyer) {
        this.costHandledByBuyer = costHandledByBuyer;
    }

    public boolean isSelfPickUpAvailable() {
        return selfPickUpAvailable;
    }

    public void setSelfPickUpAvailable(boolean selfPickUpAvailable) {
        this.selfPickUpAvailable = selfPickUpAvailable;
    }
}
