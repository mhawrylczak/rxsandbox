package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OffersFacadeV2Request {
    private Integer limit;
    private Integer offset;
    private String sort;
    private String searchString;
    private String user;
    private String category;
    private Boolean searchInDescription;
    private List<OffersFilter> filters = Collections.emptyList();
    private Boolean finished;
    private Boolean similar;
    private String ean;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getSearchInDescription() {
        return searchInDescription;
    }

    public void setSearchInDescription(Boolean searchInDescription) {
        this.searchInDescription = searchInDescription;
    }

    public List<OffersFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<OffersFilter> filters) {
        this.filters = filters;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getSimilar() {
        return similar;
    }

    public void setSimilar(Boolean similar) {
        this.similar = similar;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public static class OffersFilter {
        private String id;
        private List<String> values;
        private OffersFilterRange range;

        public OffersFilter withFilterId(String id) {
            this.id = id;
            return this;
        }

        public OffersFilter withValues(List<String> values) {
            this.values = values;
            return this;
        }

        public OffersFilter withRange(OffersFilterRange range) {
            this.range = range;
            return this;
        }

        public String getId() {
            return id;
        }

        public List<String> getValues() {
            return values;
        }

        public OffersFilterRange getRange() {
            return range;
        }

        @Override
        public boolean equals(Object o){
            if (o instanceof OffersFilter){
                OffersFilter temp = (OffersFilter)o;
                if (this.id.equals(temp.getId())){
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.id.hashCode();
        }

    }

    public static class OffersFilterRange {
        private String min;
        private String max;

        public OffersFilterRange withMin(String min) {
            this.min = min;
            return this;
        }

        public OffersFilterRange withMax(String max) {
            this.max = max;
            return this;
        }

        public String getMin() {
            return min;
        }

        public String getMax() {
            return max;
        }
    }
}
