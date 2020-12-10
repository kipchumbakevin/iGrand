package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.settings.SettingsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    Button change;
    EditText old,newP,confirm;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change = findViewById(R.id.change);
        old = findViewById(R.id.old_pass);
        newP = findViewById(R.id.new_password);
        progress = findViewById(R.id.progress);
        confirm = findViewById(R.id.confirm_new);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
    }
    private void changePass() {
        showProgress();
        String oldp,newp;
        oldp = old.getText().toString();
        newp = newP.getText().toString();
        Call<MessagesModel> call = RetrofitClient.getInstance(ChangePassword.this)
                .getApiConnector()
                .changePass(oldp,newp);
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
                hideProgress();
                if (response.code() == 201) {
                    Toast.makeText(ChangePassword.this, "Password changed", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChangePassword.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<MessagesModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePassword.this, "Network error. Check your connection", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }
}
