package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.igrandbusiness.mybusinessplans.adapters.ContentAdapter;
import com.igrandbusiness.mybusinessplans.adapters.MagazineAdapter;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Magazines extends AppCompatActivity {

    MagazineAdapter magazineAdapter;
    TextView novideos;
    RecyclerView recyclerView;
    ImageButton reload;
    private ArrayList<ReceiveData> mContentArrayList = new ArrayList<>();
    ProgressBar progressLyt;
    private AdView adView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazines);
        recyclerView = findViewById(R.id.recycler);
        reload = findViewById(R.id.reload);
        progressLyt = findViewById(R.id.progress);
        novideos = findViewById(R.id.novideos);
        magazineAdapter = new MagazineAdapter(this,mContentArrayList);
        recyclerView.setAdapter(magazineAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                Intent intent = new Intent(Magazines.this, MainActivity.class);
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

        fetchMagazines();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload.setVisibility(View.GONE);
                fetchMagazines();
            }
        });
    }
    private void fetchMagazines() {
        showProgress();
        mContentArrayList.clear();
        Call<List<ReceiveData>> call = RetrofitClient.getInstance(Magazines.this)
                .getApiConnector()
                .getMags();
        call.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().size()>0){
                        mContentArrayList.addAll(response.body());
                        magazineAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    reload.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(Magazines.this, "Server error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                hideProgress();
                reload.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(Magazines.this, "Network error", Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    public void onBackPressed() {
        if (interstitialAd.isAdLoaded()){
            interstitialAd.show();
        }else {
            Intent intent = new Intent(Magazines.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}
