package com.hrawat.paginglibrary.endlessScroll;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hrawat.paginglibrary.R;

import static com.hrawat.paginglibrary.endlessScroll.NetworkState.Status.RUNNING;

/**
 * Created by hrawat on 12/26/2017.
 */
public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar progressBar;

    static NetworkStateItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reddit_post_item, parent, false);
        return new NetworkStateItemViewHolder(view);
    }

    private NetworkStateItemViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }

     void bind(NetworkState networkState) {
        progressBar.setVisibility(toVisbility(networkState.getStatus() == RUNNING));
    }


   private  int toVisbility(Boolean constraint){
         if (constraint) {
             return    View.VISIBLE;
        } else {
             return  View.GONE;
        }
    }
}
//class NetworkStateItemViewHolder(view: View,
//private val retryCallback: () -> Unit)
//        : RecyclerView.ViewHolder(view) {
//private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
//private val retry = view.findViewById<Button>(R.id.retry_button)
//private val errorMsg = view.findViewById<TextView>(R.id.error_msg)
//        init {
//        retry.setOnClickListener {
//        retryCallback()
//        }
//        }
//        fun bindTo(networkState: NetworkState?) {
//        progressBar.visibility = toVisbility(networkState?.status == RUNNING)
//        retry.visibility = toVisbility(networkState?.status == FAILED)
//        errorMsg.visibility = toVisbility(networkState?.msg != null)
//        errorMsg.text = networkState?.msg
//        }
//
//        companion object {
//        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
//        val view = LayoutInflater.from(parent.context)
//        .inflate(R.layout.network_state_item, parent, false)
//        return NetworkStateItemViewHolder(view, retryCallback)
//        }
//
//        fun toVisbility(constraint : Boolean): Int {
//        return if (constraint) {
//        View.VISIBLE
//        } else {
//        View.GONE
//        }
//        }
//        }
//        }
