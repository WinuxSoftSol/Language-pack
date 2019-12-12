package com.winux.languagepack.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.winux.languagepack.LanguagePack;

public class LocaleButton extends AppCompatButton {

    public LocaleButton(Context context) {
        super(context);
    }

    public LocaleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocaleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (LanguagePack.isBuild()) {
            text = LanguagePack.get().getLocaleLanguage(text.toString());
        }
        super.setText(text, type);
    }
}
