package com.hrawat.paginglibrary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hrawat.paginglibrary.db.AppDatabase;
import com.hrawat.paginglibrary.db.DatabaseCreator;
import com.hrawat.paginglibrary.db.User;
import com.hrawat.paginglibrary.db.dao.UserDao;
import com.hrawat.paginglibrary.model.UserViewModel;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        appDatabase = MyApplication.getAppDatabase();
        userDao = appDatabase.userDao();
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        RecyclerView recyclerView = findViewById(R.id.userList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init(userDao);
        final UserAdapter userAdapter = new UserAdapter();
        viewModel.userList.observe(this, pagedList -> userAdapter.setList((PagedList<User>) pagedList));
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(userAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onQueryTextSubmit(String query) {
                new AsyncTask<Void, Void, List<User>>() {
                    @Override
                    protected List<User> doInBackground(Void... voids) {
                        List<User> list = appDatabase.userDao().findUsers(query);
                        return list;
                    }

                    @Override
                    protected void onPostExecute(List<User> user) {
                        if (user != null && user.size() > 0) {
//                            userAdapter.onCurrentListChanged((PagedList<User>) user);
                            Toast.makeText(ListActivity.this, "found name " +
                                    user.get(0).firstName + " " + user.size() + " times", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(ListActivity.this, query +
                                    " not found", Toast.LENGTH_SHORT).show();
                        super.onPostExecute(user);
                    }
                }.execute();
                return false;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        userAdapter.setClickListener(new UserAdapter.ClickListener() {
            @Override
            public void onLongClick(long userId) {
                preActionDialog(userId);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void preActionDialog(long userId) {
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {
                return appDatabase.userDao().findByUserId(userId);
            }

            @Override
            protected void onPostExecute(User user) {
                showActionDialog(user);
            }
        }.execute();
    }

    public void showActionDialog(User user) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Action");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.action_dialog);
        EditText username = dialog.findViewById(R.id.text_name);
        EditText address = dialog.findViewById(R.id.text_address);
        username.setText(user.firstName);
        address.setText(user.address);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.firstName = username.getText().toString();
                user.address = address.getText().toString();
                updateUser(user);
                dialog.dismiss();
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnDelete = dialog.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(user);
                dialog.dismiss();
            }
        });
        dialog.show();
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
                insertUser();
                break;
            case R.id.action_insert_single:
                insertSingleUser();
                break;
            case R.id.action_delete:
                deleteAllUsers();
                break;
            case R.id.action_second_list:
                Intent intent = new Intent(ListActivity.this, InfiniteListActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteAllUsers() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().deleteAllUsers();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertSingleUser() {
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
    public void insertUser() {
        final DatabaseCreator databaseCreator = new DatabaseCreator();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insertAll(databaseCreator.getRandomUserList());
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteUser(User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().deleteUser(user);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void updateUser(User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().updateUser(user);
                return null;
            }
        }.execute();
    }
}
