package com.mag.taskmanager.Model.DatabaseORM;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mag.taskmanager.Model.DaoMaster;
import com.mag.taskmanager.Model.DaoSession;

public class GreenDaoApplication extends Application {

    private static GreenDaoApplication application;
    private DaoSession daoSession;

    public static GreenDaoApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GreenDaoOpenHelper openHelper = new GreenDaoOpenHelper(this, "task_db_green_dao.db");
        SQLiteDatabase database = openHelper.getWritableDatabase();
        daoSession = new DaoMaster(database).newSession();

        application = this;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
