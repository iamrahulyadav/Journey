package com.archi.archiinfo.journey.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.archi.archiinfo.journey.ArticleListActivity;
import com.archi.archiinfo.journey.R;
import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.model.Article;

import java.util.List;

/**
 * Created by archi_info on 1/6/2017.
 */

public class ArticlesGridAdapter extends BaseAdapter {
    private Activity mContext;
    private List<Article> articleMainList;
    private static LayoutInflater inflater = null;

    // Constructor
    public ArticlesGridAdapter(Activity c, List<Article> articleList) {
        this.mContext = c;
        this.articleMainList = articleList;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return articleMainList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleMainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolderItem viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.grid_item_article, null);
            viewHolder = new ViewHolderItem();
            viewHolder.tvArticleName = (TextView) view.findViewById(R.id.tvArticleName_GridAdapter);
            viewHolder.llArticle = (LinearLayout) view.findViewById(R.id.llArticleMain_ArticleActivity);
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }
//        Log.e("NAME", "ADP " + articleMainList.get(position).getArticales_type_name());
        viewHolder.tvArticleName.setText(articleMainList.get(position).getArticales_type_name());

        viewHolder.llArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_ARTICLE_TYPE, articleMainList.get(position).getArticales_type_id());
                Intent intent = new Intent(mContext, ArticleListActivity.class);
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                mContext.finish();
            }
        });


        return view;

    }

    static class ViewHolderItem {
        TextView tvArticleName;
        LinearLayout llArticle;
    }
}
