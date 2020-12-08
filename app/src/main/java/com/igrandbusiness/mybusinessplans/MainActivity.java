package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    BottomSheetBehavior bottomSheetBehavior;
    ConstraintLayout bottom;
    TextView greeting;
    String phone;
    ImageView faceb,twitter,telegram,mail,linkedin;
    LinearLayoutCompat videos,magazine,podcasts,businessPlans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greeting = findViewById(R.id.greeting);
        bottom = findViewById(R.id.bottom);
        faceb = findViewById(R.id.fb);
        videos = findViewById(R.id.videos);
        magazine = findViewById(R.id.magazine);
        podcasts = findViewById(R.id.podcasts);
        businessPlans = findViewById(R.id.business_plans);
        twitter = findViewById(R.id.twitter);
        telegram = findViewById(R.id.telegram);
        mail = findViewById(R.id.mail);
        linkedin = findViewById(R.id.linkedin);
        phone = "+254795801971";
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomNavigation);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_call:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+ phone));
                                startActivity(intent);
                                return true;
                            case R.id.navigation_media:
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                return true;
                        }
                        return false;
                    }
                };
        greetUser();
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        magazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        podcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        businessPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        faceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void greetUser() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay<12){
            greeting.setText("Good morning");
        }else if (timeOfDay >=12 && timeOfDay < 16){
            greeting.setText("Good afternoon");
        }else if (timeOfDay >= 16 && timeOfDay < 24) {
            greeting.setText("Good evening");
        }
    }
}
