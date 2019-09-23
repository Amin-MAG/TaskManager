package com.mag.taskmanager.Model;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private String id;
    private String username;
    private String password;

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


    public List<Task> getTaskByStatus(TaskStatus value) {
//        List<Task> speceficTasks = new ArrayList<>();
//        for (Task task : tasks) {
//            if (task.getTaskStatus() == value)
//                speceficTasks.add(task);
//        }
//        return speceficTasks;
        return  null;
    }

    public void addTask(Task task) {
//        tasks.add(task);
    }

    public void deleteTaskWithId(int uuid) {
//        for (int i = 0; i < tasks.size(); i++) {
//            if (tasks.get(i).getTaskId() == uuid){
//                tasks.remove(i);
//                break;
//            }
//        }
    }

//    public void updateTask(Task task) {
//        for (int i = 0; i < tasks.size(); i++) {
//            if (tasks.get(i).getTaskId() == task.getTaskId()){
//                tasks.get(i).setTitle(task.getTitle());
//                tasks.get(i).setDescription(task.getDescription());
//                tasks.get(i).setDate(task.getDate());
//                tasks.get(i).setTaskStatus(task.getTaskStatus());
//                break;
//            }
//        }
//    }

    public void  clearTasks() {
//        tasks = new ArrayList<>();
    }


}
