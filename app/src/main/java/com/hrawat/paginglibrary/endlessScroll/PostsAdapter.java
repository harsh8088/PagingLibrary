package com.hrawat.paginglibrary.endlessScroll;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.hrawat.paginglibrary.R;

/**
 * Created by hrawat on 12/26/2017.
 */
public class PostsAdapter extends PagedListAdapter<RedditPost, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private RequestManager glide;
    private Object PAYLOAD_SCORE;

    PostsAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.reddit_post_item:
                return RedditPostViewHolder.create(parent, glide);
            case R.layout.network_state_item:
                return NetworkStateItemViewHolder.create(parent);
            default:
                throw new IllegalArgumentException("unknown view type $viewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.reddit_post_item:
                if (holder instanceof RedditPostViewHolder) {
                    RedditPostViewHolder redditPostViewHolder = (RedditPostViewHolder) holder;
                    redditPostViewHolder.bind(getItem(position));
                }
                break;
            case R.layout.network_state_item:
                if (holder instanceof NetworkStateItemViewHolder) {
                    NetworkStateItemViewHolder networkStateItemViewHolder = (NetworkStateItemViewHolder) holder;
                    networkStateItemViewHolder.bind(networkState);
                }
                break;
        }
    }

    private static DiffCallback<RedditPost> DIFF_CALLBACK = new DiffCallback<RedditPost>() {
        @Override
        public boolean areItemsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return oldItem.name == newItem.name;
        }
    };

    private boolean hasExtraRow() {
        return networkState != null && networkState.getStatus() == NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.reddit_post_item;
        }
    }

    @Override
    public int getItemCount() {
        if (hasExtraRow()) {
            return super.getItemCount() + 1;
        } else {
            return super.getItemCount();
        }
    }

     void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}
//class PostsAdapter(
//    private val glide: RequestManager,
//private val retryCallback: () -> Unit)
//        : PagedListAdapter<RedditPost, RecyclerView.ViewHolder>(POST_COMPARATOR) {
//private var networkState: NetworkState? = null
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (getItemViewType(position)) {
//        R.layout.reddit_post_item -> (holder as RedditPostViewHolder).bind(getItem(position))
//        R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
//        networkState)
//        }
//        }
//
//        override fun onBindViewHolder(
//        holder: RecyclerView.ViewHolder,
//        position: Int,
//        payloads: MutableList<Any>) {
//        if (payloads.isNotEmpty()) {
//        val item = getItem(position)
//        (holder as RedditPostViewHolder).updateScore(item)
//        } else {
//        onBindViewHolder(holder, position)
//        }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//        R.layout.reddit_post_item -> RedditPostViewHolder.create(parent, glide)
//        R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
//        else -> throw IllegalArgumentException("unknown view type $viewType")
//        }
//        }
//
//private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED
//
//        override fun getItemViewType(position: Int): Int {
//        return if (hasExtraRow() && position == itemCount - 1) {
//        R.layout.network_state_item
//        } else {
//        R.layout.reddit_post_item
//        }
//        }
//
//        override fun getItemCount(): Int {
//        return super.getItemCount() + if (hasExtraRow()) 1 else 0
//        }
//
//        fun setNetworkState(newNetworkState: NetworkState?) {
//        val previousState = this.networkState
//        val hadExtraRow = hasExtraRow()
//        this.networkState = newNetworkState
//        val hasExtraRow = hasExtraRow()
//        if (hadExtraRow != hasExtraRow) {
//        if (hadExtraRow) {
//        notifyItemRemoved(super.getItemCount())
//        } else {
//        notifyItemInserted(super.getItemCount())
//        }
//        } else if (hasExtraRow && previousState != newNetworkState) {
//        notifyItemChanged(itemCount - 1)
//        }
//        }
//
//        companion object {
//private val PAYLOAD_SCORE = Any()
//        val POST_COMPARATOR = object : DiffCallback<RedditPost>() {
//        override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
//        oldItem == newItem
//
//        override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
//        oldItem.name == newItem.name
//
//        override fun getChangePayload(oldItem: RedditPost, newItem: RedditPost): Any? {
//        return if (sameExceptScore(oldItem, newItem)) {
//        PAYLOAD_SCORE
//        } else {
//        null
//        }
//        }
//        }
//
//private fun sameExceptScore(oldItem: RedditPost, newItem: RedditPost): Boolean {
//        // DON'T do this copy in a real app, it is just convenient here for the demo :)
//        // because reddit randomizes scores, we want to pass it as a payload to minimize
//        // UI updates between refreshes
//        return oldItem.copy(score = newItem.score) == newItem
//        }
//        }