package com.mag.taskmanager.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {

    private static Repository instance = new Repository();

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }


    // Repository

    private List<User> users;

    private Repository() {

        users = new ArrayList<User>();

    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(UUID uuid) {
        for (User user : users) {
            if (user.getId() == uuid)
                return user;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }


}
