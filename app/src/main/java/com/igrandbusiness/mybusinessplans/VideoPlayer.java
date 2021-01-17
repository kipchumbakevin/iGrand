package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
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
import com.igrandbusiness.mybusinessplans.adapters.VideosAdapter;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.khizar1556.mkvideoplayer.MKPlayer;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayer extends AppCompatActivity {

    VideosAdapter videosAdapter;
    TextView novideos;
    ImageButton reload;
    RecyclerView recyclerView;
    private ArrayList<ReceiveData>mContentArrayList = new ArrayList<>();
    ProgressBar progressLyt;
    private AdView adView;
    private InterstitialAd interstitialAd;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        recyclerView = findViewById(R.id.recycler);
        progressLyt = findViewById(R.id.progress);
        reload = findViewById(R.id.reload);
        novideos = findViewById(R.id.novideos);
        videosAdapter = new VideosAdapter(this,mContentArrayList);
        recyclerView.setAdapter(videosAdapter);
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
                 Intent intent = new Intent(VideoPlayer.this, MainActivity.class);
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
        fetchVideos();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload.setVisibility(View.GONE);
                fetchVideos();
            }
        });

//        pick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new VideoPicker.Builder(VideoPlayer.this)
//                        .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
//                        .directory(VideoPicker.Directory.DEFAULT)
//                        .extension(VideoPicker.Extension.MP4)
//                        .enableDebuggingMode(true)
//                        .build();
//            }
//        });


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

    private void fetchVideos() {
        showProgress();
        mContentArrayList.clear();
        Call<List<ReceiveData>> call = RetrofitClient.getInstance(VideoPlayer.this)
                .getApiConnector()
                .getVids();
        call.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().size()>0){
                        mContentArrayList.addAll(response.body());
                        videosAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    reload.setVisibility(View.VISIBLE);
                    Toast.makeText(VideoPlayer.this, "Server error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                hideProgress();
                recyclerView.setVisibility(View.GONE);
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(VideoPlayer.this, "Network error", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
//            List<String> mPaths =  data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
//            //Your Code
//            MKPlayerActivity.configPlayer(this).play(mPaths.get(0));
//        }
//    }


    @Override
    public void onBackPressed() {
        if (interstitialAd.isAdLoaded()){
            interstitialAd.show();
        }else {
            Intent intent = new Intent(VideoPlayer.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
