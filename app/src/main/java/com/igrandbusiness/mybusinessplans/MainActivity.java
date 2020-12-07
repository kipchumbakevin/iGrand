package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetUser();
    }
    
    private void greetUser() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay<12){
            Toast.makeText(this, "Good morning", Toast.LENGTH_SHORT).show();
        }else if (timeOfDay >=12 && timeOfDay < 16){
            Toast.makeText(this, "Good afternoon", Toast.LENGTH_SHORT).show();
        }else if (timeOfDay >= 16 && timeOfDay < 24) {
            Toast.makeText(this, "Good evening", Toast.LENGTH_SHORT).show();
        }
    }
}
