package com.techbull.bmi.WalkThrough;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import com.techbull.bmi.R;

public class WalkThrough extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageView backBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.walk_through_background));


        WormDotsIndicator dotsIndicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
        viewPager = findViewById(R.id.viewpager);
        backBtn = findViewById(R.id.backBtn);
        viewPager.setAdapter(new AdapterWalkThrough(getSupportFragmentManager()));
        dotsIndicator.setViewPager(viewPager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
    }

    public void updatePosition(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }
}