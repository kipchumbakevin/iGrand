package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideosList extends AppCompatActivity {
   // VideoView videoView;
    String vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_list);
      //  videoView = findViewById(R.id.videoView);
        vid = getIntent().getExtras().getString("VIDEO");
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
}
