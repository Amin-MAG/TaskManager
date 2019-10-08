package com.mag.taskmanager.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mag.taskmanager.Model.Database.TaskManagerCursorWrapper;
import com.mag.taskmanager.Model.Database.TaskManagerDBSchema;
import com.mag.taskmanager.Model.Database.TaskManagerOpenHelper;
import com.mag.taskmanager.Model.Exceptions.BadAuthorizationException;

import java.util.ArrayList;
import java.util.List;

public class RepositoryOld {

    private static RepositoryOld instance;

    public static RepositoryOld getInstance(Context context) {
        if (instance == null)
            instance = new RepositoryOld(context);
        return instance;
    }


    // Repository

    private SQLiteDatabase database;
    private Context context;

    private RepositoryOld(Context context) {
        this.context = context.getApplicationContext();
        this.database = new TaskManagerOpenHelper(context).getWritableDatabase();
    }


    // Users

    public User getUserByUsername(String username) {

        ArrayList<User> corresponding = new ArrayList<>();

        Cursor cursor = database.query(TaskManagerDBSchema.Users.NAME, null, TaskManagerDBSchema.Users.Cols.USERNAME + " = ? ", new String[]{username}, null, null, null);
        TaskManagerCursorWrapper cursorWrapper = new TaskManagerCursorWrapper(cursor);

        try {

            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()) {

                corresponding.add((cursorWrapper).getUser());
                cursor.moveToNext();

            }

        } finally {

            cursor.close();
            cursorWrapper.close();

        }

        if (corresponding.size() == 1)
            return corresponding.get(0);

        return null;
    }

    public void addUser(User user) {
        database.insertOrThrow(TaskManagerDBSchema.Users.NAME, null, getContentValues(user));
    }

    public boolean checkAuthorization(String username, String password) throws BadAuthorizationException {

        User user = getUserByUsername(username);

        if (user == null || !user.getPassword().equals(password))
            throw new BadAuthorizationException();

        return true;
    }

    // Tasks

    public List<Task> getTasks(String username, TaskStatus status) {
        return getTasks(username, status, null);
    }

    public List<Task> getTasks(String username, TaskStatus status, String search) {

        List<Task> speceficTasks = new ArrayList<>();

        String command = "SELECT * FROM " + TaskManagerDBSchema.TaskManager.NAME
                + " LEFT JOIN " + TaskManagerDBSchema.Users.NAME
                + " on " + TaskManagerDBSchema.Users.NAME + "." + TaskManagerDBSchema.Users.Cols._ID + " = " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.USER_ID
                + " LEFT JOIN " + TaskManagerDBSchema.Tasks.NAME
                + " on " + TaskManagerDBSchema.Tasks.NAME + "." + TaskManagerDBSchema.Tasks.Cols._ID + " = " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.TASK_ID
                + " WHERE " + TaskManagerDBSchema.Users.NAME + "." + TaskManagerDBSchema.Users.Cols.USERNAME + " = \"" + username + "\"";

        if (search != null)
            command += " AND ( " + TaskManagerDBSchema.Tasks.Cols.TITLE + " Like \"%" + search + "%\" OR " + TaskManagerDBSchema.Tasks.Cols.DESCRIPTION + " Like \"%" + search + "%\" )";

        Cursor cursor = database.rawQuery(command, new String[]{});

        TaskManagerCursorWrapper cursorWrapper = new TaskManagerCursorWrapper(cursor);

        try {

            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()) {

                Task task = cursorWrapper.getTask();

                if (task.getTaskStatus() == status)
                    speceficTasks.add(task);

                cursorWrapper.moveToNext();

            }

        } finally {

            cursorWrapper.close();
            cursor.close();

        }

        return speceficTasks;
    }


    public void addTaskForUser(String userId, Task task) {
        int id = addTask(task);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskManagerDBSchema.TaskManager.Cols.TASK_ID, id);
        contentValues.put(TaskManagerDBSchema.TaskManager.Cols.USER_ID, Integer.parseInt(userId));

        database.insertOrThrow(TaskManagerDBSchema.TaskManager.NAME, null, contentValues);
    }

    public int addTask(Task task) {
        database.insertOrThrow(TaskManagerDBSchema.Tasks.NAME, null, getContentValues(task));
        Cursor cursor = database.rawQuery("SELECT " + TaskManagerDBSchema.Tasks.Cols._ID + " FROM " + TaskManagerDBSchema.Tasks.NAME + " ORDER BY " + TaskManagerDBSchema.Tasks.Cols._ID + " DESC", new String[]{});
        TaskManagerCursorWrapper cursorWrapper = new TaskManagerCursorWrapper(cursor);

        cursorWrapper.moveToFirst();

        return cursorWrapper.getInt(0);
    }

    public void deleteTaskForUser(String userId, String id) {
        database.delete(TaskManagerDBSchema.Tasks.NAME, TaskManagerDBSchema.Tasks.Cols._ID + " = ? ", new String[]{id});
        database.delete(TaskManagerDBSchema.TaskManager.NAME, TaskManagerDBSchema.TaskManager.Cols.TASK_ID + " = ? AND " + TaskManagerDBSchema.TaskManager.Cols.USER_ID + " = ?", new String[]{id, userId});
    }

    public void updateTaskForUser(Task task) {
        database.update(TaskManagerDBSchema.Tasks.NAME, getContentValues(task), TaskManagerDBSchema.Tasks.Cols._ID + " = ?", new String[]{String.valueOf(task.getTaskId())});
    }

    public void clearTasksForUser(String userId) {
        database.execSQL(
                "DELETE FROM " + TaskManagerDBSchema.TaskManager.NAME + " WHERE " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.USER_ID + " = " + userId + ";");
    }

    // Get Content Values

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskManagerDBSchema.Tasks.Cols._ID, task.getTaskId());
        values.put(TaskManagerDBSchema.Tasks.Cols.TITLE, task.getTitle());
        values.put(TaskManagerDBSchema.Tasks.Cols.DESCRIPTION, task.getDescription());
        values.put(TaskManagerDBSchema.Tasks.Cols.DATE, task.getDate().getTime());

        int statusNumber = TaskManagerCursorWrapper.getStatusNumber(task.getTaskStatus());
        values.put(TaskManagerDBSchema.Tasks.Cols.STATUS, statusNumber);

        return values;
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(TaskManagerDBSchema.Users.Cols.USERNAME, user.getUsername());
        values.put(TaskManagerDBSchema.Users.Cols.PASSWORD, user.getPassword());

        return values;
    }

}
