package com.hrawat.paginglibrary.network;

import com.hrawat.paginglibrary.db.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hrawat on 12/20/2017.
 */
public interface GitHubService {
        @GET("/users")
        Call<List<User>> getUser(@Query("since") int since, @Query("per_page") int perPage);
}
