package com.projecr.semicolon.newz.rest;

import com.projecr.semicolon.newz.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {
    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country")
                                   String country,
                                       @Query("category")
                                   String category,
                                       @Query("q")
                                   String query,
                                       @Query("pageSize")
                                   int pageSize,
                                       @Query("page")
                                   int page,
                                       @Query("apiKey")
                                   String apiKey);

}
