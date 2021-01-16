package com.igrandbusiness.mybusinessplans.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.igrandbusiness.mybusinessplans.ChangePassword;
import com.igrandbusiness.mybusinessplans.ChangePhone;
import com.igrandbusiness.mybusinessplans.LoginActivity;
import com.igrandbusiness.mybusinessplans.MainActivity;
import com.igrandbusiness.mybusinessplans.ProfileActivity;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    ImageView firstLetterImageView;
    TextView usernameTextView, emailTextView;
    private SharedPreferencesConfig sharedPreferencesConfig;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        progress = findViewById(R.id.progress);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());


        firstLetterImageView = findViewById(R.id.first_letter_image_view);
        usernameTextView = findViewById(R.id.name_text_view);
        emailTextView = findViewById(R.id.des_text_view);
        String status = sharedPreferencesConfig.readClientsStatus();

            usernameTextView.setText("iGrand");
            emailTextView.setText("Business Plans");

            getFirstLetterInCircularBackground(firstLetterImageView, "iGrand");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
            alert.setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            logout();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final Context context = getContext();

            Preference preferenceChangePassword, preferencePhone;
            preferenceChangePassword = findPreference("password");
            preferencePhone = findPreference("phone");

            preferenceChangePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(context, ChangePassword.class);
                    startActivity(intent);
                    ((Activity)context).finish();

                    return true;
                }
            });

            preferencePhone.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(context, ChangePhone.class);
                    startActivity(intent);

                    return false;
                }
            });


        }
    }
    private void logout() {
        sharedPreferencesConfig.clear();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

//        showProgress();
//        Call<MessagesModel> call = RetrofitClient.getInstance(SettingsActivity.this)
//                .getApiConnector()
//                .logOut();
//        call.enqueue(new Callback<MessagesModel>() {
//            @Override
//            public void onResponse(Call<MessagesModel> call, Response<MessagesModel> response) {
//                hideProgress();
//                if (response.code() == 200) {
//                    sharedPreferencesConfig.clear();
//                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                    Toast.makeText(SettingsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(SettingsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MessagesModel> call, Throwable t) {
//                hideProgress();
//                Toast.makeText(SettingsActivity.this, "Network error" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public void getFirstLetterInCircularBackground(ImageView imageView, String username){
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//        generate random color
//        int color = generator.getColor(getItem());

        int color = generator.getRandomColor();
        String firstLetter = String.valueOf(username.charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        imageView.setImageDrawable(drawable);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
