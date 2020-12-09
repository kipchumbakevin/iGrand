package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhone extends AppCompatActivity {
    CountryCodePicker ccp,ccp2;
    EditText old,newPhone;
    Button change;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ccp = findViewById(R.id.ccp);
        ccp2 = findViewById(R.id.ccp2);
        progress = findViewById(R.id.progress);
        old = findViewById(R.id.old_phone);
        newPhone = findViewById(R.id.new_phone);
        change = findViewById(R.id.change);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhone();
            }
        });
    }

    private void checkPhone() {
        showProgress();
        final String oldpho = old.getText().toString();
        final String newpho = newPhone.getText().toString();
            Call<MessagesModel> call = RetrofitClient.getInstance(ChangePhone.this)
                    .getApiConnector()
                    .checkpho(oldpho);
            call.enqueue(new Callback<MessagesModel>() {
                @Override
                public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
                    hideProgress();
                    if (response.code() == 201) {
                        Intent intent = new Intent(ChangePhone.this, CodeVerification.class);
                        intent.putExtra("CODES", Integer.toString(2));
                        intent.putExtra("NEWPHONE", newpho);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChangePhone.this, response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<MessagesModel> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(ChangePhone.this, "Network error. Check your connection", Toast.LENGTH_LONG).show();
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
