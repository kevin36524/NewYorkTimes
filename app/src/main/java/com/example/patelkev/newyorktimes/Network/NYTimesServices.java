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
    public Call<RootResponse> listArticles(@Query("api-key") String apiKey, @Query("q") String query, @Query("page") int page);

    @GET("svc/search/v2/articlesearch.json?fq=news_desk:(\"Sports\"%20\"Foreign\")")
    public Call<RootResponse> listArticles(@Query("api-key") String apiKey, @Query("page") int page, @Query("fq") String filter);

    //eg : https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=56e2ed9898c442f9826db5ee05a33ac4
    // &q=android
    // &fq=news_desk:(%22Sports%22%20%22Foreign%22)
    // &end_date=20161023
    // &begin_date=20160801
    // &sort=newest

    @GET("svc/search/v2/articlesearch.json")
    public Call<RootResponse> listArticles(@Query("api-key") String apiKey,
                                           @Query("q") String query,
                                           @Query("page") int page,
                                           @Query("fq") String filter,
                                           @Query("begin_date") String begin_date,
                                           @Query("end_date") String end_date,
                                           @Query("sort") String sort);

}
