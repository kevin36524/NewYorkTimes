package com.example.patelkev.newyorktimes;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.patelkev.newyorktimes.Adapters.ArticlesAdapter;
import com.example.patelkev.newyorktimes.Models.RootResponse;
import com.example.patelkev.newyorktimes.Network.NYTimesServices;
import com.example.patelkev.newyorktimes.Network.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    RecyclerView rvArticlesContainer;
    ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        rvArticlesContainer = (RecyclerView) findViewById(R.id.rvArticlesContainer);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        articlesAdapter = new ArticlesAdapter(this);

        rvArticlesContainer.setLayoutManager(layoutManager);
        rvArticlesContainer.setAdapter(articlesAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fecthArticlesWithRetrofit(null);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
        NYTimesServices nyTimesServices = NetworkHelper.sharedInstance().getNyTimesServices();
        retrofit2.Call<RootResponse> rootResponseCall;
        String APIKey = "56e2ed9898c442f9826db5ee05a33ac4";

        if (query == null) {
            rootResponseCall = nyTimesServices.listArticles(APIKey);
        } else {
            rootResponseCall = nyTimesServices.listArticles(APIKey, query);
        }

        rootResponseCall.enqueue(new retrofit2.Callback<RootResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RootResponse> call, retrofit2.Response<RootResponse> response) {
                RootResponse rootResponse = response.body();
                Log.d("KevinDEBUG", "Got the response from retrofit" + rootResponse.getStatus());
                articlesAdapter.appendArticles(rootResponse.getResponse().getDocs());
            }

            @Override
            public void onFailure(retrofit2.Call<RootResponse> call, Throwable t) {

            }
        });

    }

}
