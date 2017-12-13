package com.archi.archiinfo.journey;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.model.JhonyHistory;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archi_info on 1/4/2017.
 * http://hire-people.com/host2/Android_IOS_App_journey/api/get_joureny_history.php
 */

public class JhonyAndHistoryActivity extends BaseActivity {
    ArrayList<JhonyHistory> jonyHistoryList;
    CoordinatorLayout coordinatorLayout;
    ImageView ivBookImage;
    TextView tvBookDesc, tvBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhony_and_history);
        init();
        if (Util.isOnline(getApplicationContext())) {
            new getJonyHistoryDetails().execute();
        }
//        http://hire-people.com/host2/Android_IOS_App_journey/api/get_joureny_history.php
    }

    private void init() {
        ivBookImage = (ImageView) findViewById(R.id.ivBookCoverPic_JohnyHistoryActivity);
        tvBookDesc = (TextView) findViewById(R.id.tvBookDesc_JhoneyHistoryActivity);
        tvBookTitle = (TextView) findViewById(R.id.tvBookTitle_JohnnyHistoryActivity);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_JhoneyHistoryActivity);

    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
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


    public class getJonyHistoryDetails extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(JhonyAndHistoryActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            jonyHistoryList = new ArrayList<>();
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            response = Util.getResponseofGet(Constant.BASE_URL + "get_joureny_history.php");
//                urlString = Constant.BASE_URL + "login.php?email=" + usernamestr + "&password=" + passwordstr;
            Log.e("URL ", ">>" + Constant.BASE_URL + "get_joureny_history.php");
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
                        JhonyHistory journey = new JhonyHistory();
                        journey.setBookDescription(obj.get("history_desc").toString());
                        journey.setBookImage(obj.get("history_image").toString());
                        journey.setHistoryName(obj.get("history_name").toString());
                        jonyHistoryList.add(journey);
                    }
                } else {
                    Toast.makeText(JhonyAndHistoryActivity.this, R.string.message_error, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setData();
        }

        private void setData() {
            tvBookDesc.setText(jonyHistoryList.get(0).getBookDescription());
            Picasso.with(JhonyAndHistoryActivity.this).load(jonyHistoryList.get(0).getBookImage()).placeholder(R.drawable.default_img).into(ivBookImage);
            tvBookTitle.setText(jonyHistoryList.get(0).getHistoryName());
//            Log.d("HistoryName", jonyHistoryList.get(0).getHistoryName());
        }
    }
}
