package com.vizja.swp.lab2.lib.filter;

import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) {

        String path = request.getPath();

        if (path.equals("/login") ||
                path.equals("/register") ||
                path.startsWith("/activate") ||
                path.equals("/generate-key")) {
            chain.doFilter(request, response);
            return;
        }


        String cookie = request.getHeader("Cookie").orElse("");

        boolean loggedIn = cookie.contains("username=");

        if (!loggedIn) {
            response.setStatus(401, "Unauthorized");
            response.getWriter().println(
                    "<h2>Not logged in. Please <a href='/login'>login</a>.</h2>"
            );
            return;
        }

        chain.doFilter(request, response);
    }
}
