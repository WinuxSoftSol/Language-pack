package com.winux.languagepack;

import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class AndroidLocales {
    protected void locale() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> localcountries = new ArrayList<String>();
        for (Locale l : locales) {
            localcountries.add(l.getDisplayLanguage().toString());
        }
        String[] languages = (String[]) localcountries.toArray(new String[localcountries.size()]);

        Log.d("all _locale", languages.toString());
    }
}
