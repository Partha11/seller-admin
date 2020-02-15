package com.supernova.selleradmin.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.util.SharedPrefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        changeActivity();
    }

    private void changeActivity() {

        SharedPrefs prefs = new SharedPrefs(this);

        new Handler().postDelayed(() -> {

            prefs.setIsFirstInstall(true);

            if (prefs.isLoggedIn()) {


            } else {

                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_right, R.anim.stay);
                finish();
            }

        }, prefs.isFirstInstall() ? 2000 : 1500);
    }
}
