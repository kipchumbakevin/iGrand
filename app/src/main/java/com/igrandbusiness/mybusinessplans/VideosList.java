package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideosList extends AppCompatActivity {
   // VideoView videoView;
    String vid;
    private AdView adView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_list);
      //  videoView = findViewById(R.id.videoView);
        vid = getIntent().getExtras().getString("VIDEO");

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
                Intent intent = new Intent(VideosList.this,VideoPlayer.class);
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

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "S0Q4gqBUs7c";
                youTubePlayer.loadVideo(vid, 0);
            }
        });

//        videoView.setVideoURI(Uri.parse(vid));
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//        videoView.requestFocus();
//        videoView.start();
    }

//    @RequiresApi (api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public void changeOrientation(boolean landscape){
//        if (landscape){
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
//            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            videoView.setLayoutParams(params);
//            return;
//        }
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
//        params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
//        params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        videoView.setLayoutParams(params);
//    }
//    @RequiresApi (api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//
//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        switch (newConfig.orientation){
//            case Configuration.ORIENTATION_LANDSCAPE:
//            changeOrientation(true);
//            break;
//            case Configuration.ORIENTATION_PORTRAIT:
//                changeOrientation(false);
//                break;
//        }
//    }

    @Override
    public void onBackPressed() {
        if (interstitialAd.isAdLoaded()){
            interstitialAd.show();
        }else {
            Intent intent = new Intent(VideosList.this,VideoPlayer.class);
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
}
