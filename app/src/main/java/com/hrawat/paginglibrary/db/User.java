package com.hrawat.paginglibrary.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

@Entity
public class User {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long userId;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "address")
    public String address;
    @ColumnInfo(name = "age")
    public int age;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        User user = (User) obj;
        return user.userId == this.userId && Objects.equals(user.firstName, this.firstName);
    }
}
