package com.hrawat.paginglibrary;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.hrawat.paginglibrary.db.AppDatabase;
import com.hrawat.paginglibrary.db.DatabaseCreator;
import com.hrawat.paginglibrary.db.dao.UserDao;
import com.hrawat.paginglibrary.model.UserViewModel;

public class ListActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        appDatabase = Room.databaseBuilder(ListActivity.this,
                AppDatabase.class,
                AppDatabase.DATABASE_NAME).build();
        userDao = appDatabase.userDao();
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        RecyclerView recyclerView = findViewById(R.id.userList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init(userDao);
        final UserAdapter userUserAdapter = new UserAdapter();
        viewModel.userList.observe(this, pagedList -> userUserAdapter.setList(pagedList));
        recyclerView.setAdapter(userUserAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onQueryTextSubmit(String query) {
                new AsyncTask<Void, Void, User>() {
                    @Override
                    protected User doInBackground(Void... voids) {
                        return appDatabase.userDao().findByName(query);
                    }

                    @Override
                    protected void onPostExecute(User user) {
                        if (user != null && user.firstName != null)
                            Toast.makeText(ListActivity.this, "found name " +
                                    user.firstName + "at value : " + user.userId, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ListActivity.this, user.firstName +
                                    " not found", Toast.LENGTH_SHORT).show();
                        super.onPostExecute(user);
                    }
                }.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_insert:
                insertUser(appDatabase);
                break;
            case R.id.action_insert_single:
                insertSingleUser(appDatabase);
                break;
            case R.id.action_delete:
                deleteAllUsers(appDatabase);
                break;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteAllUsers(AppDatabase appDatabase) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().deleteAllUsers();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertSingleUser(AppDatabase appDatabase) {
        final DatabaseCreator databaseCreator = new DatabaseCreator();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insert(databaseCreator.getRandomUserList().get(0));
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertUser(final AppDatabase appDatabase) {
        final DatabaseCreator databaseCreator = new DatabaseCreator();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insertAll(databaseCreator.getRandomUserList());
                return null;
            }
        }.execute();
    }
}
