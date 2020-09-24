package com.techbull.bmi.Helper;

import android.content.Context;

import com.techbull.bmi.BuildConfig;

public class BuildInfo {

    public static boolean salePaid = false;

    public static boolean isPaid() {
        if(salePaid) {
            return true;
        }

        return BuildConfig.FLAVOR.equals("paid");
    }

}
