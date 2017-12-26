package com.hrawat.paginglibrary.endlessScroll;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

/**
 * Created by hrawat on 12/26/2017.
 * <p>
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
class Listing<T> {

    Listing(LiveData<PagedList<T>> pagedList, LiveData<NetworkState> networkState, LiveData<NetworkState> refreshState) {
        this.pagedList = pagedList;
        this.networkState = networkState;
        this.refreshState = refreshState;
    }

    // the LiveData of paged lists for the UI to observe
    private LiveData<PagedList<T>> pagedList;
    // represents the network request status to show to the user
    LiveData<NetworkState> networkState;
    // represents the refresh status to show to the user. Separate from networkState, this
    // value is importantly only when refresh is requested.
    private LiveData<NetworkState> refreshState;

    public LiveData<PagedList<T>> getPagedList() {
        return pagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }

    // refreshes the whole data and fetches it from scratch.
    void refresh() {
    }

    // retries any failed requests.
    void retry() {}
//    // the LiveData of paged lists for the UI to observe
//    val pagedList: LiveData<PagedList<T>>,
//    // represents the network request status to show to the user
//    val networkState: LiveData<NetworkState>,
//    // represents the refresh status to show to the user. Separate from networkState, this
//    // value is importantly only when refresh is requested.
//    val refreshState: LiveData<NetworkState>,
//    // refreshes the whole data and fetches it from scratch.
//    val refresh: () -> Unit,
//    // retries any failed requests.
//    val retry: () -> Unit)
}
