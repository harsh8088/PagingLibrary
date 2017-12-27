package com.hrawat.paginglibrary.listactivity.db.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hrawat.paginglibrary.listactivity.db.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User... user);

    @Delete
    void deleteUser(User... user);

    @Query("DELETE FROM User")
    void deleteAllUsers();

    @Query("SELECT * FROM User")
//    LivePagedListProvider<Integer, User> usersByFirstName();
    DataSource.Factory<Integer, User> usersByFirstName();

    @Query("SELECT * FROM user WHERE first_name LIKE :first ")
    List<User> findByName(String first);

    @Query("SELECT * FROM user WHERE user_id LIKE :userId ")
    User findByUserId(long userId);

    @Query("select * from user WHERE first_name LIKE :first order by first_name DESC")
    List<User> findUsers(String first);

    @Query("select * from user")
    List<User> findAllUsers();

    @Query("select * from user WHERE age > :age order by user_id ASC")
    DataSource.Factory<Integer, User> usersOlderThan(int age);


}
