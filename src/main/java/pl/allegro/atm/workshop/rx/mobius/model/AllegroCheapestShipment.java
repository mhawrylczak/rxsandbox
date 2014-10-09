package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroCheapestShipment {
    private String inAdvance;
    private String onDelivery;

    public String getInAdvance() {
        return inAdvance;
    }

    public void setInAdvance(String inAdvance) {
        this.inAdvance = inAdvance;
    }

    public String getOnDelivery() {
        return onDelivery;
    }

    public void setOnDelivery(String onDelivery) {
        this.onDelivery = onDelivery;
    }
}
