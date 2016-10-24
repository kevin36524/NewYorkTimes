package com.example.patelkev.newyorktimes.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.patelkev.newyorktimes.Models.Doc;
import com.example.patelkev.newyorktimes.R;

public class ArticleDetailActivity extends AppCompatActivity {

    WebView detailWebView;
    Toolbar toolbar;
    Doc selectedArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent receivedIntent = getIntent();
        selectedArticle = (Doc) receivedIntent.getSerializableExtra("article");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        detailWebView = (WebView) findViewById(R.id.detailWebView);
        detailWebView.getSettings().setLoadsImagesAutomatically(true);
        detailWebView.getSettings().setJavaScriptEnabled(true);
        detailWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        detailWebView.setWebViewClient(new MyBrowser());
        detailWebView.loadUrl(selectedArticle.getWebUrl());
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private class MyBrowser extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
