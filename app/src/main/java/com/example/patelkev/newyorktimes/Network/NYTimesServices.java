package com.example.patelkev.newyorktimes.Network;

import com.example.patelkev.newyorktimes.Models.RootResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by patelkev on 10/23/16.
 */

public interface NYTimesServices {
    @GET("svc/search/v2/articlesearch.json")
    public Call<RootResponse> listArticles(@Query("api-key") String apiKey, @Query("page") int page);

    @GET("svc/search/v2/articlesearch.json")
    public Call<RootResponse> listArticles(@Query("api-key") String apiKey, @Query("q") String query, @Query("page") int page);

}
