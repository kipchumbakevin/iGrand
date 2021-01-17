package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    BottomSheetBehavior bottomSheetBehavior;
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
    private AdView adView;
    private InterstitialAd interstitialAd;
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

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, getString(R.string.banner), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();
        interstitialAd = new InterstitialAd(this, getString(R.string.interstitial));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                //  Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Intent intent = new Intent(getApplicationContext(), Magazines.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                //Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                //interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                // Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                // Log.d(TAG, "Interstitial ad impression logged!");
            }
        };
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());




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
                finish();
            }
        });
        magazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (interstitialAd.isAdLoaded()){
                    interstitialAd.show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), Magazines.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        podcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Podcasts.class);
                startActivity(intent);
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

    }
    public void showPopup(View v){
        if (!oo) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            oo = true;
        }
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.pop_up);
        popupMenu.show();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }if (interstitialAd != null){
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.call:
                ff = true;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone));
                startActivity(intent);
                return true;
            case R.id.find:
                if (oo) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    oo = false;
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    oo = true;
                }
                return true;
            case R.id.share:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ff = true;
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                String shareBody = "Make money now and get instant loans.\n" +
                        "Download iMoney now at https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName();
                intent1.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent1, "Share via"));
                return true;
            case R.id.rate:
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
                return true;
                default:
                    return false;
        }
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
