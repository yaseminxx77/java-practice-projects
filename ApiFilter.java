package com.vizja.swp.lab2.lib.filter;

import com.vizja.swp.lab2.app.apikey.ApiKey;
import com.vizja.swp.lab2.app.apikey.ApiKeyRepository;
import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public class ApiFilter implements Filter {

    private final ApiKeyRepository repository = ApiKeyRepository.getInstance();

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) {

        String path = request.getPath();

        if (path.equals("/login") ||
                path.equals("/register") ||
                path.startsWith("/activate") ||
                path.equals("/logout") ||
                path.equals("/generate-key") ||
                path.equals("/favicon.ico") ||
                path.equals("/cookie") ||
                path.equals("/todos")) {     // <--- EKLENDİ
            chain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-KEY").orElse(null);

        if (apiKey == null) {
            response.setStatus(401, "Unauthorized");
            response.getWriter().println("<h2>Missing API Key</h2>");
            return;
        }

        ApiKey saved = repository.get();

        if (saved == null || !saved.getKey().equals(apiKey)) {
            response.setStatus(401, "Unauthorized");
            response.getWriter().println("<h2>Invalid API Key</h2>");
            return;
        }

        if (saved.isExpired()) {
            response.setStatus(401, "Unauthorized");
            response.getWriter().println("<h2>API Key expired</h2>");
            return;
        }

        chain.doFilter(request, response);
    }
}
