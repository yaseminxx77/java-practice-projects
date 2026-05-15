package com.vizja.swp.lab2;

import com.vizja.swp.lab2.app.apikey.ApiKeyController;
import com.vizja.swp.lab2.app.user.UserController;
import com.vizja.swp.lab2.app.TodoController;
import com.vizja.swp.lab2.app.CookieController;
import com.vizja.swp.lab2.lib.Server;
import com.vizja.swp.lab2.lib.FrontController;
import com.vizja.swp.lab2.app.jwt.JwtController;
// this is my main class
public class Application {
    public static void main(String[] args) {

        TodoController todoController = new TodoController();
        UserController userController = new UserController();

        // USER ROUTES
        FrontController.addRoute("/register", userController);
        FrontController.addRoute("/login", userController);
        FrontController.addRoute("/logout", userController);
        FrontController.addRoute("/activate", userController);

        // TODO
        FrontController.addRoute("/todos", todoController);

        // COOKIE TEST
        FrontController.addRoute("/cookie", new CookieController());

        // API KEY
        FrontController.addRoute("/generate-key", new ApiKeyController());
        FrontController.addRoute("/jwt-login", new JwtController());

        try (var server = new Server()) {
            server.start(8080);
        }
    }
}
// if we wanna test to working cookie http://localhost:8080/todos http://localhost:8080/register