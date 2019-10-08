package com.mag.taskmanager.Model.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.Model.User;

public class TaskManagerCursorWrapper extends CursorWrapper {


    public static final int TODO_CODE = 5001;
    public static final int DOING_CODE = 5002;
    public static final int DONE_CODE = 5003;

    public TaskManagerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {

        int id = getInt(getColumnIndex(TaskManagerDBSchema.Users.Cols._ID));
        String username = getString(getColumnIndex(TaskManagerDBSchema.Users.Cols.USERNAME));
        String password = getString(getColumnIndex(TaskManagerDBSchema.Users.Cols.PASSWORD));
//        User user = new User(id, username, password);

//        return user;
        return null;

    }

    public Task getTask() {
        int id = getInt(getColumnIndex(TaskManagerDBSchema.Tasks.Cols._ID));
        String title = getString(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.TITLE));
        String description = getString(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.DESCRIPTION));
        long date = getLong(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.DATE));
        TaskStatus taskStatus = getStatus(getInt(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.STATUS)));

//        return new Task(id, title, description, date, taskStatus);
        return null;
    }


    public static int getStatusNumber(TaskStatus taskStatus) {

        switch (taskStatus) {
            case DONE:
                return TaskManagerCursorWrapper.DONE_CODE;
            case DOING:
                return TaskManagerCursorWrapper.DOING_CODE;
            case TODO:
                return TaskManagerCursorWrapper.TODO_CODE;
        }

        return -1;
    }

    public static TaskStatus getStatus(int number) {

        switch (number) {
            case TODO_CODE:
                return TaskStatus.TODO;
            case DOING_CODE:
                return TaskStatus.DOING;
            case DONE_CODE:
                return TaskStatus.DONE;
        }

        return null;
    }

}
