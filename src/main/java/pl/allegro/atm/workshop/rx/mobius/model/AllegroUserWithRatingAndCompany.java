package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wladyslaw.hawrylczak on 2014-10-09.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroUserWithRatingAndCompany {
    private AllegroCompany company;
    private int rating;
    private String id;
    private String login;

    public AllegroCompany getCompany() {
        return company;
    }

    public void setCompany(AllegroCompany company) {
        this.company = company;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
