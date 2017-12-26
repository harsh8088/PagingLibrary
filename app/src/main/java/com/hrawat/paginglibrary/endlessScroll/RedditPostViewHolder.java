package com.hrawat.paginglibrary.endlessScroll;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.hrawat.paginglibrary.R;

/**
 * Created by hrawat on 12/26/2017.
 */
class RedditPostViewHolder extends RecyclerView.ViewHolder {

    private RequestManager glide;
    private TextView title;
    private TextView subtitle;
    private TextView scoreview;
    private ImageView thumbnail;

    static RedditPostViewHolder create(ViewGroup parent, RequestManager glide) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reddit_post_item, parent, false);
        return new RedditPostViewHolder(view, glide);
    }

    RedditPostViewHolder(View itemView, RequestManager glide) {
        super(itemView);
        this.glide = glide;
        title = itemView.findViewById(R.id.title);
        subtitle = itemView.findViewById(R.id.subtitle);
        scoreview = itemView.findViewById(R.id.score);
        thumbnail = itemView.findViewById(R.id.thumbnail);
    }

    void bind(RedditPost redditPost) {
        title.setText(redditPost.title);
        subtitle.setText(redditPost.subreddit);
        scoreview.setText(redditPost.score);
    }
}
//    private val title: TextView = view.findViewById(R.id.title)
//private val subtitle: TextView = view.findViewById(R.id.subtitle)
//private val score: TextView = view.findViewById(R.id.score)
//private val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
//private var post : RedditPost? = null
//        init {
//        view.setOnClickListener {
//        post?.url?.let { url ->
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        view.context.startActivity(intent)
//        }
//        }
//        }
//
//        fun bind(post: RedditPost?) {
//        this.post = post
//        title.text = post?.title ?: "loading"
//        subtitle.text = itemView.context.resources.getString(R.string.post_subtitle,
//        post?.author ?: "unknown")
//        score.text = "${post?.score ?: 0}"
//        if (post?.thumbnail?.startsWith("http") == true) {
//        thumbnail.visibility = View.VISIBLE
//        glide.load(post.thumbnail).centerCrop()
//        .placeholder(R.drawable.ic_insert_photo_black_48dp)
//        .into(thumbnail)
//        } else {
//        thumbnail.visibility = View.GONE
//        Glide.clear(thumbnail)
//        }
//
//        }
//
//        companion object {
//        fun create(parent: ViewGroup, glide: RequestManager): RedditPostViewHolder {
//        val view = LayoutInflater.from(parent.context)
//        .inflate(R.layout.reddit_post_item, parent, false)
//        return RedditPostViewHolder(view, glide)
//        }
//        }
//
//        fun updateScore(item: RedditPost?) {
//        post = item
//        score.text = "${item?.score ?: 0}"
//        }
