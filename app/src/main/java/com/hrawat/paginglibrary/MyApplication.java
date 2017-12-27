package com.hrawat.paginglibrary;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.hrawat.paginglibrary.listactivity.db.AppDatabase;

/**
 * Created by hrawat on 12/18/2017.
 */
public class MyApplication extends Application {

    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,
                AppDatabase.DATABASE_NAME).build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
