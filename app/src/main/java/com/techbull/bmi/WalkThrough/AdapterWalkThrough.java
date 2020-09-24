package com.techbull.bmi.WalkThrough;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.techbull.bmi.WalkThrough.fragments.FragmentChooseGender;
import com.techbull.bmi.WalkThrough.fragments.FragmentChooseHeight;
import com.techbull.bmi.WalkThrough.fragments.FragmentChooseLanguage;
import com.techbull.bmi.WalkThrough.fragments.FragmentChooseWeight;
import com.techbull.bmi.WalkThrough.fragments.FragmentSelectAge;

public class AdapterWalkThrough extends FragmentPagerAdapter {

    public AdapterWalkThrough(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentChooseLanguage.newInstance("", "");
            case 1:
                return FragmentChooseGender.newInstance("", "");
            case 2:
                return FragmentSelectAge.newInstance("", "");
            case 3:
                return FragmentChooseWeight.newInstance("", "");
            case 4:
                return FragmentChooseHeight.newInstance("", "");
            default:
                return FragmentChooseHeight.newInstance("", "");

        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
