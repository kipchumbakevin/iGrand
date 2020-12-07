package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greeting = findViewById(R.id.greeting);
        bottom = findViewById(R.id.bottom);
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
