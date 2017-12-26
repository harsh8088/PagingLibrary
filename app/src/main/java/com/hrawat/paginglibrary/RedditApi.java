package com.hrawat.paginglibrary;

import android.util.Log;

import com.hrawat.paginglibrary.endlessScroll.RedditPost;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hrawat on 12/26/2017.
 */
public interface RedditApi {

    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTop(@Path("subreddit") String subreddit,
                                 @Query("limit") int limit);

    // for after/before param, either get from RedditDataResponse.after/before,
    // or pass RedditNewsDataResponse.name (though this is technically incorrect)
    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTopAfter(
            @Path("subreddit") String subreddit,
            @Query("after") String after,
            @Query("limit") int limit);

    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTopBefore(
            @Path("subreddit") String subreddit,
            @Query("before") String before,
            @Query("limit") int limit);

    class ListingResponse {

        ListingData data;

        ListingResponse(ListingData data) {
            this.data = data;
        }
    }

    class ListingData {

        List<RedditChildrenResponse> children;
        String after;
        String before;

        public ListingData(List<RedditChildrenResponse> children, String after, String before) {
            this.children = children;
            this.after = after;
            this.before = before;
        }
    }

    class RedditChildrenResponse {

        RedditPost data;

        public RedditChildrenResponse(RedditPost data) {
            this.data = data;
        }
    }

    public static String BASE_URL = "https://www.reddit.com/";

    static RedditApi create() {
        return create(HttpUrl.parse(BASE_URL));
    }

    static RedditApi create(HttpUrl httpUrl) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("API", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();
        return new Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi.class);
    }
}