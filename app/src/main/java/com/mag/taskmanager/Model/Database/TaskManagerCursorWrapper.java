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
        User user = new User(username, password);
        return user;
    }

    public Task getTask() {
        int id = getInt(getColumnIndex(TaskManagerDBSchema.Tasks.Cols._ID));
        String title = getString(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.TITLE));
        String description = getString(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.DESCRIPTION));
        long date = getLong(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.DATE));
        TaskStatus taskStatus = null;

        switch (getInt(getColumnIndex(TaskManagerDBSchema.Tasks.Cols.STATUS))) {
            case TODO_CODE:
                taskStatus = TaskStatus.TODO;
                break;
            case DOING_CODE:
                taskStatus = TaskStatus.DOING;
                break;
            case DONE_CODE:
                taskStatus = TaskStatus.DONE;
                break;
            default:
                break;
        }

        return new Task(title, description, date, taskStatus);
    }

}
