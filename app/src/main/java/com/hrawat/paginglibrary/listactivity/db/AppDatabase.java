package com.hrawat.paginglibrary.listactivity.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hrawat.paginglibrary.listactivity.db.dao.UserDao;

@Database(entities = {User.class}, version = 1)
abstract public class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "UserDb";

    public abstract UserDao userDao();
}





