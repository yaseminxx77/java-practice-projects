package com.vizja.swp.lab2.lib.filter;

import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public interface Filter {
    void doFilter(HttpRequest request, HttpResponse response, FilterChain chain);
}
