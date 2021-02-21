package com.example.icommerce.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingResponse<T> implements Serializable {

    private static final long serialVersionUID = 2427064781974662015L;

    private List<T>     data;
    private ObjectPager paging;
    private ObjectError error;

    public PagingResponse () {

    }

    public PagingResponse (Page<T> page, PagingRequest pagingRequest) {
        this.data = page.getContent();
        this.paging = new ObjectPager(page, pagingRequest);
    }

    public List<T> getData () {
        return data;
    }

    public void setData (List<T> data) {
        this.data = data;
    }

    public ObjectPager getPaging () {
        return paging;
    }

    public void setPaging (ObjectPager paging) {
        this.paging = paging;
    }

    public ObjectError getError () {
        return error;
    }

    public void setError (ObjectError error) {
        this.error = error;
    }
}
