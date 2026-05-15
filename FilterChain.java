package com.vizja.swp.lab2.lib.filter;

import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

import java.util.List;

public class FilterChain {

    private final List<Filter> filters;
    private int currentIndex = 0;

    public FilterChain(List<Filter> filters) {
        this.filters = filters;
    }

    public void doFilter(HttpRequest request, HttpResponse response) {
        if (currentIndex < filters.size()) {
            Filter nextFilter = filters.get(currentIndex++);
            nextFilter.doFilter(request, response, this);
        }
    }
}
