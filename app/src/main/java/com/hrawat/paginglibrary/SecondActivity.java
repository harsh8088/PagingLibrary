package com.hrawat.paginglibrary;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hrawat.paginglibrary.db.AppDatabase;
import com.hrawat.paginglibrary.db.dao.UserDao;
import com.hrawat.paginglibrary.model.UserViewModel;

public class SecondActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    private void init() {
        appDatabase=MyApplication.getAppDatabase();
        userDao = appDatabase.userDao();
        RecyclerView recyclerView = findViewById(R.id.second_user_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init(userDao);
        final UserAdapter userUserAdapter = new UserAdapter();
        viewModel.userList.observe(this, pagedList -> userUserAdapter.setList(pagedList));
        recyclerView.setAdapter(userUserAdapter);
    }
}
