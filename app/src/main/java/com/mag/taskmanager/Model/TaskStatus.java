package com.mag.taskmanager.Model;

public enum TaskStatus {
    TODO(0),
    DOING(1),
    DONE(2);

    private int index;

    TaskStatus(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
