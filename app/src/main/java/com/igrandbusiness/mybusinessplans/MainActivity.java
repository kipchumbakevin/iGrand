package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    BottomSheetBehavior bottomSheetBehavior,bottomDetails;
    ConstraintLayout bottom;
    TextView greeting;
    CardView call,find,rate,share;
    RelativeLayout profile;
    String phone;
    ImageView faceb,twitter,telegram,mail,linkedin,menu;
    LinearLayoutCompat videos,magazine,podcasts,businessPlans,bottomD;
    private int[] mImages = new int[]{
            R.drawable.car,R.drawable.dog,R.drawable.plane,R.drawable.pool
    };
    boolean oo,ff = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greeting = findViewById(R.id.greeting);
        bottom = findViewById(R.id.bottom);
        bottomD = findViewById(R.id.bottomD);
        rate = findViewById(R.id.rate);
        share = findViewById(R.id.share);
        faceb = findViewById(R.id.fb);
        videos = findViewById(R.id.videos);
        profile = findViewById(R.id.profile);
        call = findViewById(R.id.call_us);
        menu = findViewById(R.id.menu);
        find = findViewById(R.id.find_us);
        magazine = findViewById(R.id.magazine);
        podcasts = findViewById(R.id.podcasts);
        businessPlans = findViewById(R.id.business_plans);
        twitter = findViewById(R.id.twitter);
        telegram = findViewById(R.id.telegram);
        CarouselView carouselView = findViewById(R.id.slider);
        carouselView.setPageCount(mImages.length);
        mail = findViewById(R.id.mail);
        linkedin = findViewById(R.id.linkedin);
        phone = "+254795801971";
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        bottomDetails = BottomSheetBehavior.from(bottomD);

        greetUser();
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),VideoPlayer.class);
                startActivity(intent);
            }
        });
        magazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Magazines.class);
                startActivity(intent);
            }
        });
        podcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AudioPlayer.class);
                startActivity(intent);
                finish();
            }
        });
        businessPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
               finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ff && !oo) {
                    bottomDetails.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    oo = true;
                    ff = false;
                }else if (ff){
                    bottomDetails.setState(BottomSheetBehavior.STATE_EXPANDED);
                    ff = false;
                }
                else {
                    bottomDetails.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ff = true;
                }
            }
        });
        faceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Uri uri = Uri.parse("https://www.facebook.com/iGrandbp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/iGrandbp")));
                }
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Uri uri = Uri.parse("https://twitter.com/iGrandbp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/iGrandbp")));
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomDetails.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ff = true;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Make money now and get instant loans.\n" +
                        "Download iMoney now at https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName();
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDetails.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ff = true;
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,  new String[]{"igrand@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "I need help iGrand");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Uri uri = Uri.parse("https://t.me/iGrandbp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/iGrandbp")));
                }
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Uri uri = Uri.parse("https://www.linkedin.com/company/igrand-business-plans/");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.linkedin.com/company/igrand-business-plans/")));
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDetails.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ff = true;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone));
                startActivity(intent);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDetails.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ff = true;
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
