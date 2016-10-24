package com.example.patelkev.newyorktimes.Activities;

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
import com.example.patelkev.newyorktimes.Models.Doc;
import com.example.patelkev.newyorktimes.Models.RootResponse;
import com.example.patelkev.newyorktimes.Network.NYTimesServices;
import com.example.patelkev.newyorktimes.Network.NetworkHelper;
import com.example.patelkev.newyorktimes.R;
import com.example.patelkev.newyorktimes.Utility.EndlessRecyclerViewScrollListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    RecyclerView rvArticlesContainer;
    ArticlesAdapter articlesAdapter;
    RecyclerView.LayoutManager layoutManager;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        rvArticlesContainer = (RecyclerView) findViewById(R.id.rvArticlesContainer);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        articlesAdapter = new ArticlesAdapter(this);

        rvArticlesContainer.setLayoutManager(layoutManager);
        rvArticlesContainer.setAdapter(articlesAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bindEndlessScrollViewListener();

        fetchArticles(0);
    }

    private void bindEndlessScrollViewListener() {
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener((StaggeredGridLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchArticles(page);
            }
        };
        rvArticlesContainer.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        final MainActivity activityReference = this;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                activityReference.searchQuery = query;
                fetchArticles(0);
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

    private void fetchArticles(final int page) {
        NYTimesServices nyTimesServices = NetworkHelper.sharedInstance().getNyTimesServices();
        retrofit2.Call<RootResponse> rootResponseCall;
        String APIKey = "56e2ed9898c442f9826db5ee05a33ac4";

        if (searchQuery == null) {
            rootResponseCall = nyTimesServices.listArticles(APIKey, page);
        } else {
            rootResponseCall = nyTimesServices.listArticles(APIKey, searchQuery, page);
        }

        if (page == 0) {
            endlessRecyclerViewScrollListener.resetState();
            articlesAdapter.resetAdapter();
        }

        rootResponseCall.enqueue(new retrofit2.Callback<RootResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RootResponse> call, retrofit2.Response<RootResponse> response) {
                RootResponse rootResponse = response.body();
                try {
                    List<Doc> newArticles = rootResponse.getResponse().getDocs();
                    articlesAdapter.appendArticles(newArticles);
                } catch (NullPointerException e) {
                    Log.d("KEVINDEBUG" , "Something is wrong");
                    // This is mostly due to rate limiting and I will try again.
                    fetchArticles(page);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RootResponse> call, Throwable t) {

            }
        });

    }

}
