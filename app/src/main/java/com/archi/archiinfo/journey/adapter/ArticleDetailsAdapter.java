package com.archi.archiinfo.journey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.archi.archiinfo.journey.R;
import com.archi.archiinfo.journey.model.ArticleDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by archi_info on 1/5/2017.
 */

public class ArticleDetailsAdapter extends RecyclerView.Adapter<ArticleDetailsAdapter.MyViewHolder> {

    private List<ArticleDetail> articlesList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desc, dateTime;
        public ImageView ivArticle;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvArticleName);
            desc = (TextView) view.findViewById(R.id.tvArticleDesc);
            dateTime = (TextView) view.findViewById(R.id.tvDateTime);
            ivArticle = (ImageView) view.findViewById(R.id.ivArticleImage);
        }
    }

    public ArticleDetailsAdapter(Context mcontext, List<ArticleDetail> articlesList) {
        this.articlesList = articlesList;
        this.mContext = mcontext;
    }

    @Override
    public ArticleDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articles_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleDetailsAdapter.MyViewHolder holder, int position) {
        ArticleDetail articles = articlesList.get(position);
        holder.name.setText(articles.getName());
        holder.dateTime.setText(articles.getDateTime());
//        holder.desc.setText(articles.getDescription());
        holder.desc.setText(articles.getAuthorName());
        Log.e("IMAGE ", "IMG " + articles.getImage());
        if (!articles.getImage().equalsIgnoreCase("")) {
            Picasso.with(mContext).load(articles.getImage()).placeholder(R.drawable.default_img).error(R.drawable.default_img).resize(200, 200).into(holder.ivArticle);
        }

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}
