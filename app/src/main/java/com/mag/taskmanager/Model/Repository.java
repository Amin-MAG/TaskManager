package com.mag.taskmanager.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mag.taskmanager.Model.Database.TaskManagerCursorWrapper;
import com.mag.taskmanager.Model.Database.TaskManagerDBSchema;
import com.mag.taskmanager.Model.Database.TaskManagerOpenHelper;
import com.mag.taskmanager.Model.Exceptions.BadAuthorizationException;
import com.mag.taskmanager.Var.Global;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Repository {

    private static Repository instance;

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
//            setMockTest();
        }
        return instance;
    }

    private static void setMockTest() {

//        getInstance().addUser(new User("amin", "12345", new ArrayList<Task>() {{
//           add(new Task("Wash Car", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Play Football", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Programming Android", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Make lunch", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Clean Room", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Wash Room", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Watch TV", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Feed Cat", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Read Books", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Do Homework", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Call Hamid", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Buy Shoes", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Buy Watch", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Attend BP Class", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Wash Window", "Something More", new Date(),TaskStatus.TODO));
//            add(new Task("Sell Modem", "Something More", new Date(),TaskStatus.TODO));
//            add(new Task("Pick up Reza", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Chat Zahra", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Wash the Dishes", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Planing For weekend", "Something More", new Date(),TaskStatus.DOING));
//            add(new Task("Study", "Something More", new Date(),TaskStatus.DOING));
//        }}));

    }


    // Repository

    private SQLiteDatabase database;
    private Context context;
//    private List<User> users = new ArrayList<>();

    private Repository(Context context) {
//        users = new ArrayList<User>();
        this.context = context.getApplicationContext();
        this.database = new TaskManagerOpenHelper(context).getWritableDatabase();
    }


    // Users

    public List<User> getUsers() {
        Cursor cursor = database.query(TaskManagerDBSchema.Users.NAME, null, null, null, null, null, null);
        CursorWrapper cursorWrapper = new CursorWrapper(cursor);
        return null;
    }

//    public User getUserById(UUID uuid) {
//        for (User user : users) {
//            if (user.getId() == uuid)
//                return user;
//        }
//        return null;
//    }


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

        List<Task> speceficTasks = new ArrayList<>();

        Log.d("sql-debug",
                status + " SELECT * FROM " + TaskManagerDBSchema.TaskManager.NAME
                        + " LEFT JOIN " + TaskManagerDBSchema.Users.NAME
                        + " on " + TaskManagerDBSchema.Users.NAME + "." + TaskManagerDBSchema.Users.Cols._ID + " = " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.USER_ID
                        + " LEFT JOIN " + TaskManagerDBSchema.Tasks.NAME
                        + " on " + TaskManagerDBSchema.Tasks.NAME + "." + TaskManagerDBSchema.Tasks.Cols._ID + " = " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.TASK_ID
                        + " WHERE " + TaskManagerDBSchema.Users.NAME + "." + TaskManagerDBSchema.Users.Cols.USERNAME + " = \"" + username + "\"");

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + TaskManagerDBSchema.TaskManager.NAME
                        + " LEFT JOIN " + TaskManagerDBSchema.Users.NAME
                        + " on " + TaskManagerDBSchema.Users.NAME + "." + TaskManagerDBSchema.Users.Cols._ID + " = " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.USER_ID
                        + " LEFT JOIN " + TaskManagerDBSchema.Tasks.NAME
                        + " on " + TaskManagerDBSchema.Tasks.NAME + "." + TaskManagerDBSchema.Tasks.Cols._ID + " = " + TaskManagerDBSchema.TaskManager.NAME + "." + TaskManagerDBSchema.TaskManager.Cols.TASK_ID
                        + " WHERE " + TaskManagerDBSchema.Users.NAME + "." + TaskManagerDBSchema.Users.Cols.USERNAME + " = \"" + username + "\""
                , new String[]{});

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


    public void addTaskForUser(String username, Task task) {
        int id = addTask(task);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskManagerDBSchema.TaskManager.Cols.TASK_ID, id);
        contentValues.put(TaskManagerDBSchema.TaskManager.Cols.USER_ID, Integer.parseInt(getUserByUsername(username).getId()));
        Log.d("sql-debug", username + " " + id);
        database.insertOrThrow(TaskManagerDBSchema.TaskManager.NAME, null, contentValues);
    }

    public int addTask(Task task) {
        Log.d("sql-debug", "SELECT " + TaskManagerDBSchema.Tasks.Cols._ID + " FROM " + TaskManagerDBSchema.Tasks.NAME + " ORDER BY " + TaskManagerDBSchema.Tasks.Cols._ID + " DESC;");
        database.insertOrThrow(TaskManagerDBSchema.Tasks.NAME, null, getContentValues(task));
        Cursor cursor = database.rawQuery("SELECT " + TaskManagerDBSchema.Tasks.Cols._ID + " FROM " + TaskManagerDBSchema.Tasks.NAME + " ORDER BY " + TaskManagerDBSchema.Tasks.Cols._ID + " DESC", new String[]{});
        TaskManagerCursorWrapper cursorWrapper = new TaskManagerCursorWrapper(cursor);

        cursorWrapper.moveToFirst();

        return cursorWrapper.getInt(0);
    }

    public void deleteTaskForUser(String username, String id) {
        database.delete(TaskManagerDBSchema.Tasks.NAME, TaskManagerDBSchema.Tasks.Cols._ID + " = ? ", new String[]{id});
        database.delete(TaskManagerDBSchema.TaskManager.NAME, TaskManagerDBSchema.TaskManager.Cols.TASK_ID + " = ? AND " + TaskManagerDBSchema.TaskManager.Cols.USER_ID + " = ?", new String[]{id, getUserByUsername(username).getId()});
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
//        values.put(TaskManagerDBSchema.Users.Cols._ID, user.getId());
        values.put(TaskManagerDBSchema.Users.Cols.USERNAME, user.getUsername());
        values.put(TaskManagerDBSchema.Users.Cols.PASSWORD, user.getPassword());

        return values;
    }

}
