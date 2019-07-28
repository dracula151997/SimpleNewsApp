package com.projecr.semicolon.newz.pagination;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.projecr.semicolon.newz.AppController;
import com.projecr.semicolon.newz.Constants;
import com.projecr.semicolon.newz.model.ArticlesItem;
import com.projecr.semicolon.newz.model.NewsResponse;
import com.projecr.semicolon.newz.util.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.projecr.semicolon.newz.util.LogUtil.verbose;

public class NewsDataSource extends PageKeyedDataSource<Integer, ArticlesItem> implements Constants {
    private MutableLiveData networkState;
    private AppController appController;

    public NewsDataSource(AppController appController) {
        this.appController = appController;
        networkState = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ArticlesItem> callback) {
        networkState.postValue(NetworkState.LOADING);
        verbose("loadInitial");
        appController.getRestApi().getTopHeadlines(COUNTRY, CATEGORY, QUERY, params.requestedLoadSize, 1, KEY)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            networkState.postValue(NetworkState.LOADED);
                            callback.onResult(response.body().getArticles(), null, 2);
                        } else {
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));

                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ArticlesItem> callback) {


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, ArticlesItem> callback) {
        verbose("loadAfter: Loading range = " + params.key + "Count = " + params.requestedLoadSize);
        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().getTopHeadlines(COUNTRY, CATEGORY, QUERY, params.requestedLoadSize, params.key, KEY)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            networkState.postValue(NetworkState.LOADED);

                            callback.onResult(response.body().getArticles(), params.key +1);
                        } else {
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));

                    }
                });


    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }
}


