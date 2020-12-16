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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

}
