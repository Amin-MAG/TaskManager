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

    // Get Content Values

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskManagerDBSchema.Tasks.Cols._ID, task.getTaskId().toString());
        values.put(TaskManagerDBSchema.Tasks.Cols.TITLE, task.getTitle());
        values.put(TaskManagerDBSchema.Tasks.Cols.DESCRIPTION, task.getDescription());
        values.put(TaskManagerDBSchema.Tasks.Cols.DATE, task.getDate().getTime());

        int statusNumber = TaskManagerCursorWrapper.getStatusNumber(task.getTaskStatus());
        values.put(TaskManagerDBSchema.Tasks.Cols.STATUS, statusNumber);

        return values;
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(TaskManagerDBSchema.Users.Cols._ID, user.getId());
        values.put(TaskManagerDBSchema.Users.Cols.USERNAME, user.getUsername());
        values.put(TaskManagerDBSchema.Users.Cols.PASSWORD, user.getPassword());

        return values;
    }

}
