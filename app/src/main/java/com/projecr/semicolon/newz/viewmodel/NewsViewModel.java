package com.projecr.semicolon.newz.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.projecr.semicolon.newz.AppController;
import com.projecr.semicolon.newz.model.ArticlesItem;
import com.projecr.semicolon.newz.pagination.NewsDataFactory;
import com.projecr.semicolon.newz.pagination.NewsDataSource;
import com.projecr.semicolon.newz.util.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NewsViewModel extends ViewModel {
    private LiveData<NetworkState> networkStateLiveData;
    private LiveData<PagedList<ArticlesItem>> articlesItemLiveData;
    private AppController appController;
    private Executor executor;

    public NewsViewModel(AppController appController) {
        this.appController = appController;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        NewsDataFactory newsDataFactory = new NewsDataFactory(appController);

        networkStateLiveData = Transformations.switchMap(newsDataFactory.getNewsDataSourceLiveData(), NewsDataSource::getNetworkState);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .build();
        articlesItemLiveData = new LivePagedListBuilder(newsDataFactory, config)
                .setFetchExecutor(executor)
                .build();

    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public LiveData<PagedList<ArticlesItem>> getArticlesItemLiveData() {
        return articlesItemLiveData;
    }
}
