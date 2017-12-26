package com.hrawat.paginglibrary.endlessScroll.byPage;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.hrawat.paginglibrary.RedditApi;
import com.hrawat.paginglibrary.endlessScroll.RedditPost;

import java.util.concurrent.Executor;

/**
 * Created by hrawat on 12/26/2017.
 */
public class SubRedditDataSourceFactory implements DataSource.Factory<String, RedditPost> {
    private RedditApi redditApi;
    private String subredditName;
    private Executor retryExecutor;
    MutableLiveData sourceLiveData =new  MutableLiveData<PageKeyedSubredditDataSource>();

    public SubRedditDataSourceFactory(RedditApi redditApi, String subredditName, Executor retryExecutor) {
        this.redditApi = redditApi;
        this.subredditName = subredditName;
        this.retryExecutor = retryExecutor;
    }

    @Override
    public DataSource<String, RedditPost> create() {
        PageKeyedDataSource<String,RedditPost> source=new PageKeyedSubredditDataSource(redditApi,subredditName,retryExecutor);
        sourceLiveData.postValue(source);
        return source;
    }
}

//    private val redditApi: RedditApi,
//private val subredditName: String,
//private val retryExecutor: Executor) : DataSource.Factory<String, RedditPost> {
//        val sourceLiveData = MutableLiveData<PageKeyedSubredditDataSource>()
//        override fun create(): DataSource<String, RedditPost> {
//        val source = PageKeyedSubredditDataSource(redditApi, subredditName, retryExecutor)
//        sourceLiveData.postValue(source)
//        return source
//        }