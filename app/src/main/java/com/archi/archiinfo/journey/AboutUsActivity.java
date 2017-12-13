package com.archi.archiinfo.journey;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.model.Aboutus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by archi_info on 1/4/2017.
 */

public class AboutUsActivity extends BaseActivity {
    private CoordinatorLayout coordinatorLayout;
    ArrayList<Aboutus> aboutUsList;
    private HashMap<String, String> hashmap;
    private ImageView ivAboutus;
    private LinearLayout llMainLayout;
    private String[] aboutDataList;
    private TextView tvComapnyName, tvAddress, tvContent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/abc.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_about_us);
        init();
        if (Util.isOnline(getApplicationContext())) {
            new getAboutUsDetails().execute();
        }
    }

    private void init() {
        llMainLayout = (LinearLayout) findViewById(R.id.llMainLayout_AboutUsActivity);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_AboutUsActivity);
        tvComapnyName = (TextView) findViewById(R.id.tvCompanyName_AboutusActivity);
        tvAddress = (TextView) findViewById(R.id.tvAddress_AboutActivity);
        tvContent = (TextView) findViewById(R.id.tvContent_AboutActivity);
    }


//    private void setData(String setValue) {
//        TextView et = new TextView(this);
//        et.setPadding(5, 5, 5, 5);
//        et.setTextSize(20);
//        et.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
//        et.setText(setValue);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        llMainLayout.addView(et, lp);
//    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public class getAboutUsDetails extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AboutUsActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            aboutUsList = new ArrayList<>();
            aboutUsList.clear();
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            response = Util.getResponseofGet(Constant.BASE_URL + "get_about_us.php");
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            pd.dismiss();
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").equalsIgnoreCase("true")) {
                    JSONArray arry = object.getJSONArray("data");
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(0);
                        Aboutus about = new Aboutus();
                        about.setName(obj.get("name").toString());
//                        setData(obj.get("name").toString());
                        tvComapnyName.setText(obj.get("name").toString());

                        about.setContent(obj.get("content").toString());
//                        setData(obj.get("content").toString());
                        String data=(obj.get("content").toString());
                        String cleaned = data.replaceAll("&nbsp;"," ").replaceAll("&quot;","");

                        System.out.println(data);
                        tvContent.setText(cleaned);
//                        String data = "<h1>تطبيقي ثقافي تاريخي يساهم في نشر المقالات ذات الطابع التاريخي و تقديم العديد من المواضيع التاريخيه لكل متخصص و مهتم.تطبيقي يساهم في تسليط الضوء على المقالات التاريخيه عن طريق مشاركتكم لنا مواضيعكم التخصصيه و رحلاتكم و زياراتكم التاريخيه.شاركوا و استفيدوا من كل ما يقدمه التطبيق و هو ما يسعدني كثيراً.\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\t\\tعواطف الرفاعي</h1>";
//                        tvContent.setText(Html.fromHtml(data));
                        about.setAddress(obj.get("extra_message").toString());
//                        setData(obj.get("address").toString());
                        tvAddress.setText(obj.get("extra_message").toString());

                        aboutUsList.add(about);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                pd.dismiss();
            }
        }


    }


}
