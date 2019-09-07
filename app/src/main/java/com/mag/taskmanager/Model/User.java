package com.mag.taskmanager.Model;

import java.util.List;
import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String password;
    private List<Task> tasks;

    public User(String username, String password, List<Task> tasks) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.tasks = tasks;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
