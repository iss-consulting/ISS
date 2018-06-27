package com.iss.flavr.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.iss.flavr.R;
import com.iss.flavr.data.repository.local.session.SessionManager;
import com.iss.flavr.presentation.home.HomeActivity;
import com.iss.flavr.presentation.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    // Set the duration of the splash screen
    public final String TAG = SplashActivity.class.getSimpleName();
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private SessionManager sessionManager;
    private String url = "https://play.google.com/store/apps/details?id=com.capsuladigital.perumundial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*Glide.with(getApplicationContext())
                .load(R.drawable.anim_logo)
                .into(ivLogo);*/

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /*if (hasActiveInternetConnection()) {
                    try {
                        if (BuildConfig.VERSION_NAME.equals( VersionChecker.getPlayStoreAppVersion(url + "&hl=en"))) {
                            launchProperActivity();
                        }else{
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    notifyUpdate();
                                }
                            });
                        }
                    } catch (IOException e) {
                        Log.e("gg", e.toString());*/
                launchProperActivity();
                    /*}
                } else {
                    launchProperActivity();

                }
            }

             }*/

            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);


    }

    private void launchProperActivity() {
        sessionManager = SessionManager.getInstance(getApplicationContext());
        if (sessionManager.isActive()) {
            launchActivity(getApplicationContext(), HomeActivity.class);
        } else {
            launchActivity(getApplicationContext(), LoginActivity.class);

        }

    }

    private void launchActivity(Context origin, Class destiny) {
        Intent i = new Intent(origin, destiny);
        startActivity(i);
        //overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }
}



