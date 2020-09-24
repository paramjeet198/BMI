package com.techbull.bmi.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class AdManager {


    public static boolean isShow(Context context) {
        if (BuildInfo.isPaid()) {
            return false;
        } else {
            SharedPreferences pref = context.getSharedPreferences("adManagement", Context.MODE_PRIVATE);
            long time = pref.getLong("ad_disable_till", 0);
            return time <= System.currentTimeMillis();
        }
    }
}
