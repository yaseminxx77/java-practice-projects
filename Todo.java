package com.vizja.swp.lab2.app;

public class Todo {
    private int id;
    private String title;
    private boolean done;
//it defines onetodo item with id,
//title, and done (status).
    public Todo(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }

    public void setTitle(String title) { this.title = title; }
    public void setDone(boolean done) { this.done = done; }
}
