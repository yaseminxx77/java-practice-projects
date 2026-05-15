package com.vizja.swp.lab2.lib.filter;

import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

import java.time.LocalDateTime;

public class LoggingFilter implements Filter {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) {
        // Request log
        System.out.println("---- Logging Filter ----");
        System.out.println("Time: " + LocalDateTime.now());
        System.out.println("Request: " + request.getMethod() + " " + request.getPath());

        chain.doFilter(request, response);

        // Response log
        System.out.println("Response: " + response.toString().split("\r\n")[0]);
        System.out.println("-------------------------");
    }
}
