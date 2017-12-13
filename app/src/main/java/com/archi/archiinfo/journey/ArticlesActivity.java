package com.archi.archiinfo.journey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.adapter.ArticlesAdapter;
import com.archi.archiinfo.journey.model.Article;
import com.archi.archiinfo.journey.recyclerClick.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archi_info on 1/4/2017.
 * http://web-medico.com/web2/Android_IOS_App_journey/api/get_articales_type.php
 */

public class ArticlesActivity extends BaseActivity {
    private List<Article> articleMainList;
    //    private GridView gvArticles;
    private ArticlesAdapter mAdapter;
    private RecyclerView rvArticleList;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        init();
        if (Util.isOnline(getApplicationContext())) {
            new getArticleMainList().execute();
        }
    }


    private void init() {
//        gvArticles = (GridView) findViewById(R.id.gvArticles_ArticlesActivity);
        rvArticleList = (RecyclerView) findViewById(R.id.rvArticles_ArticlesActivity);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_ArticlesActivity);

        rvArticleList.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvArticleList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Article article = articleMainList.get(position);
//                Gson gson = new Gson();
//                Bundle bundle = new Bundle();
//                bundle.putString("article", gson.toJson(article));
                Util.WriteSharePrefrence(ArticlesActivity.this, Constant.SHRED_PR.KEY_ARTICLE_TYPE, articleMainList.get(position).getArticales_type_id());
                Intent intent = new Intent(ArticlesActivity.this, ArticleListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public class getArticleMainList extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ArticlesActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            articleMainList = new ArrayList<>();
            articleMainList.clear();
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";

            response = Util.getResponseofGet(Constant.BASE_URL + "get_articales_type.php");
//                urlString = Constant.BASE_URL + "login.php?email=" + usernamestr + "&password=" + passwordstr;
            Log.e("RESULT", ">>" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            pd.dismiss();
            super.onPostExecute(s);
            pd.dismiss();
            Log.d("Response", "" + s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").equalsIgnoreCase("true")) {

                    JSONArray arry = object.getJSONArray("data");
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
                        Article articlesMain = new Article();
                        Log.e("@@@@@@ ", "@@@@@@@@@@@@");
                        Log.e("NAME ", "" + obj.getString("articales_type_id"));
                        Log.e("NAME ", "" + obj.getString("articales_type_name"));
                        articlesMain.setArticales_type_id(obj.getString("articales_type_id"));
                        articlesMain.setArticales_type_name(obj.getString("articales_type_name"));
                        articlesMain.setArticales_type_created_date(obj.getString("articales_type_created_date"));
                        articleMainList.add(articlesMain);
                    }
                }
            } catch (Exception e) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                e.printStackTrace();
            }
            mAdapter = new ArticlesAdapter(ArticlesActivity.this, articleMainList);
//            rvArticleList.setAdapter(new ArticlesAdapter(ArticlesActivity.this, articleMainList));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvArticleList.setLayoutManager(mLayoutManager);
            rvArticleList.setItemAnimator(new DefaultItemAnimator());
            rvArticleList.setAdapter(mAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_language, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_english:
                Toast.makeText(getApplicationContext(), "English Selected", Toast.LENGTH_LONG).show();
                Util.setLocale(getApplicationContext(), "en");
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE, "en");
                return true;
            case R.id.select_arabic:
                Toast.makeText(getApplicationContext(), "Arabic Selected", Toast.LENGTH_LONG).show();
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE, "ar");
                Util.setLocale(getApplicationContext(), "ar");
                return true;
            default:
                return false;
        }
    }

}
