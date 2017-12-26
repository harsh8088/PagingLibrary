package com.hrawat.paginglibrary.endlessScroll;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.hrawat.paginglibrary.MyApplication;
import com.hrawat.paginglibrary.R;
import com.hrawat.paginglibrary.db.AppDatabase;
import com.hrawat.paginglibrary.db.dao.UserDao;
import com.hrawat.paginglibrary.model.UserViewModel;

public class EndlessListActivity extends AppCompatActivity {

    public static String KEY_SUBREDDIT = "subreddit";
    public static String DEFAULT_SUBREDDIT = "androiddev";
    public static String KEY_REPOSITORY_TYPE = "repository_type";
    private AppDatabase appDatabase;
    private UserDao userDao;
    private SubRedditViewModel subRedditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless_scroll);
        appDatabase = MyApplication.getAppDatabase();
        userDao = appDatabase.userDao();
        subRedditViewModel = getViewModel();
        initAdapter();
        initView();
    }

    private void initAdapter() {
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        PostsAdapter adapter = new PostsAdapter();
        recyclerView.setAdapter(adapter);
        subRedditViewModel.posts.observe(this, new Observer<PagedList<RedditPost>>() {
            @Override
            public void onChanged(@Nullable PagedList<RedditPost> redditPosts) {
                adapter.setList(redditPosts);
            }
        });
        subRedditViewModel.networkState.observe(this, new Observer<NetworkState>() {
                    @Override
                    public void onChanged(@Nullable NetworkState networkState) {
                        adapter.setNetworkState(networkState);
                    }
                });

    }

    private SubRedditViewModel getViewModel() {

        return new SubRedditViewModel(new RedditPostRepository());



    }

    private void initView() {
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        RecyclerView recyclerView = findViewById(R.id.rv_user_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init(userDao);
    }
}
