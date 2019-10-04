package com.example.quickscore;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;


import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText {
    public CustomEditText( Context context )
    {
        super( context );
    }

    public CustomEditText( Context context, AttributeSet attribute_set )
    {
        super( context, attribute_set );
    }

    public CustomEditText(Context context, AttributeSet attribute_set, int def_style_attribute )
    {
        super( context, attribute_set, def_style_attribute );
    }

    @Override
    public boolean onKeyPreIme( int key_code, KeyEvent event )
    {
        if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP )
            this.clearFocus();
        return super.onKeyPreIme( key_code, event );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
            }catch (Exception e){
                e.printStackTrace();
            }

            this.clearFocus();
        }
        return super.onKeyDown(keyCode, event);
    }
}
