package com.hrawat.paginglibrary.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.hrawat.paginglibrary.db.User;
import com.hrawat.paginglibrary.db.dao.UserDao;

public class UserViewModel extends ViewModel {

    public LiveData<PagedList<User>> userList;

    public UserViewModel() {
    }

    public void init(UserDao userDao) {
//        userList = userDao.usersByFirstName().create(0,
//                new PagedList.Config.Builder()
//                        .setEnablePlaceholders(true)
//                        .setPageSize(10)
//                        .setPrefetchDistance(5)
//                        .build());
        userList = new LivePagedListBuilder<>(
                userDao.usersByFirstName(), /* page size */ 20).build();
    }

    public void initInfinite(UserDao userDao) {
//        userList = new LivePagedListBuilder<Integer, User>() {
//            @Override
//            protected DataSource<Integer, User> createDataSource() {
//                tDataSource = new TDataSource();
//                return tDataSource;
//            }
//        };
        userList = new LivePagedListBuilder(userDao.usersOlderThan(25),new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .build()).build();
    }
}
