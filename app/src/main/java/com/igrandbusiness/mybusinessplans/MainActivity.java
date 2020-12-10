package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    BottomSheetBehavior bottomSheetBehavior;
    ConstraintLayout bottom;
    TextView greeting;
    CardView call,find;
    RelativeLayout profile;
    String phone;
    ImageView faceb,twitter,telegram,mail,linkedin;
    LinearLayoutCompat videos,magazine,podcasts,businessPlans;
    boolean oo = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greeting = findViewById(R.id.greeting);
        bottom = findViewById(R.id.bottom);
        faceb = findViewById(R.id.fb);
        videos = findViewById(R.id.videos);
        profile = findViewById(R.id.profile);
        call = findViewById(R.id.call_us);
        find = findViewById(R.id.find_us);
        magazine = findViewById(R.id.magazine);
        podcasts = findViewById(R.id.podcasts);
        businessPlans = findViewById(R.id.business_plans);
        twitter = findViewById(R.id.twitter);
        telegram = findViewById(R.id.telegram);

        mail = findViewById(R.id.mail);
        linkedin = findViewById(R.id.linkedin);
        phone = "+254795801971";
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);

        greetUser();
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "heey", Toast.LENGTH_SHORT).show();
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
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        businessPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        faceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
            }
        });
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone));
                startActivity(intent);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oo) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    oo = false;
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    oo = true;
                }
            }
        });
    }
    private void greetUser() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay<12){
            greeting.setText("Good Morning!");
        }else if (timeOfDay >=12 && timeOfDay < 16){
            greeting.setText("Good Afternoon!");
        }else if (timeOfDay >= 16 && timeOfDay < 24) {
            greeting.setText("Good Evening!");
        }
    }
}
