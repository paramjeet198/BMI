package com.techbull.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.WalkThrough.GetStarted;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme_Transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        FirstRun();
    }

    private void FirstRun() {
        boolean isFirstRun = Prefs.getBoolean(Keys.KEY_IS_FIRST_RUN, true);
        if (isFirstRun) {
            startActivity(new Intent(this, GetStarted.class));
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }, 2000);

        }
    }
}