package com.archi.archiinfo.journey;

import android.net.Uri;
import android.os.Bundle;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

/**
 * Created by archi_info on 1/16/2017.
 */

public class VideoWatchActivity extends BaseActivity {
    private String videoPath;
    FullscreenVideoLayout myVideoView;
    Uri uri;
    //private int position = 0;
  //  private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_watch);
        myVideoView=(FullscreenVideoLayout)findViewById(R.id.videoview);
        init();
    }

    private void init() {
        videoPath = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_ARTICLE_VIDEO);

        try {
          //  myVideoView.setVideoURI(Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp"));
            myVideoView.setVideoURI(Uri.parse(videoPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  Toast.makeText(LoginSignupDashboard.this, object.getString("link"), Toast.LENGTH_SHORT).show();


        // myVideoView = (FullscreenVideoLayout) findViewById(R.id.vcv_img_fullscreen);
        //(myVideoView.findViewById(R.id.vcv_img_fullscreen)).setVisibility(View.INVISIBLE);

        /*MediaController mc = new MediaController(VideoWatchActivity.this);
        mc.setAnchorView(vwArticleVideo);
        mc.setMediaPlayer(vwArticleVideo);
        uri = Uri.parse(videoPath);
        vwArticleVideo.setMediaController(mc);
        vwArticleVideo.setVideoURI(uri);
        vwArticleVideo.requestFocus();
        vwArticleVideo.start();*/



        /*if (mediaControls == null) {
            mediaControls = new MediaController(VideoWatchActivity.this);
        }*/


        //   myVideoView.requestFocus();
        /*myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                myVideoView.seekTo(position);
                if (position == 0) {
                //    myVideoView.start();
                } else {
                ///    myVideoView.stopPlayback();
                }
            }
*/    }

    @Override
    public void onBackPressed() {
        startActivity(ArticlesDetailsActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }*/
}
