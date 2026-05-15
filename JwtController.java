package com.vizja.swp.lab2.app.jwt;

import com.vizja.swp.lab2.lib.BaseController;
import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public class JwtController extends BaseController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.getWriter().println("""
                <h1>JWT Login</h1>
                <form method='post' action='/jwt-login'>
                    Username: <input name='username'><br>
                    Password: <input name='password' type='password'><br>
                    <button type='submit'>Login</button>
                </form>
        """);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String username = request.getParam("username");
        String password = request.getParam("password");

        if (!"yasemin".equals(username) || !"123123".equals(password)) {
            response.setStatus(401, "Unauthorized");
            response.getWriter().println("<h2>Wrong username or password</h2>");
            return;
        }

        String token = JwtUtil.createToken(username);

        response.getWriter().println("""
                <h2>Login successful!</h2>
                <p>Your JWT Token:</p>
                <textarea style='width:500px;height:120px;'>%s</textarea>
                """.formatted(token));
    }
}
