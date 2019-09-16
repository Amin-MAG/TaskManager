package com.mag.taskmanager.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {

    private UUID taskId;
    private String title;
    private String description;
    private Date date;
    private TaskStatus taskStatus;

    public Task(String title, String description, Date date, TaskStatus taskStatus) {
        this.taskId = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.date = date;
        this.taskStatus = taskStatus;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

}
