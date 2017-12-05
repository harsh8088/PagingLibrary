package com.hrawat.paginglibrary.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hrawat.paginglibrary.User;
import com.hrawat.paginglibrary.db.dao.UserDao;

@Database(entities = {User.class}, version = 1)
abstract public class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "UserDb";

    public abstract UserDao userDao();
}





