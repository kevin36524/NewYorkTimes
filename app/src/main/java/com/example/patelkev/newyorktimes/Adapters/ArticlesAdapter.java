package com.example.patelkev.newyorktimes.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patelkev.newyorktimes.Models.Doc;
import com.example.patelkev.newyorktimes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patelkev on 10/23/16.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    public interface TapDelegate {
        public void articleTapped(Doc article, int position);
    }

    public List<Doc> articles = new ArrayList<Doc>();
    Context context;
    TapDelegate delegate;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View articleView = inflater.inflate(R.layout.article_cell, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView, parent, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doc article = articles.get(position);

        holder.headlineTextView.setText(article.getHeadlineString());
        holder.articleImageView.setImageResource(0);
        String imageUrl = article.getImageUrl();
        if (imageUrl != null) {
            Picasso.with(context).load(imageUrl).resize(holder.cellWidth, 0).into(holder.articleImageView);
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public ArticlesAdapter(Context context, TapDelegate tapDelegate) {
        this.context = context;
        this.delegate = tapDelegate;
    }

    public void appendArticles(List<Doc> newArticles) {

        int currentSize = getItemCount();
        articles.addAll(newArticles);

        this.notifyDataSetChanged();
        //this.notifyItemRangeInserted(currentSize, articles.size() - 1);
    }

    public void resetAdapter() {
        this.articles = new ArrayList<Doc>();
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView articleImageView;
        TextView headlineTextView;
        ArticlesAdapter adapter;
        int cellWidth;

        public ViewHolder(View itemView, ViewGroup parent, ArticlesAdapter adapter) {
            super(itemView);

            itemView.setOnClickListener(this);
            this.adapter = adapter;
            cellWidth = parent.getWidth()/2 - 10;
            articleImageView = (ImageView) itemView.findViewById(R.id.ivArticleImage);
            headlineTextView = (TextView) itemView.findViewById(R.id.tvHeadline);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Doc article = adapter.articles.get(position);
            adapter.delegate.articleTapped(article, position);
        }
    }
}
