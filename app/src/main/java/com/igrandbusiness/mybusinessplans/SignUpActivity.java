package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText email,phone,password,confirm;
    Button singnup,login;
    TextView terms;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ccp = findViewById(R.id.ccp);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        progress = findViewById(R.id.progress);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm_password);
        singnup = findViewById(R.id.signup);
        login = findViewById(R.id.go_to_login);
        terms = findViewById(R.id.terms_of_service);

        singnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });
    }
    private void checkDetails() {
        showProgress();
        final String pho,mail,pass,cpass;
        pho = ccp.getDefaultCountryCodeWithPlus();
        mail = email.getText().toString();
        pass = password.getText().toString();
        cpass = confirm.getText().toString();
        if (pass.equals(cpass)) {
            Call<MessagesModel> call = RetrofitClient.getInstance(SignUpActivity.this)
                    .getApiConnector()
                    .checkDetails(pho, mail);
            call.enqueue(new Callback<MessagesModel>() {
                @Override
                public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
                    hideProgress();
                    if (response.code() == 201) {
                        //  Toast.makeText(SignUpActivity.this,"Your verification code has been sent",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, CodeVerification.class);
                        intent.putExtra("CODES", Integer.toString(1));
                        intent.putExtra("PHONE", pho);
                        intent.putExtra("EMAIL", mail);
                        intent.putExtra("PASSWORD", pass);
                        intent.putExtra("CONFIRM", cpass);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<MessagesModel> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(SignUpActivity.this, "Network error. Check your connection", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

}
