package com.example.patelkev.newyorktimes;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.patelkev.newyorktimes.Models.RootResponse;
import com.example.patelkev.newyorktimes.Network.NetworkHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TODODEBUG", String.format("perform search for %s", query));

                fecthArticlesWithRetrofit(query);

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void fecthArticlesWithRetrofit(String query) {
        retrofit2.Call<RootResponse> rootResponseCall = NetworkHelper.sharedInstance()
                .getNyTimesServices().listArticles("56e2ed9898c442f9826db5ee05a33ac4",query);

        rootResponseCall.enqueue(new retrofit2.Callback<RootResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RootResponse> call, retrofit2.Response<RootResponse> response) {
                RootResponse rootResponse = response.body();
                Log.d("KevinDEBUG", "Got the response from retrofit" + rootResponse.getStatus());
            }

            @Override
            public void onFailure(retrofit2.Call<RootResponse> call, Throwable t) {

            }
        });

    }

    private void fetchArticlesWithAsyncHttp() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=56e2ed9898c442f9826db5ee05a33ac4&q=android";
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("KevinDebug", "Got the responseString" + responseString);
                Gson gson = new GsonBuilder().create();
                RootResponse rootResponse = gson.fromJson(responseString, RootResponse.class);

                Log.d("KevinDebug", "Got the rootResponse" + rootResponse.getStatus());
            }
        });
    }

    private void fetchArticlesWithOKhttp() {
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        String apiKey = "56e2ed9898c442f9826db5ee05a33ac4";
        HttpUrl.Builder urlbuilder = HttpUrl.parse(url).newBuilder();
        urlbuilder.addQueryParameter("api-key", apiKey);
        urlbuilder.addQueryParameter("q", "android");

        Request request = new Request.Builder().url(urlbuilder.build().toString()).build();
        NetworkHelper.sharedInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new GsonBuilder().create();
                RootResponse rootResponse = gson.fromJson(responseData, RootResponse.class);
                Log.d("KevinDebug", "got the response " + rootResponse.getStatus());
            }
        });
    }

}
