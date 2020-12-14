package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igrandbusiness.mybusinessplans.adapters.ContentAdapter;
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

    ContentAdapter contentAdapter;
    TextView novideos;
    RecyclerView recyclerView;
    private ArrayList<ReceiveData>mContentArrayList = new ArrayList<>();
    ProgressBar progressLyt;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        recyclerView = findViewById(R.id.recycler);
        progressLyt = findViewById(R.id.progress);
        novideos = findViewById(R.id.novideos);
        contentAdapter = new ContentAdapter(this,mContentArrayList);
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchVideos();

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
                    if (response.body().size()>0){
                        mContentArrayList.addAll(response.body());
                        contentAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(VideoPlayer.this, "Server error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                hideProgress();
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
