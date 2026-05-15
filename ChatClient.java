package com.vizja.swp.lab1;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        String name = args.length > 0 ? args[0] : "Bob";
        String host = args.length > 1 ? args[1] : "127.0.0.1";
        int port    = args.length > 2 ? Integer.parseInt(args[2]) : 5000;

        try (Socket socket = new Socket(host, port)) {
            BufferedReader in      = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out        = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected. You are: " + name);

            Thread t = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) System.out.println(line);
                } catch (IOException ignored) {}
                System.out.println("Disconnected.");
            });
            t.setDaemon(true);
            t.start();

            String input;
            while ((input = console.readLine()) != null) {
                if (input.trim().isEmpty()) continue;
                out.println(name + ": " + input);
            }
        }
    }
}
