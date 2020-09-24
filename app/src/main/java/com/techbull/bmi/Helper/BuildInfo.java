package com.techbull.bmi.Helper;

import com.techbull.bmi.BuildConfig;

public class BuildInfo {
    public static boolean isPaid() {
        return BuildConfig.FLAVOR.equals("paid");
    }
}
