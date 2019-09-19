package com.mag.taskmanager.Model;

import com.mag.taskmanager.Model.Exception.BadAuthorizationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Repository {

    private static Repository instance;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
            setMockTest();
        }
        return instance;
    }

    private static void setMockTest() {

        getInstance().addUser(new User("amin", "12345", new ArrayList<Task>() {{
           add(new Task("Wash Car", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Play Football", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Programming Android", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Make lunch", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Clean Room", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Wash Room", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Watch TV", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Feed Cat", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Read Books", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Do Homework", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Call Hamid", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Buy Shoes", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Buy Watch", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Attend BP Class", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Wash Window", "Something More", new Date(),TaskStatus.TODO));
            add(new Task("Sell Modem", "Something More", new Date(),TaskStatus.TODO));
            add(new Task("Pick up Reza", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Chat Zahra", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Wash the Dishes", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Planing For weekend", "Something More", new Date(),TaskStatus.DOING));
            add(new Task("Study", "Something More", new Date(),TaskStatus.DOING));
        }}));

    }



    // Repository

    private List<User> users = new ArrayList<>();

//
//    private List<User> users;
//
//    private Repository() {
//
//        users = new ArrayList<User>();
//
//    }
//
    
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

    public boolean checkAuthorization(String username, String password) throws BadAuthorizationException {
        User user = getUserByUsername(username);

        if (user==null || !user.getPassword().equals(password))
            throw new BadAuthorizationException();

        return true;
    }

}
