package com.mag.taskmanager.Controller;

import androidx.fragment.app.Fragment;

import com.mag.taskmanager.Model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskListFragmentCollection {
    
    private static TaskListFragmentCollection instance;

    public static TaskListFragmentCollection getInstance() {
        if (instance == null) {
            instance = new TaskListFragmentCollection();
        }
        return instance;
    }

    private HashMap<TaskStatus, Fragment> taskListFragments = new HashMap<>();


    public HashMap<TaskStatus, Fragment> getTaskListFragments() {
        return taskListFragments;
    }

    public void setTaskListFragments(HashMap<TaskStatus, Fragment> taskListFragments) {
        this.taskListFragments = taskListFragments;
    }

}
