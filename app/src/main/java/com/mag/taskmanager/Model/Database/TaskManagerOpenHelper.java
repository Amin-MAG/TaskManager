package com.mag.taskmanager.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class TaskManagerOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public TaskManagerOpenHelper(@Nullable Context context) {
        super(context, TaskManagerDBSchema.NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TaskManagerDBSchema.Users.NAME + "(" +
                TaskManagerDBSchema.Users.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskManagerDBSchema.Users.Cols.USERNAME + ", " +
                TaskManagerDBSchema.Users.Cols.PASSWORD +
                ");");

        Log.d("sql-debug", "CREATE TABLE " + TaskManagerDBSchema.Tasks.NAME + "(" +
                TaskManagerDBSchema.Tasks.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskManagerDBSchema.Tasks.Cols.TITLE + ", " +
                TaskManagerDBSchema.Tasks.Cols.DESCRIPTION + ", " +
                TaskManagerDBSchema.Tasks.Cols.DATE + ", " +
                TaskManagerDBSchema.Tasks.Cols.STATUS +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + TaskManagerDBSchema.Tasks.NAME + "(" +
                TaskManagerDBSchema.Tasks.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskManagerDBSchema.Tasks.Cols.TITLE + ", " +
                TaskManagerDBSchema.Tasks.Cols.DESCRIPTION + ", " +
                TaskManagerDBSchema.Tasks.Cols.DATE + ", " +
                TaskManagerDBSchema.Tasks.Cols.STATUS +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + TaskManagerDBSchema.TaskManager.NAME + "(" +
                TaskManagerDBSchema.Tasks.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskManagerDBSchema.TaskManager.Cols.USER_ID+ ", " +
                TaskManagerDBSchema.TaskManager.Cols.TASK_ID + ", " +
                "FOREIGN  KEY(" + TaskManagerDBSchema.TaskManager.Cols.USER_ID + ") REFERENCES " + TaskManagerDBSchema.Users.NAME + "(" +  TaskManagerDBSchema.Users.Cols._ID + ")," +
                "FOREIGN  KEY(" + TaskManagerDBSchema.TaskManager.Cols.TASK_ID + ") REFERENCES " + TaskManagerDBSchema.Tasks.NAME+ "(" + TaskManagerDBSchema.Tasks.Cols._ID + ")" +
                ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
