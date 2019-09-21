package com.mag.taskmanager.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        sqLiteDatabase.execSQL("CREATE TABLE " + TaskManagerDBSchema.Tasks.NAME + "(" +
                TaskManagerDBSchema.Tasks.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskManagerDBSchema.Tasks.Cols.TITLE + ", " +
                TaskManagerDBSchema.Tasks.Cols.DESCRIPTION + ", " +
                TaskManagerDBSchema.Tasks.Cols.DATE + ", " +
                TaskManagerDBSchema.Tasks.Cols.STATUS +
                ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
