package com.vizja.sw.lab5.lib.filter;

import com.vizja.sw.lab5.lib.http.HttpRequest;
import com.vizja.sw.lab5.lib.http.HttpResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingFilter implements Filter {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) throws Exception {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String method = request.getMethod().toString();
        String path = request.getPath();

        System.out.println("[" + time + "] Incoming request: " + method + " " + path);
        chain.doFilter(request, response);
        System.out.println("[" + time + "] Response: " + response.getStatusCode() + " " + response.getStatusMessage());
    }
}
