package com.vizja.swp.lab2.app;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {
    private static final List<Todo> todos = new ArrayList<>();
    private static int counter = 1;
//.java keeps all todos in a list. It can create, update, delete,
//and find todos.
    public void create(String title) {
        todos.add(new Todo(counter++, title, false));
    }

    public void update(int id, String title, boolean done) {
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                todo.setTitle(title);
                todo.setDone(done);
                return;
            }
        }
    }
    public void markAsDone(int id) {
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                todo.setDone(true);
                return;
            }
        }
    }


    public void delete(int id) {
        todos.removeIf(todo -> todo.getId() == id);
    }

    public List<Todo> findAll() {
        return todos;
    }
}
