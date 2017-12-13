package com.archi.archiinfo.journey;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;

/**
 * Created by archi_info on 1/4/2017.
 */

public class SplashActivity extends RuntimePermissionsActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    Thread splashTread;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE, "ar");
        Util.setLocale(getApplicationContext(), "ar");
        SplashActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        StartAnimations();

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    startActivity(MainActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }

            }
        };
        splashTread.start();
    }
}
