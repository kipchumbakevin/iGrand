package com.igrandbusiness.mybusinessplans.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.igrandbusiness.mybusinessplans.ChangePassword;
import com.igrandbusiness.mybusinessplans.ChangePhone;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

public class SettingsActivity extends AppCompatActivity {
    ImageView firstLetterImageView;
    TextView usernameTextView, emailTextView;
    private SharedPreferencesConfig sharedPreferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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

}
