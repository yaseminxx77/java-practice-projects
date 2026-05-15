package com.vizja.swp.lab1;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final List<PrintWriter> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 5000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server listening on " + port);
            while (true) {
                Socket s = server.accept();
                new Thread(() -> handle(s)).start();
            }
        }
    }

    private static void handle(Socket s) {
        try (Socket socket = s;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)) {

            clients.add(out);
            String line;
            while ((line = in.readLine()) != null) {
                broadcast(line);
            }
        } catch (IOException ignored) { }
    }

    private static void broadcast(String msg) {
        synchronized (clients) {
            for (PrintWriter w : clients) {
                try { w.println(msg); } catch (Exception ignored) {}
            }
        }
    }
}
