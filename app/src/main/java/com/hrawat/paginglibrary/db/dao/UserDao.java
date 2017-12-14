package com.hrawat.paginglibrary.db.dao;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hrawat.paginglibrary.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(User... user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateUser(User... user);

    @Delete
    public void deleteUser(User... user);

    @Query("DELETE FROM User")
    public void deleteAllUsers();

    @Query("SELECT * FROM User")
    public abstract LivePagedListProvider<Integer, User> usersByFirstName();

    @Query("SELECT * FROM user WHERE first_name LIKE :first ")
    List<User> findByName(String first);

    @Query("SELECT * FROM user WHERE user_id LIKE :userId ")
    User findByUserId(long userId);
}
