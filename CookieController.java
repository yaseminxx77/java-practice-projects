package com.vizja.swp.lab2.app;

import com.vizja.swp.lab2.lib.BaseController;
import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public class CookieController extends BaseController {
//When the user visits /cookie, the controller runs.
//The server sets a cookie using
//Creates a cookie and sends it to the client.
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setHeader("Set-Cookie", "username=Yasemin; Path=/; Max-Age=3600");
        String html = """
                <html>
                  <body>
                    <h2>Cookie created!</h2>
                    <p id='cookie-display'></p>
                  
                    <script>
                      document.getElementById('cookie-display').innerText = document.cookie;
                    </script>
                  </body>
                </html>
                """;
        response.getWriter().println(html);
    }
}
//Tests if the cookie is visible in the browser