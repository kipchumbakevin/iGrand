package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igrandbusiness.mybusinessplans.adapters.Clientdetailsadapter;
import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.models.UserDocs;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.settings.SettingsActivity;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView settings;
    ImageButton reload;
    RecyclerView recyclerView;
    private final ArrayList<UserDocs> mDocsArrayList = new ArrayList<>();
    Clientdetailsadapter clientdetailsadapter;
    TextView noDocs,emailView;
    ProgressBar progressLyt;
    String email;
    SharedPreferencesConfig sharedPreferencesConfig;
    int come = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        settings = findViewById(R.id.settings);
        recyclerView = findViewById(R.id.recycler);
        reload = findViewById(R.id.reload);
        emailView = findViewById(R.id.email);
        progressLyt = findViewById(R.id.progress);
        noDocs = findViewById(R.id.nodocs);
        if (getIntent().hasExtra("COME")){
            come = Integer.parseInt(getIntent().getExtras().getString("COME"));
        }
        if (come == 1){
            Intent mStartActivity = new Intent(ProfileActivity.this,ProfileActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(ProfileActivity.this,mPendingIntentId,mStartActivity,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            finish();
            AlarmManager mgr = (AlarmManager) ProfileActivity.this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC,System.currentTimeMillis()+3,mPendingIntent);
            System.exit(0);
        }
        sharedPreferencesConfig = new SharedPreferencesConfig(this);
        clientdetailsadapter = new Clientdetailsadapter(this,mDocsArrayList);
        recyclerView.setAdapter(clientdetailsadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        email = sharedPreferencesConfig.readClientsEmail();
        emailView.setText(email);

        viewDocs();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload.setVisibility(View.GONE);
                viewDocs();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void viewDocs() {
        showProgress();
        mDocsArrayList.clear();

        Call<List<UserDocs>> call = RetrofitClient.getInstance(ProfileActivity.this)
                .getApiConnector()
                .getDocs(email);
        call.enqueue(new Callback<List<UserDocs>>() {
            @Override
            public void onResponse(Call<List<UserDocs>> call, Response<List<UserDocs>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    if (response.body().size()>0){
                        mDocsArrayList.addAll(response.body());
                        clientdetailsadapter.notifyDataSetChanged();
                    }else {
                        noDocs.setVisibility(View.VISIBLE);
                    }
                } else {
                    reload.setVisibility(View.VISIBLE);
                    Toast.makeText(ProfileActivity.this, "Server error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDocs>> call, Throwable t) {
                hideProgress();
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}
