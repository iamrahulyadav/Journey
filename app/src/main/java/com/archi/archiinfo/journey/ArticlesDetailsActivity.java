package com.archi.archiinfo.journey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import static android.R.attr.name;

/**
 * Created by archi_info on 1/4/2017.
 * http://web-medico.com/web2/Android_IOS_App_journey/api/post_Article.php?articles_name=facebook&articles_description=event data&articles_img_vid=1.jpg&articales_type_id=2
 * <p>
 * https://i.diawi.com/SwcoSF
 */

public class ArticlesDetailsActivity extends BaseActivity {
    public TextView etArticleName, etDesciption, txtVwLike;
    private CoordinatorLayout coordinatorLayout;
    private ImageView ivAddImage, ivArticleImage;
    VideoView vwArticleVideo;
    public TextView tvVideoLink;
    public String articleNameStr, dateTimeStr, descriptionStr, imagePath, videoPath, addImageStr, like, disLike;
    LinearLayout llVideo;
    public ImageView ivPlayVideo;
    Button imgbtndisLike, imgbtnShare, imgbtnLike;
    String edtTxtDialogStr;
    final Context context = this;
    int isLike = 0;
    int isDislike = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        getData();
        init();


//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//// textView is the TextView view that should display it
//        etDateTime.setText(currentDateTimeString);
    }

    private void getData() {
//        Gson gson = new Gson();
        articleNameStr = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_NAME);
        dateTimeStr = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_DATETIME);
        descriptionStr = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_DESC);
        imagePath = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_IMG);
        videoPath = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_VIDEO);
        addImageStr = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_ADDIMG);
        like = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_LIKE);
        disLike = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_DISLIKE);
        Log.e("NAME", "" + name);

//        Bundle bundle = getIntent().getExtras();
//        articleNameStr = bundle.getString("article_name");
//        dateTimeStr = bundle.getString("article_time");
//        descriptionStr = bundle.getString("article_desc");
//        imagePath = bundle.getString("article_image");
//        videoPath = bundle.getString("article_video");
//        addImageStr = bundle.getString("article_add_image");
//        Log.e("NAME", "" + name);


    }


    private void init() {
//        ivAdd = (ImageView) findViewById(R.id.ivAddFromServer);
        etArticleName = (TextView) findViewById(R.id.etArticleName_ArticlesDetailsActivity);
        etDesciption = (TextView) findViewById(R.id.etDescription_ArticlesDetailsActivity);
        ivAddImage = (ImageView) findViewById(R.id.ivAddImage);
        ivArticleImage = (ImageView) findViewById(R.id.ivArticleImage);
//        tvVideoLink = (TextView) findViewById(R.id.tvVideoArticel);
      //  vwArticleVideo = (VideoView) findViewById(R.id.vdo);
       // llVideo = (LinearLayout) findViewById(R.id.llVideoArticleDetails);
        ivPlayVideo = (ImageView) findViewById(R.id.ivVideoPlay);
        imgbtnLike = (Button) findViewById(R.id.imgbtnLike);
        imgbtndisLike = (Button) findViewById(R.id.imgbtndisLike);
        imgbtnShare = (Button) findViewById(R.id.ivShare);
        imgbtnLike.setText(like);
        imgbtndisLike.setText(disLike);

        // ivPlayVideo.setOnClickListener(this);
        //  Toast.makeText(ArticlesDetailsActivity.this,v,Toast.LENGTH_LONG).show();


        etArticleName.setText(getString(R.string.article) + "  :    " + articleNameStr);
        etDesciption.setText(descriptionStr);

        if (!addImageStr.equalsIgnoreCase("")) {
            Picasso.with(getApplicationContext()).load(addImageStr).placeholder(R.drawable.default_img).into(ivAddImage);
        }

        if (!imagePath.equalsIgnoreCase("")) {
            ivArticleImage.setVisibility(View.VISIBLE);
            Picasso.with(getApplicationContext()).load(imagePath).placeholder(R.drawable.default_img).into(ivArticleImage);
        }

        if (!videoPath.equals("")) {

            Picasso.with(getApplicationContext()).load(R.drawable.ic_video).placeholder(R.drawable.ic_video).into(ivPlayVideo);

            ivPlayVideo.setVisibility(View.VISIBLE);
            ivPlayVideo.setOnClickListener
                    (new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             startActivity(VideoWatchActivity.class);
                             overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                             finish();
                     /*//    tvVideoLink.setVisibility(View.VISIBLE);
                        llVideo.setVisibility(View.VISIBLE);
                        vwArticleVideo.setVisibility(View.VISIBLE);
                        //      vwArticleVideo.setVideoURI(Uri.parse(videoPath));
                        //      vwArticleVideo.seekTo(20);

                      //  String link="http://journeyandhistory.com/uploads/video/100_1416742644.mp4";
                        MediaController mc = new MediaController(ArticlesDetailsActivity.this);
                        mc.setAnchorView(vwArticleVideo);
                        mc.setMediaPlayer(vwArticleVideo);
                        uri = Uri.parse(videoPath);
                        vwArticleVideo.setMediaController(mc);
                        vwArticleVideo.setVideoURI(uri);
                        vwArticleVideo.requestFocus();
                        vwArticleVideo.start();*/
                             // Toast.makeText(ArticlesDetailsActivity.this,"Video available",Toast.LENGTH_LONG).show();
                         }
                     }
                    );

        } else {
            ivPlayVideo.setVisibility(View.GONE);
        }
        imgbtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, descriptionStr);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share"));
            }
        });


        imgbtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.alertdialog_custom, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        if(!userInput.getText().toString().equals("")) {
                                            edtTxtDialogStr = userInput.getText().toString();
                                            new getLikeDetails().execute();
                                        }else
                                            {
                                            Toast.makeText(getApplicationContext(), "يرجى إدخال البريد الإلكتروني", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        imgbtndisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.alertdialog_custom, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        if(!userInput.getText().toString().equals("")) {
                                            edtTxtDialogStr = userInput.getText().toString();
                                            new getDisLikeDetails().execute();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "يرجى إدخال البريد الإلكتروني", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(ArticleListActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        //  imgbtnLike.setText(like);

    }
/*@Override
    public void onClick(View view) {
        startActivity(VideoWatchActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }*/

    public class getLikeDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            String article_trype = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_ID);
            response = Util.getResponseofGet(Constant.BASE_URL + "article_like.php?articale_id=" + article_trype + "&user_email=" + edtTxtDialogStr + "&islike=1");
//         Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                object.getString("status");
                object.getString("msg");
                String msg = object.getString("msg");
                String status = object.getString("status");
                isLike= Integer.parseInt(imgbtnLike.getText().toString());
                isLike= isLike+1;
                int value=isLike;

                imgbtnLike.setText(String.valueOf(value));
               // value= Integer.parseInt(txtVwLike.getText().toString());
          //      value=value+1;
                System.out.println(value);
                //txtVwLike.setText(String.valueOf(id));

                //  imgbtnLike.setText(String.valueOf(isLike));

                //  Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }
    }

    public class getDisLikeDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String response = "";
            String article_trype = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_ID);
            response = Util.getResponseofGet(Constant.BASE_URL + "article_like.php?articale_id=" + article_trype + "&user_email=" + edtTxtDialogStr + "&islike=0");
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                object.getString("status");
                object.getString("msg");
                String msg = object.getString("msg");
                String status = object.getString("status");
                isDislike= Integer.parseInt(imgbtndisLike.getText().toString());
                isDislike= isDislike+1;
                int value=isDislike;

                imgbtndisLike.setText(String.valueOf(value));
                System.out.println(value);
                //     Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }
    }

   /* public void data (View view) {
        minteger= Integer.parseInt(like);//10
       // txtVwLike.setText(minteger);//10
        minteger= minteger++;//11
       // data++;
        int data= (minteger);
        display(data);
    }

    private void display(int number) {
        System.out.println(number);
       // displayInteger.setText("" + number);
    }*/
}