package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AudioPlayer extends AppCompatActivity {
    TextView playerPosition,playerDuration;
    ImageView btRew,btFF,btPlay,btPause;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    Uri uri;
    String title;
    NotificationManager notificationManager;
    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
//        playerDuration = findViewById(R.id.player_duration);
//        playerPosition = findViewById(R.id.player_position);
//        btFF = findViewById(R.id.bt_ff);
//        btRew = findViewById(R.id.bt_rew);
//        btPause = findViewById(R.id.bt_pause);
//        btPlay = findViewById(R.id.bt_play);
//        seekBar = findViewById(R.id.seek_bar);
//        uri = Uri.parse(getIntent().getExtras().getString("URI"));
//        mediaPlayer = new MediaPlayer();
//
//        title = getIntent().getExtras().getString("TITLE");
//        setTitle(title);


        mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebview .loadUrl("https://gaana.com/song/galliyan");
        setContentView(mWebview );
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
//        populateTrack();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            createChannel();
//        }
//        mediaPlayer = MediaPlayer.create(this,uri);
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                seekBar.setProgress(mediaPlayer.getCurrentPosition());
//                handler.postDelayed(this,500);
//            }
//        };
//        int duration = mediaPlayer.getDuration();
//        String sDuration = convertFormat(duration);
//        btPlay.setVisibility(View.GONE);
//        btPause.setVisibility(View.VISIBLE);
//        mediaPlayer.start();
//        seekBar.setMax(mediaPlayer.getDuration());
//        handler.postDelayed(runnable,0);
//        Intent serviceIntent = new Intent(AudioPlayer.this,ExampleService.class);
//        serviceIntent.putExtra("TITLE",title);
//        startService(serviceIntent);
//
//      //  CreateNotification.createNotification(AudioPlayer.this,song.get(0),R.drawable.ic_pause,1,song.size()-1);
//
//        btPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btPlay.setVisibility(View.GONE);
//                btPause.setVisibility(View.VISIBLE);
//                mediaPlayer.start();
//                seekBar.setMax(mediaPlayer.getDuration());
//                handler.postDelayed(runnable,0);
//                Intent serviceIntent = new Intent(AudioPlayer.this,ExampleService.class);
//                serviceIntent.putExtra("TITLE",title);
//                startService(serviceIntent);
//            }
//        });
//        btPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btPause.setVisibility(View.GONE);
//                btPlay.setVisibility(View.VISIBLE);
//                mediaPlayer.pause();
//                handler.removeCallbacks(runnable);
//                Intent serviceIntent = new Intent(AudioPlayer.this,ExampleService.class);
//                stopService(serviceIntent);
//
//            }
//        });
//        btFF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int currentPosition = mediaPlayer.getCurrentPosition();
//                int duration = mediaPlayer.getDuration();
//                if (mediaPlayer.isPlaying() && duration != currentPosition){
//                    //fast forward for 5 seconds
//                    currentPosition = currentPosition + 5000;
//                    //set current position on textview
//                    playerPosition.setText(convertFormat(currentPosition));
//                    //set progress on seek bar
//                    mediaPlayer.seekTo(currentPosition);
//                }
//            }
//        });
//        btRew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int currentPosition = mediaPlayer.getCurrentPosition();
//                if (mediaPlayer.isPlaying() && currentPosition > 5000){
//                    //fast forward for 5 seconds
//                    currentPosition = currentPosition - 5000;
//                    //set current position on textview
//                    playerPosition.setText(convertFormat(currentPosition));
//                    //set progress on seek bar
//                    mediaPlayer.seekTo(currentPosition);
//                }
//            }
//        });
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                //check condition
//                if (fromUser){
//                    mediaPlayer.seekTo(progress);
//                }
//                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                btPause.setVisibility(View.GONE);
//                btPlay.setVisibility(View.VISIBLE);
//                mediaPlayer.seekTo(0);
//
//            }
//        });
//    }
//
////    private void populateTrack() {
////        song  =new ArrayList<>();
////        song.add(new Track(title));
////    }
////
////    private void createChannel() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
////            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
////                    "Music", NotificationManager.IMPORTANCE_LOW);
////            notificationManager = getSystemService(NotificationManager.class);
////            if (notificationManager != null){
////                notificationManager.createNotificationChannel(channel);
////
////            }
////        }
////    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(AudioPlayer.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @SuppressLint("DefaultLocale")
//    private String convertFormat(int duration) {
//        return String.format("%02d:%02d",
//                TimeUnit.MILLISECONDS.toMinutes(duration),
//                TimeUnit.MILLISECONDS.toSeconds(duration)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
//    }
//    public void startService(View v){
//        Intent serviceIntent = new Intent(this,ExampleService.class);
//        serviceIntent.putExtra("TITLE",title);
//        startService(serviceIntent);
//    }
//    public void stopService(View v){
//        Intent serviceIntent = new Intent(this,ExampleService.class);
//        stopService(serviceIntent);
//    }
//    public void stopp(View v){
//        Intent serviceIntent = new Intent(this,ExampleService.class);
//        stopService(serviceIntent);
    }
}
