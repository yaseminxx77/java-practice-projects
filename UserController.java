package com.vizja.swp.lab2.app.user;

import com.vizja.swp.lab2.lib.BaseController;
import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public class UserController extends BaseController {

    private final UserRepository userRepository = UserRepository.getInstance();

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        String path = request.getPath();

        if (path.equals("/register")) {
            showRegisterPage(response);
        }
        else if (path.startsWith("/activate")) {
            activateAccount(request, response);
        }
        else if (path.equals("/login")) {
            showLoginPage(response);
        }
        else if (path.equals("/logout")) {
            logout(response);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String path = request.getPath();

        if (path.equals("/register")) {
            registerUser(request, response);
        }
        else if (path.equals("/login")) {
            loginUser(request, response);
        }
    }

    // ------------------ REGISTER PAGE ------------------

    private void showRegisterPage(HttpResponse response) {
        response.getWriter().println("""
            <h1>User Registration</h1>
            <form method='post' action='/register'>
                Username: <input name='username'><br>
                Password: <input name='password' type='password'><br>
                Email: <input name='email'><br>
                <button type='submit'>Register</button>
            </form>
        """);
    }

    private void registerUser(HttpRequest request, HttpResponse response) {
        String username = request.getParam("username");
        String password = request.getParam("password");
        String email = request.getParam("email");

        if (username == null || password == null || email == null) {
            response.getWriter().println("Missing fields.");
            return;
        }

        User u = userRepository.save(username, password, email);

        response.getWriter().println("""
            <h2>Registration successful!</h2>
            <p>Click to activate your account:</p>
            <a href='/activate?token=%s'>Activate Account</a>
        """.formatted(u.getActivationToken()));
    }


    // ------------------ ACTIVATION ------------------

    private void activateAccount(HttpRequest request, HttpResponse response) {

        // Query string üzerinden token'i çek
        String path = request.getPath();
        String token = null;

        if (path.contains("?")) {
            String query = path.substring(path.indexOf("?") + 1);
            for (String p : query.split("&")) {
                if (p.startsWith("token=")) {
                    token = p.substring(6);
                }
            }
        }

        if (token == null || token.isBlank()) {
            response.getWriter().println("<h2>Activation token is missing.</h2>");
            return;
        }

        User user = userRepository.findByToken(token);

        if (user == null) {
            response.getWriter().println("<h2>Invalid activation token.</h2>");
            return;
        }

        user.setActivated(true);

        response.getWriter().println("""
            <h2>Account activated successfully!</h2>
            <a href='/login'>Go to login</a>
        """);
    }


    // ------------------ LOGIN ------------------

    private void showLoginPage(HttpResponse response) {
        response.getWriter().println("""
            <h1>Login</h1>
            <form method='post' action='/login'>
                Username: <input name='username'><br>
                Password: <input name='password' type='password'><br>
                <button type='submit'>Login</button>
            </form>
        """);
    }

    private void loginUser(HttpRequest request, HttpResponse response) {
        String username = request.getParam("username");
        String password = request.getParam("password");

        User user = userRepository.findByUsername(username);

        if (user == null) {
            response.getWriter().println("User not found.");
            return;
        }

        if (!user.isActivated()) {
            response.getWriter().println("Account not activated. Check your email.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            response.getWriter().println("Wrong password.");
            return;
        }

        // COOKIE LOGIN
        response.setHeader("Set-Cookie", "username=" + username + "; Path=/");

        response.getWriter().println("""
            <h2>Welcome %s!</h2>
            <a href='/todos'>Go to Todo List</a>
        """.formatted(username));
    }


    // ------------------ LOGOUT ------------------

    private void logout(HttpResponse response) {
        response.setHeader("Set-Cookie", "username=; Max-Age=0; Path=/");
        response.getWriter().println("<h2>You are logged out.</h2>");
    }
}
