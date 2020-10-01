package com.techbull.bmi;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;
import com.techbull.bmi.Helper.BuildInfo;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.WalkThrough.GetStarted;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme_Transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //Do not touch code below this
        // Check Sale Paid and Modify Variable in BuildInfo File names salePaid to true
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        try {
            long firstInstall = getPackageManager().getPackageInfo(getPackageName(), 0).firstInstallTime;
            Log.i(" Date ", "First Install : " + firstInstall);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            long comparedate = formatter.parse("20/09/2020").getTime();
            Log.i(" Date ", "Compared Date : " + comparedate);
            if(firstInstall < comparedate) {
                BuildInfo.salePaid = true;
                Log.i(" Date ", "Sale Paid : true");
            }


        } catch (PackageManager.NameNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        // Do not touch code above this
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

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

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale localeToSwitchTo = new Locale(Prefs.getString("language", ""));
        ContextWrapper localeUpdatedContext = ContextUtils.updateLocale(newBase, localeToSwitchTo);
        super.attachBaseContext(localeUpdatedContext);
    }
}