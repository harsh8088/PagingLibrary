package com.hrawat.paginglibrary.endlessScroll.byPage;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.hrawat.paginglibrary.RedditApi;

import java.util.concurrent.Executor;

/**
 * Created by hrawat on 12/26/2017.
 */
public class PageKeyedSubredditDataSource extends PageKeyedDataSource<String, RedditApi> {

    RedditApi redditApi;
    String subredditName;
    Executor retryExecutor;

    public PageKeyedSubredditDataSource(RedditApi redditApi, String subredditName, Executor retryExecutor) {
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, RedditApi> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, RedditApi> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, RedditApi> callback) {
    }
}
