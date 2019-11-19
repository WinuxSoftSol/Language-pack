package com.winux.languagepack.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.winux.languagepack.LanguagePack;

public class LocaleTextView extends AppCompatTextView {

    public LocaleTextView(Context context) {
        super(context);
    }

    public LocaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocaleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (LanguagePack.get() != null) {
            text = LanguagePack.get().getLocaleLanguage(text.toString());
        }
        super.setText(text, type);
    }
}
