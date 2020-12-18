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

public class ForgotPassword extends AppCompatActivity {
    Button goLogin,change;
    EditText phone,password,confirm_password;
    ProgressBar progress;
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        goLogin = findViewById(R.id.go_login);
        change = findViewById(R.id.change);
        phone = findViewById(R.id.phone);
        ccp = findViewById(R.id.ccp);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        progress = findViewById(R.id.progress);

        ccp.registerCarrierNumberEditText(phone);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ccp.isValidFullNumber()){
                    Toast.makeText(ForgotPassword.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                }if (password.getText().toString().isEmpty() || confirm_password.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPassword.this, "Ensure you fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    changePass();
                }
            }
        });
    }

    private void changePass() {

            showProgress();
            final String pho = ccp.getFullNumberWithPlus();
            final String pass = password.getText().toString();
            if (pass.equals(confirm_password.getText().toString())) {
                Call<MessagesModel> call = RetrofitClient.getInstance(ForgotPassword.this)
                        .getApiConnector()
                        .check(pho);
                call.enqueue(new Callback<MessagesModel>() {
                    @Override
                    public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
                        hideProgress();
                        if (response.code() == 201) {
                            Intent intent = new Intent(ForgotPassword.this, CodeVerification.class);
                            intent.putExtra("CODES", Integer.toString(2));
                            intent.putExtra("PHONE", pho);
                            intent.putExtra("NEWPASS", pass);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ForgotPassword.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<MessagesModel> call, Throwable t) {
                        hideProgress();
                        Toast.makeText(ForgotPassword.this, "Network error. Check your connection", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(ForgotPassword.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            }

    }
    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }
}
