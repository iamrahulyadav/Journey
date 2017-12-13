package com.archi.archiinfo.journey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.archi.archiinfo.journey.R;
import com.archi.archiinfo.journey.model.Article;

import java.util.List;

/**
 * Created by archi_info on 1/16/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private List<Article> articleMainList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvArticleCategoryName_ArticleAdapter);
        }
    }

    public ArticlesAdapter(Context mcontext, List<Article> articlesList) {
        this.articleMainList = articlesList;
        this.mContext = mcontext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articles_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article articles = articleMainList.get(position);
        holder.name.setText(articles.getArticales_type_name());

    }

    @Override
    public int getItemCount() {
        return articleMainList.size();
    }
}
