package com.example.pract4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout settingsLayout;
    private RadioGroup themeRadioGroup;
    private ToggleButton botToggleButton;
    private Button applyButton;
    private Button backButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("game_settings", MODE_PRIVATE);
        applyTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsLayout = findViewById(R.id.settingsLayout);

        themeRadioGroup = findViewById(R.id.themeRadioGroup);
        botToggleButton = findViewById(R.id.botToggleButton);
        applyButton = findViewById(R.id.applyButton);
        backButton = findViewById(R.id.backButton);

        loadSettings();
        updateUIForTheme();

        applyButton.setOnClickListener(v -> applySettings());

        backButton.setOnClickListener(v -> finish());
    }

    private void loadSettings() {
        boolean isBotEnabled = preferences.getBoolean("botEnabled", false);
        botToggleButton.setChecked(isBotEnabled);

        String theme = preferences.getString("theme", "light");
        if ("dark".equals(theme)) {
            themeRadioGroup.check(R.id.darkThemeRadioButton);
        } else {
            themeRadioGroup.check(R.id.lightThemeRadioButton);
        }
    }

    private void applySettings() {
        SharedPreferences.Editor editor = preferences.edit();
        if (themeRadioGroup.getCheckedRadioButtonId() == R.id.lightThemeRadioButton) {
            editor.putString("theme", "light");
        } else if (themeRadioGroup.getCheckedRadioButtonId() == R.id.darkThemeRadioButton) {
            editor.putString("theme", "dark");
        }

        editor.putBoolean("botEnabled", botToggleButton.isChecked());
        editor.apply();

        setResult(RESULT_OK);
        finish();
    }

    private void updateUIForTheme() {
        String theme = preferences.getString("theme", "light");
        if ("dark".equals(theme)) {
            settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.black));
            applyButton.setTextColor(getResources().getColor(android.R.color.white));
            backButton.setTextColor(getResources().getColor(android.R.color.white));
            botToggleButton.setTextColor(getResources().getColor(android.R.color.white));
            themeRadioGroup.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            applyButton.setTextColor(getResources().getColor(android.R.color.white));
            backButton.setTextColor(getResources().getColor(android.R.color.white));
            botToggleButton.setTextColor(getResources().getColor(android.R.color.black));
            themeRadioGroup.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void applyTheme() {
        String theme = preferences.getString("theme", "light");
        if ("dark".equals(theme)) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }
}