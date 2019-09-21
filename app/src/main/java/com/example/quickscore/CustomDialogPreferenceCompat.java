package com.example.quickscore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

public class CustomDialogPreferenceCompat extends DialogPreference {
    private CustomPreferenceDialogFragment mFragment;
    private DialogInterface.OnShowListener mOnShowListener;

    public CustomDialogPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr,
                                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomDialogPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomDialogPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDialogPreferenceCompat(Context context) {
        super(context);
    }

    public boolean isDialogOpen() {
        return getDialog() != null && getDialog().isShowing();
    }

    public Dialog getDialog() {
        return mFragment != null ? mFragment.getDialog() : null;
    }

    public void setOnShowListener(DialogInterface.OnShowListener listner) {
        mOnShowListener = listner;
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder,
                                          DialogInterface.OnClickListener listener) {
    }

    protected void onDialogClosed(boolean positiveResult) {
    }

    protected void onClick(DialogInterface dialog, int which) {
    }

    protected void onBindDialogView(View view) {
    }

    private void setFragment(CustomPreferenceDialogFragment fragment) {
        mFragment = fragment;
    }

    private DialogInterface.OnShowListener getOnShowListener() {
        return mOnShowListener;
    }

    public static class CustomPreferenceDialogFragment extends PreferenceDialogFragmentCompat {
        public static CustomPreferenceDialogFragment newInstance(String key) {
            final CustomPreferenceDialogFragment fragment = new CustomPreferenceDialogFragment();
            final Bundle b = new Bundle(1);
            b.putString(ARG_KEY, key);
            fragment.setArguments(b);
            return fragment;
        }

        private CustomDialogPreferenceCompat getCustomizablePreference() {
            return (CustomDialogPreferenceCompat) getPreference();
        }

        @Override
        protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            super.onPrepareDialogBuilder(builder);
            getCustomizablePreference().setFragment(this);
            getCustomizablePreference().onPrepareDialogBuilder(builder, this);
        }

        @Override
        public void onDialogClosed(boolean positiveResult) {
            getCustomizablePreference().onDialogClosed(positiveResult);
        }

        @Override
        protected void onBindDialogView(View view) {
            super.onBindDialogView(view);
            initSeekBarsLayout(view);
            getCustomizablePreference().onBindDialogView(view);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.setOnShowListener(getCustomizablePreference().getOnShowListener());

            return dialog;
        }
    }

    @Override
    public int getDialogLayoutResource() {
        return R.layout.seekbars_dialog_layout;
    }

    public static void initSeekBarsLayout(View view){
        SeekBar seekBarPrepareTime = view.findViewById(R.id.sb_prepare_time);
        SeekBar seekBarIndoorTime = view.findViewById(R.id.sb_indoor_time);
        SeekBar seekBarOutdoorTime = view.findViewById(R.id.sb_outdoor_time);
        TextView tvPrepareTime = view.findViewById(R.id.tv_prepare_time_value);
        TextView tvIndoorTime = view.findViewById(R.id.tv_indoor_time_value);
        TextView tvOutdoorTime = view.findViewById(R.id.tv_outdoor_time_value);
        SeekBarUtil sbUtil = new SeekBarUtil();
        sbUtil.setSeekBar(seekBarPrepareTime,0, 12, 20, 1, tvPrepareTime);
        sbUtil.setSeekBar(seekBarIndoorTime,60, 110, 180, 10, tvIndoorTime);
        sbUtil.setSeekBar(seekBarOutdoorTime,120, 240, 360, 10, tvOutdoorTime);

    }
}