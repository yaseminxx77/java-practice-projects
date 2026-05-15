package com.vizja.sw.lab5;

import com.vizja.sw.lab5.lib.BaseController;
import com.vizja.sw.lab5.lib.http.HttpRequest;
import com.vizja.sw.lab5.lib.http.HttpResponse;
import com.vizja.sw.lab5.lib.security.SecurityContext;

public class HelloController extends BaseController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        var auth = SecurityContext.getAuthentication();
        String user = auth.map(a -> a.username()).orElse("guest");
        response.getWriter().println("Hello, " + user + "!");
    }
}
