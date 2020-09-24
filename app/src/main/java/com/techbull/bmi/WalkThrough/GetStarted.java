package com.techbull.bmi.WalkThrough;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.techbull.bmi.R;

public class GetStarted extends AppCompatActivity {
    private Button nextBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.walk_through_background));

        nextBtn = findViewById(R.id.nextBtn);
        progressBar = findViewById(R.id.progressbar);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(), WalkThrough.class);
                        startActivity(intent);
                        nextBtn.setVisibility(View.VISIBLE);
                        finish();
                    }
                }, 1000);

            }
        });
    }
}