package com.projecr.semicolon.newz.pagination;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.projecr.semicolon.newz.AppController;

public class NewsDataFactory extends DataSource.Factory {
    private MutableLiveData<NewsDataSource> newsDataSourceLiveData;
    private NewsDataSource newsDataSource;
    private AppController appController;

    public NewsDataFactory(AppController appController) {
        this.appController = appController;
        this.newsDataSourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        newsDataSource = new NewsDataSource(appController);
        newsDataSourceLiveData.postValue(newsDataSource);
        return newsDataSource;
    }

    public MutableLiveData<NewsDataSource> getNewsDataSourceLiveData() {
        return newsDataSourceLiveData;
    }
}
