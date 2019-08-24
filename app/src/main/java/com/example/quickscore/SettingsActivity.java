package com.example.quickscore;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsActivity extends BaseActivity {


    //static FragmentManager fragmentManager;

    static boolean THEME_CHANGED_FLAG = false;
    @Override
    protected void onResume() {

        if(THEME_CHANGED_FLAG){
            THEME_CHANGED_FLAG = false;
            recreate();
        }
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //return super.onSupportNavigateUp();
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, settingsFragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Settings");
            actionBar.show();
        }



    }


    public static class SettingsFragment extends PreferenceFragmentCompat {


        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {


            setPreferencesFromResource(R.xml.preferences, rootKey);
            EditTextPreference etPref = findPreference("prepare_time");
            etPref.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

                }
            });

            ListPreference themeListPref = findPreference("theme");
            themeListPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    recreateActivities();
                    return true;
                }
            });
        }

        private void recreateActivities() {

            StartActivity.THEME_CHANGED_FLAG = true;
            SingleActivity.THEME_CHANGED_FLAG = true;
            MatchActivity.THEME_CHANGED_FLAG = true;
            SettingsActivity.THEME_CHANGED_FLAG = true;

            Activity activity = getActivity();
            Intent intent = activity.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.finish();
            activity.overridePendingTransition(0, 0);
            startActivity(intent);
            activity.overridePendingTransition(0, 0);
        }

    }
}