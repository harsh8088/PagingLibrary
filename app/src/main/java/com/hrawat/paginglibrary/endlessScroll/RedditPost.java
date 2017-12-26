package com.hrawat.paginglibrary.endlessScroll;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hrawat on 12/26/2017.
 */
@Entity(tableName = "posts"
)
public class RedditPost {

    @PrimaryKey
    @SerializedName("name")
    String name;
    @SerializedName("title")
    String title;
    @SerializedName("score")
    int score;
    @SerializedName("author")
    String author;
    @SerializedName("subreddit") // this seems mutable but fine for a demo
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    String subreddit;
    @SerializedName("num_comments")
    int num_comments;
    @SerializedName("created_utc")
    Long created;
    String thumbnail;
    String url;
}
