package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.igrandbusiness.mybusinessplans.models.UsersModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView forgot,gotToSignUp;
    EditText phone,password;
    CountryCodePicker ccp;
    ProgressBar progress;
    private String clientsPhone,accessToken,clientsEmail;
    SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        gotToSignUp = findViewById(R.id.go_sign_up);
        forgot = findViewById(R.id.forgot_password);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        ccp = findViewById(R.id.ccp);
        progress = findViewById(R.id.progress);
        sharedPreferencesConfig = new SharedPreferencesConfig(this);
        ccp.registerCarrierNumberEditText(phone);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ccp.isValidFullNumber()){
                    Toast.makeText(LoginActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                }if (password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ensure you fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    loginUser();
                }
            }
        });
        gotToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("logged", ""+ sharedPreferencesConfig.isloggedIn());

        if (sharedPreferencesConfig.isloggedIn()){
            // Toast.makeText(LoginActivity.this,sharedPreferencesConfig.readClientsId(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            intent.putExtra("COME",Integer.toString(2));
            startActivity(intent);
            finish();
        }
    }

    private void loginUser() {
            showProgress();
            String pho = ccp.getFullNumberWithPlus();
            String pass = password.getText().toString();
            Call<UsersModel> call = RetrofitClient.getInstance(LoginActivity.this)
                    .getApiConnector()
                    .login(pho, pass);
            call.enqueue(new Callback<UsersModel>() {
                @Override
                public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                    hideProgress();
                    if (response.isSuccessful()) {
                        //  String mmm =  Integer.toString(response.body().getUser().getId());
                        accessToken = response.body().getAccessToken();
                        clientsPhone = response.body().getUser().getPhone();
                        clientsEmail = response.body().getUser().getEmail();
                        //   clientsId = Integer.toString(response.body().getUser().getId());
                        sharedPreferencesConfig.saveAuthenticationInformation(accessToken, clientsPhone,clientsEmail, Constants.ACTIVE_CONSTANT);
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.putExtra("COME",Integer.toString(2));
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UsersModel> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(LoginActivity.this, "Network error. Check your connection", Toast.LENGTH_LONG).show();
                }
            });

    }
    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
