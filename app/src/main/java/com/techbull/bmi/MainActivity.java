package com.techbull.bmi;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.techbull.bmi.WalkThrough.WalkThrough;
import com.techbull.bmi.ui.BMI_HOME.BmiFragment;
import com.techbull.bmi.ui.gallery.GalleryFragment;
import com.techbull.bmi.ui.slideshow.SlideshowFragment;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Fragment fragment1, fragment2, fragment3, active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirstRun();

        final DrawerLayout drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFragments(R.id.nav_bmi);
        navigationView.getMenu().getItem(0).setChecked(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
//        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Body Mass Index");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_bmi:
                        loadFragments(R.id.nav_bmi);
                        navigationView.getMenu().getItem(0).setChecked(true);
                        navigationView.getMenu().getItem(1).setChecked(false);
                        navigationView.getMenu().getItem(2).setChecked(false);
                        break;
                    case R.id.nav_bmr:
                        Toast.makeText(MainActivity.this, "We are Working", Toast.LENGTH_SHORT).show();
//                        loadFragments(R.id.nav_bmr);
//                        navigationView.getMenu().getItem(1).setChecked(true);
//                        navigationView.getMenu().getItem(0).setChecked(false);
//                        navigationView.getMenu().getItem(2).setChecked(false);
                        break;
                    case R.id.nav_fat_percent:
                        Toast.makeText(MainActivity.this, "We are Working on this part.", Toast.LENGTH_SHORT).show();
////                        loadFragments(R.id.nav_fat_percent);
//                        navigationView.getMenu().getItem(2).setChecked(true);
//                        navigationView.getMenu().getItem(0).setChecked(false);
//                        navigationView.getMenu().getItem(1).setChecked(false);
                        break;
                    case R.id.water_tracking:
                        Toast.makeText(MainActivity.this, "Under development", Toast.LENGTH_SHORT).show();
////                        loadFragments(R.id.nav_fat_percent);
//                        navigationView.getMenu().getItem(2).setChecked(true);
//                        navigationView.getMenu().getItem(0).setChecked(false);
//                        navigationView.getMenu().getItem(1).setChecked(false);
                        break;

                    case R.id.rate:
                        Intent rate;
                        rate = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.techbull.bmi"));
                        startActivity(rate);
                        break;
                    case R.id.moreApps:
                        Intent moreApps = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=TechBull+Corporation"));
                        startActivity(moreApps);
                        break;
                    case R.id.update:
                        Intent update = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + ""));
                        startActivity(update);
                        break;

//            case R.id.moreApps:
//                Intent moreApps = new Intent(this, MoreApps.class);
//                startActivity(moreApps);
//                break;
                    case R.id.contact:
                        String deviceName = Build.MANUFACTURER + " " + Build.MODEL;
                        String release = Build.VERSION.RELEASE;
                        int sdkVersion = Build.VERSION.SDK_INT;
                        int versionCode = BuildConfig.VERSION_CODE;
                        String versionName = BuildConfig.VERSION_NAME;
                        String locale = getResources().getConfiguration().locale.getCountry();

                        String line = "---------------------------";
                        String data = "Device name : " + deviceName + "\n App version: " + versionCode + " " + " (" + versionName + ")" + "\nAndroid SDK: " + sdkVersion + " (" + release + ")" + "\n locale: " + locale + " \n " + line + "\n" + "Write From Here\n" + line + "\n";

                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"techbullteam@gmail.com"});
                        intent.setType("message");
                        intent.setPackage("com.google.android.gm");
                        intent.putExtra(Intent.EXTRA_TEXT, data);
                        intent.putExtra(Intent.EXTRA_SUBJECT, getPackageName());
//            intent.putExtra(Intent.EXTRA_TEXT, "Write Your Message here");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.shareApp:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, "Check out Awesome BMI app at: \n https://play.google.com/store/apps/details?id=" + getPackageName() + " ");
                        try {
                            startActivity(Intent.createChooser(share, "Share With your Friends ..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void FirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);

        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, WalkThrough.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
    }

    void loadFragments(int id) {
        FragmentTransaction sfm = getSupportFragmentManager().beginTransaction();
        if (active != null) {
            sfm.hide(active);
        }

        switch (id) {
            case R.id.nav_bmi:
                if (fragment1 == null) {
                    fragment1 = new BmiFragment();
                    sfm.add(R.id.nav_host_fragment, fragment1);
                }
                active = fragment1;
                break;
            case R.id.nav_bmr:
                if (fragment2 == null) {
                    fragment2 = new GalleryFragment();
                    sfm.add(R.id.nav_host_fragment, fragment2);
                }
                active = fragment2;
                break;
            case R.id.nav_fat_percent:
                if (fragment3 == null) {
                    fragment3 = new SlideshowFragment();
                    sfm.add(R.id.nav_host_fragment, fragment3);
                }
                active = fragment3;
                break;
        }
        if (active == null) {
            return;
        }
        sfm.show(active);
        sfm.commit();

    }

}