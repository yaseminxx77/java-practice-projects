package com.vizja.swp.lab2.lib.filter;

import com.vizja.swp.lab2.lib.FrontController;
import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

public class FrontControllerFilter implements Filter {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) {

        // AuthenticationFilter 401 yazdıysa devam etme
        if (response.getStatus() == 401) {
            return;
        }

        // Controller'i çalıştır
        HttpResponse controllerResponse = FrontController.handle(request);

        // ----------------------------------------------------------------
        // FULL RESPONSE'U PARSE ET: Başlıkları ve body'yi ayır
        // ----------------------------------------------------------------
        String raw = controllerResponse.toString();
        String[] split = raw.split("\r\n\r\n", 2);

        String headersPart = split.length > 0 ? split[0] : "";
        String bodyPart = split.length > 1 ? split[1] : "";

        // ----------------------------------------------------------------
        // HEADERS'I Output Response'a yaz
        // ----------------------------------------------------------------
        for (String line : headersPart.split("\r\n")) {
            if (line.startsWith("Set-Cookie:")) {
                String cookie = line.substring("Set-Cookie:".length()).trim();
                response.setHeader("Set-Cookie", cookie);
            }
        }

        // ----------------------------------------------------------------
        // BODY YAZ
        // ----------------------------------------------------------------
        response.getWriter().print(bodyPart);
    }
}
