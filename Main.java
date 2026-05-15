package com.vizja.sw.lab5;

import com.vizja.sw.lab5.lib.FrontController;
import com.vizja.sw.lab5.lib.Server;
import com.vizja.sw.lab5.lib.filter.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting server...");
        List<Filter> filters = FilterChain.builder()
                .addFilter(new LoggingFilter())
                .addFilter(new AuthenticationFilter())
                .build();
        FrontController.registerFilterChain(filters);
        FrontController.addRoute("/hello", new HelloController());
        Server server = new Server();
        server.start(8080);
        System.out.println("Server running at http://localhost:8080");
    }
}
