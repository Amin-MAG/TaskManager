package com.mag.taskmanager.Model;

import java.util.ArrayList;

public class TaskRepository {

    private static TaskRepository instance = new TaskRepository();
    public static TaskRepository getInstance() {
        return instance;
    }


    // Repository

    private ArrayList<Task> tasks;


    private TaskRepository() {

    }


    public ArrayList<Task> getTasks() {
        return tasks;
    }


    public int getTaskSize() {
        return tasks.size();
    }


}
