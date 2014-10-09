package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroOfferCategoryCollection {
    private List<AllegroOfferCategory> breadcrumbs;

    public List<AllegroOfferCategory> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<AllegroOfferCategory> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
