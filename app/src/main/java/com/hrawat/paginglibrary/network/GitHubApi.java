package com.hrawat.paginglibrary.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hrawat on 12/20/2017.
 */
public class GitHubApi {
    public static GitHubService createGitHubService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com");

        return builder.build().create(GitHubService.class);
    }
}
