package com.hrawat.paginglibrary;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hrawat.paginglibrary.db.User;
import com.hrawat.paginglibrary.network.GitHubApi;
import com.hrawat.paginglibrary.network.GitHubService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by hrawat on 12/20/2017.
 */
public class PDataSource extends PositionalDataSource<User> {

    private GitHubService gitHubService;

    public PDataSource() {
        gitHubService = GitHubApi.createGitHubService();
    }

    private int computeCount() {
        // actual count code here
        return 20;
    }

    private List<User> loadRangeInternal(int startPosition, int loadCount) {
        // actual load code here
        List<User> gitHubUser = new ArrayList();
        try {
            Response<List<User>> response = gitHubService.getUser(startPosition, loadCount).execute();
            if (response.isSuccessful() && response.code() == 200) {
                gitHubUser.addAll(response.body());
            } else {
                Log.e("API CALL", response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gitHubUser;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<User> callback) {
        int totalCount = computeCount();
        int position = computeInitialLoadPosition(params, totalCount);
        int loadSize = computeInitialLoadSize(params, position, totalCount);
        callback.onResult(loadRangeInternal(position, loadSize), position, totalCount);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<User> callback) {
        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize));
    }
}
