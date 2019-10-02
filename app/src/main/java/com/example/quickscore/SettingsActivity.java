package com.example.quickscore;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsActivity extends BaseActivity {

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            actionBar.setElevation(0);
            actionBar.show();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {


        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);


//            EditTextPreference etPref = findPreference("prepare_time");
//            etPref.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
//                @Override
//                public void onBindEditText(@NonNull EditText editText) {
//                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
//                }
//            });


            ListPreference themeListPref = findPreference(KEY_PREF_THEME);
            themeListPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    StartActivity.THEME_CHANGED_FLAG = true;
//                    SingleActivity.THEME_CHANGED_FLAG = true;
                    SingleActivity.RECREATE_FLAG = true;
                    MatchActivity.RECREATE_FLAG = true;
                    recreateActivity();
                    return true;
                }
            });

            CheckBoxPreference checkBoxPref = findPreference(KEY_PREF_COLORED_CELLS);
            checkBoxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    SingleActivity.CELLS_COLORING_CHANGED_FLAG = true;
                    SingleActivity.RECREATE_FLAG = true;
                    MatchActivity.RECREATE_FLAG = true;
                    return true;
                }
            });
//            Preference prepareTimePref = findPreference("prepare_time_test");



////            SeekBar prepTimeSeekBar =
//            TextView tt = getView().findViewById(R.id.title);
//            tt.setText("fff");


        }


        @Override
        public void onDisplayPreferenceDialog(Preference preference) {
            // Try if the preference is one of our custom Preferences
            CustomDialogPreferenceCompat.CustomPreferenceDialogFragment dialogFragment = null;
            if (preference instanceof CustomDialogPreferenceCompat) {
//                // Create a new instance of TimePreferenceDialogFragment with the key of the related
//                // Preference
                dialogFragment = CustomDialogPreferenceCompat.CustomPreferenceDialogFragment.newInstance(preference.getKey());
            }
//
            // If it was one of our cutom Preferences, show its dialog
            if (dialogFragment != null) {
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(this.getFragmentManager(), "tag");
            }else {
                super.onDisplayPreferenceDialog(preference);
            }
        }



        private void recreateActivity() {
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