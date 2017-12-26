package com.hrawat.paginglibrary.endlessScroll;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

/**
 * Created by hrawat on 12/26/2017.
 */
class SubRedditViewModel extends ViewModel {

    private RedditPostRepository redditPostRepository;
    private MutableLiveData<String> subredditName = new MutableLiveData<>();

    public SubRedditViewModel(RedditPostRepository repo) {
        this.redditPostRepository = repo;
    }

    private LiveData<Listing<RedditPost>> repoResult = Transformations.map(subredditName, new Function<String, Listing<RedditPost>>() {
        @Override
        public Listing<RedditPost> apply(String input) {
            return redditPostRepository.postsofSubReddit(input, 30);
        }
    });
     LiveData<PagedList<RedditPost>> posts = Transformations.switchMap(repoResult, Listing::getPagedList);
     LiveData<NetworkState> networkState = Transformations.switchMap(repoResult, Listing::getNetworkState);
     LiveData<NetworkState> refreshState = Transformations.switchMap(repoResult, Listing::getRefreshState);

    private void refresh() {
        repoResult.getValue().refresh();
    }

    private boolean showSubreddit(String subreddit) {
        if (subredditName.getValue() == subreddit) {
            return false;
        } else {
            subredditName.setValue(subreddit);
            return true;
        }
    }

    private void retry() {
        repoResult.getValue().retry();
    }

    private String currentSubReddit() {
        return subredditName.getValue();
    }
}
//class SubRedditViewModel(private val repository: RedditPostRepository) : ViewModel() {
//private val subredditName = MutableLiveData<String>()
//private val repoResult = map(subredditName, {
//        repository.postsOfSubreddit(it, 30)
//        })
//        val posts = switchMap(repoResult, { it.pagedList })!!
//        val networkState = switchMap(repoResult, { it.networkState })!!
//        val refreshState = switchMap(repoResult, { it.refreshState })!!
//
//        fun refresh() {
//        repoResult.value?.refresh?.invoke()
//        }
//
//        fun showSubreddit(subreddit: String): Boolean {
//        if (subredditName.value == subreddit) {
//        return false
//        }
//        subredditName.value = subreddit
//        return true
//        }
//
//        fun retry() {
//        val listing = repoResult?.value
//        listing?.retry?.invoke()
//        }
//
//        fun currentSubreddit(): String? = subredditName.value
//        }
