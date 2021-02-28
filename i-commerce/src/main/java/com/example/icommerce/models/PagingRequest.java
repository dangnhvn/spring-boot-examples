package com.example.icommerce.models;

import java.io.Serializable;
import java.util.Optional;

public class PagingRequest implements Serializable {

    private static final long serialVersionUID = -6209972368913919977L;

    private Integer page;
    private Integer limit;
    private String  sortBy;
    private String  search;

    public PagingRequest () {
    }

    public Integer getPage () {
        return Optional.ofNullable(page).orElse(Integer.valueOf(0));
    }

    public void setPage (Integer page) {
        this.page = page;
    }

    public Integer getLimit () {
        return Optional.ofNullable(limit).orElse(Integer.valueOf(20));
    }

    public void setLimit (Integer limit) {
        this.limit = limit;
    }

    public String getSortBy () {
        return sortBy;
    }

    public void setSortBy (String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearch () {
        return search;
    }

    public void setSearch (String search) {
        this.search = search;
    }
}
