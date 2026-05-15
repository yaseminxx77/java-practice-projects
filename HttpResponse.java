package com.vizja.swp.lab2.lib.http;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

    private final StringWriter bodyWriter = new StringWriter();
    private final PrintWriter writer = new PrintWriter(bodyWriter);

    private final Map<String, String> headers = new LinkedHashMap<>();

    private int statusCode = 200;
    private String statusMessage = "OK";

    // -------------------- GETTER'lar --------------------

    public PrintWriter getWriter() {
        return writer;
    }

    public int getStatus() {
        return statusCode;
    }

    public String getBody() {
        writer.flush();
        return bodyWriter.toString();
    }

    // -------------------- SETTER'lar --------------------

    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    // -------------------- FULL HTTP RESPONSE --------------------

    @Override
    public String toString() {

        writer.flush();
        String body = bodyWriter.toString();

        // Varsayılan headerlar eklenir
        headers.putIfAbsent("Content-Type", "text/html; charset=UTF-8");
        headers.put("Content-Length", String.valueOf(body.getBytes(StandardCharsets.UTF_8).length));

        // HTTP başlangıç satırı
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n");

        // Tüm headerlar
        headers.forEach((k, v) -> sb.append(k).append(": ").append(v).append("\r\n"));

        sb.append("\r\n");   // Header + Body ayracı

        sb.append(body);

        return sb.toString();
    }
}
