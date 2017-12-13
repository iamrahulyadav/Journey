package com.archi.archiinfo.journey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.adapter.ArticleDetailsAdapter;
import com.archi.archiinfo.journey.model.ArticleDetail;
import com.archi.archiinfo.journey.recyclerClick.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by archi_info on 1/5/2017.
 * http://web-medico.com/web2/Android_IOS_App_journey/api/get_Article.php?articales_type_id=2
 */

public class ArticleListActivity extends BaseActivity {
    private List<ArticleDetail> articleList;
    private RecyclerView recyclerView;
    private ArticleDetailsAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        init();
        if (Util.isOnline(getApplicationContext())) {
            new getArticlesList().execute();
        }


    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.rvArticleList_ArticleListActivity);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_ArticlesListActivity);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ArticleDetail article = articleList.get(position);
//                Toast.makeText(getApplicationContext(), article.getName() + " is selected!", Toast.LENGTH_SHORT).show();
//                Gson gson = new Gson();
//                Bundle bundle = new Bundle();
//                bundle.putString("article", gson.toJson(article));

                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_NAME, article.getName());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_ID, article.getId());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_DATETIME, article.getDateTime());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_IMG, article.getOther_image());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_VIDEO, article.getVideo());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_DESC, article.getDescription());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_ADDIMG, article.getAdd_image());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_LIKE, article.getLike());
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_DISLIKE, article.getDislike());

                Intent intent = new Intent(ArticleListActivity.this, ArticlesDetailsActivity.class);
//                intent.putExtra("article_name", article.getName());
//                intent.putExtra("article_time", article.getDateTime());
//                intent.putExtra("article_image", article.getImage());
//                intent.putExtra("article_video", article.getVideo());
//                intent.putExtra("article_desc", article.getDescription());
//                intent.putExtra("article_add_image", article.getAdd_image());
                startActivity(intent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onBackPressed() {
        startActivity(ArticlesActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    public class getArticlesList extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ArticleListActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            articleList = new ArrayList<>();
            articleList.clear();
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";

            HashMap<String, String> hashmap = new HashMap<String, String>();
            String article_trype = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_TYPE);
            hashmap.put("articales_type_id", article_trype);
            response = Util.getResponseofPost(Constant.BASE_URL + "get_Article.php", hashmap);
//                urlString = Constant.BASE_URL + "login.php?email=" + usernamestr + "&password=" + passwordstr;
            Log.e("URL ", ">>" + Constant.BASE_URL + "get_Article.php?articales_type_id=" + article_trype);
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
                        ArticleDetail articles = new ArticleDetail();
                        articles.setId(obj.getString("articles_id"));
                        articles.setAdd_image(obj.getString("articles_advertisement"));
                        articles.setName(obj.getString("articles_name"));
                        articles.setDateTime(obj.getString("articles_created_date"));
                        articles.setDescription(obj.getString("description"));
                        articles.setImage(obj.getString("articles_image"));
                        articles.setOther_image(obj.getString("articles_image2"));
                        articles.setVideo(obj.getString("articles_video"));
                        articles.setLike(obj.getString("Like"));
                        String like=obj.getString("Like");
                        articles.setDislike(obj.getString("disLike"));
                        articles.setAuthorName(obj.getString("articles_author"));
                        articleList.add(articles);
                    }

                    mAdapter = new ArticleDetailsAdapter(getApplicationContext(), articleList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
