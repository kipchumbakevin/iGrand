package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.models.UsersModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.receivers.AppSignatureHashHelper;
import com.igrandbusiness.mybusinessplans.receivers.ZikySMSReceiver;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeVerification extends AppCompatActivity implements
        ZikySMSReceiver.OTPReceiveListener {
    EditText enter_code;
    Button confirm;
    ProgressBar progress;
    TextView resend;
    String email,phone,code,password,confirmPassword,newpass,clientsPhone,token;
    private ZikySMSReceiver smsReceiver;
    public String appSignature;
    private Context context;
    private SharedPreferencesConfig sharedPreferencesConfig;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        enter_code = findViewById(R.id.enter_code);
        confirm = findViewById(R.id.confirm);
        progress = findViewById(R.id.progress);
        resend = findViewById(R.id.resend_code);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        newpass = getIntent().getExtras().getString("NEWPASS");
        appSignature = new AppSignatureHashHelper(this).getAppSignatures().get(0);
        i = Integer.parseInt(getIntent().getExtras().getString("CODES"));
        if (i == 1){
            phone = getIntent().getExtras().getString("PHONE");
            email = getIntent().getExtras().getString("EMAIL");
            password = getIntent().getExtras().getString("PASSWORD");
            confirmPassword = getIntent().getExtras().getString("CONFIRM");
        }else if (i == 2){
            phone = getIntent().getExtras().getString("PHONE");
            password = getIntent().getExtras().getString("NEWPASS");
        }else if (i == 3){
            phone = getIntent().getExtras().getString("NEWPHONE");
        }
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification();
            }
        });
        context = getApplicationContext();
        sendCode();
    }
    private void sendCode() {
        showProgress();
        Call<MessagesModel> call = RetrofitClient.getInstance(CodeVerification.this)
                .getApiConnector()
                .code(phone,appSignature);
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
                hideProgress();
                if(response.code()==201){
                    startSMSListener();
                    startCountDownTimer();
                    Toast.makeText(CodeVerification.this,"Your verification code has been sent",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CodeVerification.this,"Server error",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<MessagesModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(CodeVerification.this,"Network error. Check your connection",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUserAfterConfirmation() {
        showProgress();
        code = enter_code.getText().toString();
        Call<UsersModel> call = RetrofitClient.getInstance(CodeVerification.this)
                .getApiConnector()
                .register(email,phone,password,confirmPassword,code);
        call.enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                hideProgress();
                if(response.code()==201){
                    token = response.body().getAccessToken();
                    clientsPhone = response.body().getUser().getPhone();
                    //  clientsId = Integer.toString(response.body().getUser().getId());
                    sharedPreferencesConfig.saveAuthenticationInformation(token,clientsPhone, Constants.ACTIVE_CONSTANT);
                    Toast.makeText(CodeVerification.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CodeVerification.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(CodeVerification.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(CodeVerification.this,"Network error. Check your connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startSMSListener() {
        try {
            smsReceiver = new ZikySMSReceiver();
            smsReceiver.setOTPListener(CodeVerification.this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started
                    Log.d("OTP-API:", "Successfully Started");
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
                    //Reload Activity or what?

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startCountDownTimer(){

        new CountDownTimer(60000, 1000) { // 60 seconds, in 1 second intervals
            public void onTick(long millisUntilFinished) {
                resend.setText("Resend code after "+millisUntilFinished / 1000 +" Seconds");
                resend.setVisibility(View.VISIBLE);
                resend.setEnabled(false);
            }

            public void onFinish() {
                resend.setEnabled(true);
                resend.setText("Resend Code");

            }
        }.start();
    }
    @Override
    public void onOTPReceived(String verificationSMS) {
        //success
//        Log.d("OTP-SUCCESS:", verificationSMS);
//        Toast.makeText(this, "OTP Success", Toast.LENGTH_SHORT).show();

        //EXTRACT THE 5-digit Code
        enter_code.setText(extractCodeFromSMS(verificationSMS));

        //Start Verification too :)
        verification();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

    private void verification() {
       if (i == 1){
           registerUserAfterConfirmation();
       }else if (i == 2){
           changePassword();
       }else if (i == 3){
           changePhone();
       }
    }

    @Override
    public void onOTPTimeOut() {
        Log.d("OTP-TIMEOUT:", "TIMEOUT");
        Toast.makeText(this, "TIME-OUT, Try Again", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onOTPReceivedError(String error) {
        //error
        Log.d("OTP-ERROR:", error);
        Toast.makeText(this, "Error: "+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

    public String extractCodeFromSMS(String verificationSMS) {
        return verificationSMS.split(":")[1];
    }
    public void resendSMS() {
        enter_code.setText(""); // Clear Code
        sendCode();
    }
    private void changePassword() {
        showProgress();
        code = enter_code.getText().toString();
        Call<UsersModel> call = RetrofitClient.getInstance(CodeVerification.this)
                .getApiConnector()
                .forgotSign(phone,password,code);
        call.enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                hideProgress();
                if(response.code()==201){
                    token = response.body().getAccessToken();
                    clientsPhone = response.body().getUser().getPhone();
                    //  clientsId = Integer.toString(response.body().getUser().getId());
                    sharedPreferencesConfig.saveAuthenticationInformation(token,clientsPhone, Constants.ACTIVE_CONSTANT);
                    Toast.makeText(CodeVerification.this, "Successfully changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CodeVerification.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(CodeVerification.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(CodeVerification.this,"Network error. Check your connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void changePhone() {
        showProgress();
        code = enter_code.getText().toString();
        Call<MessagesModel> call = RetrofitClient.getInstance(CodeVerification.this)
                .getApiConnector()
                .changePP(phone,code);
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
                hideProgress();
                if(response.code()==201){
                    Toast.makeText(CodeVerification.this, "Successfully changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CodeVerification.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(CodeVerification.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MessagesModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(CodeVerification.this,"Network error. Check your connection",Toast.LENGTH_SHORT).show();
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
