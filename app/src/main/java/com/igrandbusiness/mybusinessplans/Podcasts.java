package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igrandbusiness.mybusinessplans.adapters.ContentAdapter;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Podcasts extends AppCompatActivity {

    ContentAdapter contentAdapter;
    TextView novideos;
    ImageButton reload;
    RecyclerView recyclerView;
    private ArrayList<ReceiveData> mContentArrayList = new ArrayList<>();
    ProgressBar progressLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcasts);
        recyclerView = findViewById(R.id.recycler);
        reload = findViewById(R.id.reload);
        progressLyt = findViewById(R.id.progress);
        novideos = findViewById(R.id.novideos);
        contentAdapter = new ContentAdapter(this,mContentArrayList);
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!getIntent().hasExtra("STRESS")){
            Intent serviceIntent = new Intent(this,ExampleService.class);
            stopService(serviceIntent);
            Intent mStartActivity = new Intent(Podcasts.this,Podcasts.class);
            mStartActivity.putExtra("STRESS",Integer.toString(1));
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(Podcasts.this,mPendingIntentId,mStartActivity,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            finish();
            AlarmManager mgr = (AlarmManager) Podcasts.this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC,System.currentTimeMillis(),mPendingIntent);
            System.exit(0);
           // audioPlayer.stopp();
        }

        fetchPod();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload.setVisibility(View.GONE);
                fetchPod();
            }
        });
    }

    private void fetchPod() {
        showProgress();
        mContentArrayList.clear();
        Call<List<ReceiveData>> call = RetrofitClient.getInstance(Podcasts.this)
                .getApiConnector()
                .getPod();
        call.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().size()>0){
                        mContentArrayList.addAll(response.body());
                        contentAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    reload.setVisibility(View.VISIBLE);
                    Toast.makeText(Podcasts.this, "Server error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                hideProgress();
                recyclerView.setVisibility(View.GONE);
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(Podcasts.this, "Network error", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Podcasts.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
