package com.vizja.swp.lab2.lib;

import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrontController {
    private static final Map<String, BaseController> routes = new ConcurrentHashMap<>();

    public static HttpResponse handle(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        BaseController controller = null;

        // URL eşleşmesini daha esnek hale getiriyoruz
        for (String pathKey : routes.keySet()) {
            if (request.getPath().startsWith(pathKey)) {
                controller = routes.get(pathKey);
                break;
            }
        }

        if (controller == null) {
            response.setStatus(404, "Not Found");
            response.getWriter().println("404 Not Found: No controller for " + request.getPath());
            return response;
        }

        try {
            controller.handle(request, response);
        } catch (UnsupportedOperationException e) {
            response.setStatus(405, "Method Not Allowed");
            response.getWriter().println(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500, "Internal Server Error");
            response.getWriter().println("Server error: " + e.getMessage());
        }

        return response;
    }

    public static Map<String, BaseController> addRoute(String path, BaseController controller) {
        routes.put(path, controller);
        return routes;
    }
}
