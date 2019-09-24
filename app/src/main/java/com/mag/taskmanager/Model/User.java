package com.mag.taskmanager.Model;

public class User {

    private String id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this(username, password);
        this.id = String.valueOf(id);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
