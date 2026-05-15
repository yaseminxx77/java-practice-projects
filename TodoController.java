package com.vizja.swp.lab2.app;

import com.vizja.swp.lab2.lib.BaseController;
import com.vizja.swp.lab2.lib.http.HttpRequest;
import com.vizja.swp.lab2.lib.http.HttpResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TodoController extends BaseController {

    private static final TodoRepository repository = new TodoRepository();

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {


        String cookie = request.getHeader("Cookie").orElse("");
        String username = null;

        if (cookie.contains("username=")) {
            int s = cookie.indexOf("username=") + 9;
            int e = cookie.indexOf(";", s);
            if (e == -1) e = cookie.length();
            username = cookie.substring(s, e);
        }

        if (username == null) {
            response.getWriter().println("Not logged in. Please <a href='/login'>login</a>.");
            return;
        }

        String action = null;
        String title = null;
        int id = -1;

        String fullPath = request.getPath();

        if (fullPath.contains("?")) {
            String query = fullPath.substring(fullPath.indexOf("?") + 1);
            String[] params = query.split("&");

            for (String p : params) {
                if (p.startsWith("action=")) {
                    action = p.substring("action=".length());
                } else if (p.startsWith("title=")) {
                    title = URLDecoder.decode(
                            p.substring("title=".length()),
                            StandardCharsets.UTF_8
                    );
                } else if (p.startsWith("id=")) {
                    id = Integer.parseInt(p.substring("id=".length()));
                }
            }
        }

        if ("create".equals(action)) {
            repository.create(title);
        } else if ("update".equals(action)) {
            repository.markAsDone(id);
        } else if ("delete".equals(action)) {
            repository.delete(id);
        }
        try {
            String html = Files.readString(Paths.get("src/main/resources/index.html"));

            StringBuilder list = new StringBuilder();

            for (Todo t : repository.findAll()) {
                list.append("<p>")
                        .append(t.getId()).append(" - ")
                        .append(t.getTitle())
                        .append(" [")
                        .append(t.isDone() ? "Done" : "Pending")
                        .append("] ")
                        .append("<a href='/todos?action=update&id=")
                        .append(t.getId()).append("'>Update</a> ")
                        .append("<a href='/todos?action=delete&id=")
                        .append(t.getId()).append("'>Delete</a>")
                        .append("</p>");
            }

            html = html.replace("<!-- Java liste buraya gelecek -->", list.toString());
            response.getWriter().println(html);

        } catch (Exception e) {
            response.getWriter().println("Page error: " + e.getMessage());
        }
    }
}
