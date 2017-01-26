package com.steen.models;
import com.steen.session.Search;

public class ProductModel implements Model {

    private Search search;

    public ProductModel() {
        this.search = new Search();
    }

    public void clearSession() {
        search = new Search();
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }
}
