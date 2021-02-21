package com.example.icommerce.models;

import java.io.Serializable;

import org.springframework.data.domain.Page;

public class ObjectPager implements Serializable {

    private static final long serialVersionUID = 167662233543280087L;

    private int page;
    private int limit;
    private int totalPage;

    public ObjectPager () {

    }

    public ObjectPager (Page<?> page, PagingRequest pagingRequest) {
        this.page = pagingRequest.getPage();
        this.limit = pagingRequest.getLimit();
        this.totalPage = page.getTotalPages();
    }

    public int getPage () {
        return page;
    }

    public void setPage (int page) {
        this.page = page;
    }

    public int getLimit () {
        return limit;
    }

    public void setLimit (int limit) {
        this.limit = limit;
    }

    public int getTotalPage () {
        return totalPage;
    }

    public void setTotalPage (int totalPage) {
        this.totalPage = totalPage;
    }

}
