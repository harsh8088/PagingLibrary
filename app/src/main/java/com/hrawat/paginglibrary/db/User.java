package com.hrawat.paginglibrary.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.util.Objects;

@Entity
public class User {

    public static DiffCallback<User> DIFF_CALLBACK = new DiffCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.userId == newItem.userId;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };
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
