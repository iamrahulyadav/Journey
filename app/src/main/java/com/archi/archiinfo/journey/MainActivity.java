package com.archi.archiinfo.journey;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.calendar.MainCalendar;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout llArticles, llAboutus, lljohnnyAndHistory, llEvents, llContactUs;
    private TextView tvArticles, tvAboutus, tvJonny, tvEvents, tvContactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    private void init() {
        tvArticles = (TextView) findViewById(R.id.tvArticle_MainActivity);
        tvAboutus = (TextView) findViewById(R.id.tvAboutus_MainActivity);
        tvJonny = (TextView) findViewById(R.id.tvJohnnyHistory_MainActivity);
        tvEvents = (TextView) findViewById(R.id.tvEvents_MainActivity);
        tvContactus = (TextView) findViewById(R.id.tvContctus_MainActivity);

//        Typeface font = Typeface.createFromAsset(getAssets(), "mobilefont.ttf");
//        ((TextView)findViewById(R.id.tvContctus_MainActivity_1)).setTypeface(font);

        tvArticles.setOnClickListener(this);
        tvAboutus.setOnClickListener(this);
        tvContactus.setOnClickListener(this);
        tvJonny.setOnClickListener(this);
        tvEvents.setOnClickListener(this);
        tvEvents.setOnClickListener(this);

//        llArticles = (LinearLayout) findViewById(R.id.llArticles_MainActivity);
//        llAboutus = (LinearLayout) findViewById(R.id.llAboutus_MainActivity);
//        lljohnnyAndHistory = (LinearLayout) findViewById(R.id.llJhonnyAndHistory_MainActivity);
//        llEvents = (LinearLayout) findViewById(R.id.llEvents_MainActivity);
//        llContactUs = (LinearLayout) findViewById(R.id.llContactUs_MainActivity);
//        llArticles.setOnClickListener(this);
//        llAboutus.setOnClickListener(this);
//        lljohnnyAndHistory.setOnClickListener(this);
//        llEvents.setOnClickListener(this);
//        llContactUs.setOnClickListener(this);
//        if (!Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE).equalsIgnoreCase("")) {
//            updateViews(Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE));
//        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvArticle_MainActivity:
                startActivity(ArticlesActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.tvAboutus_MainActivity:
                startActivity(AboutUsActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.tvJohnnyHistory_MainActivity:
                startActivity(JhonyAndHistoryActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.tvEvents_MainActivity:
                startActivity(MainCalendar.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.tvContctus_MainActivity:
                startActivity(ContactUsActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

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

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(LocaleHelper.onAttach(base));
//    }


//    private void updateViews(String languageCode) {
//        Context context = LocaleHelper.setLocale(this, languageCode);
//        Resources resources = context.getResources();
//
//        tvArticles = (TextView) findViewById(R.id.tvArticle_MainActivity);
//        tvAboutus = (TextView) findViewById(R.id.tvAboutus_MainActivity);
//        tvJonny = (TextView) findViewById(R.id.tvJohnnyHistory_MainActivity);
//        tvEvents = (TextView) findViewById(R.id.tvEvents_MainActivity);
//        tvContactus = (TextView) findViewById(R.id.tvContctus_MainActivity);
//    }

}
