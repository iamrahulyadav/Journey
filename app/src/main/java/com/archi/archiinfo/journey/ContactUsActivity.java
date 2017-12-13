package com.archi.archiinfo.journey;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.model.ContactUs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by archi_info on 1/4/2017.
 * http://web-medico.com/web2/Android_IOS_App_journey/api/get_contact_us.php
 */

public class ContactUsActivity extends BaseActivity implements View.OnClickListener {
    private CoordinatorLayout coordinatorLayout;
    public ArrayList<ContactUs> contactUsList;
    public HashMap<String, String> hashmap;
    private TextView tvName, tvAddess, tvContact, tvEmail, tvFb, tvTwitt, tvInsta;
    public String email, snap_chat, twitter, facebook, youtube, instagram;
    public ImageView twitterIv, youtubeIv, instagramIv;
    private TextView snapChatTv, emailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        init();
        if (Util.isOnline(getApplicationContext())) {
            new getContactUsDetails().execute();
        }
    }

    private void init() {
        // TODO: 14-Feb-17 init widget
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_ContactUsActivity);
        emailTv = (TextView) findViewById(R.id.activity_contact_us_email_tv);
//        webLinear = (LinearLayout) findViewById(R.id.activity_contactus_snap_chat);
        twitterIv = (ImageView) findViewById(R.id.activity_contactus_twitter);
        youtubeIv = (ImageView) findViewById(R.id.activity_contactus_youtube);
        instagramIv = (ImageView) findViewById(R.id.activity_contactus_insta);


        emailTv.setOnClickListener(this);
//        webLinear.setOnClickListener(this);
        twitterIv.setOnClickListener(this);
        youtubeIv.setOnClickListener(this);
        instagramIv.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_contact_us_email_tv:

                PackageManager pm = getPackageManager();
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("*/*");
                List<ResolveInfo> resInfo = pm.queryIntentActivities(myIntent, 0);
                for (int i = 0; i < resInfo.size(); i++) {
                    ResolveInfo ri = resInfo.get(i);
                    if (ri.activityInfo.packageName.contains("android.gm")) {
                        myIntent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                        myIntent.setAction(Intent.ACTION_SEND);
                        myIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        myIntent.setType("message/rfc822");
                        myIntent.putExtra(Intent.EXTRA_TEXT, "");
                        myIntent.putExtra(Intent.EXTRA_SUBJECT, "Journey and history");
                    }
                }
                startActivity(myIntent);

                break;
//            case R.id.activity_contactus_snap_chat:
//
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(snap_chat));
//                startActivity(i);
//
//                break;
            case R.id.activity_contactus_twitter:
                    Intent in = new Intent(Intent.ACTION_VIEW);
                    in.setData(Uri.parse(twitter));
                    startActivity(in);
                break;
            case R.id.activity_contactus_youtube:
                try {
                    String video_path = "https://www.youtube.com/user/awatifalrefaie";
                    Intent youTubeIntent = new Intent(Intent.ACTION_VIEW);
                    youTubeIntent.setData(Uri.parse(youtube));
                    startActivity(youTubeIntent);
                    Toast.makeText(this,youtube,Toast.LENGTH_LONG).show();


                    //   youtubeIv.setEnabled(true);
                      /* intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_path));
                    startActivity(intent);*/
                   /* else {
                        youtubeIv.setEnabled(false);
                        Toast.makeText(this,"Video is not available",Toast.LENGTH_LONG).show();

                    }*/
                } catch (ActivityNotFoundException e) {

                  //  intent = new Intent(Intent.ACTION_VIEW);
                   // intent.setData(Uri.parse(youtube));
                   // startActivity(intent);
                }

                break;

            /*case R.id.activity_contactus_fb:

                Intent inFb = new Intent(Intent.ACTION_VIEW);
                inFb.setData(Uri.parse(facebook));
                startActivity(inFb);

                break;*/

            case R.id.activity_contactus_insta:

                Intent inInsta = new Intent(Intent.ACTION_VIEW);
                inInsta.setData(Uri.parse(instagram));
                startActivity(inInsta);

                break;

        }
    }

    public class getContactUsDetails extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(ContactUsActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            contactUsList = new ArrayList<>();
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            response = Util.getResponseofGet(Constant.BASE_URL + "get_contact_us.php");
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            pd.dismiss();
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {

                    JSONArray arry = object.getJSONArray("data");
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
                        ContactUs contact = new ContactUs();
                        contact.setContactus_id(obj.get("contactus_id").toString());

                        contact.setName(obj.get("name").toString());
                        contact.setPhone(obj.get("phone").toString());
                        contact.setEmail(obj.get("email").toString());
                        contact.setFacebook(obj.get("facebook_account").toString());
                        contact.setTwitter(obj.get("twitter_account").toString());
                        twitter = String.valueOf(obj.get("twitter_account"));
                        facebook = String.valueOf(obj.get("facebook_account"));
                        instagram = obj.get("instagram_account").toString();
                        youtube = String.valueOf((obj.get("youtube")));
                        snap_chat = String.valueOf(obj.get("snap_chat"));
                        email = String.valueOf(obj.get("email"));

                        emailTv.setText(email);
                        snapChatTv.setText("" + snap_chat);

                        contactUsList.add(contact);
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();

            }

        }
    }

//    private void setData(String aboutValue) {
//        TextView et = new TextView(ContactUsActivity.this);
//        et.setPadding(5, 5, 5, 5);
//        et.setTextSize(20);
//        et.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
//        et.setText(aboutValue);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        llMainLayout.addView(et, lp);
//    }

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

    public String checkURL(String url) {


        if (!url.startsWith("www.") && !url.startsWith("http://")) {
            url = "www." + url;
        }
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }

        return url;
    }
}
