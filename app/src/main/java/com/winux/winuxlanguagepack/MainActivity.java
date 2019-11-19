package com.winux.winuxlanguagepack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.winux.languagepack.LanguagePack;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AppCompatTextView laguage_text;
    private AppCompatSpinner spinner_all_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LanguagePack.init()
                .setMode(LanguagePack.MODE_ONLINE)
                .setAuth(this, "15444", "154455544")
                .setUpdate(LanguagePack.UPDATE_INTERVAL.UPDATE_INTERVAL_2_HOUR)
                .build();

       final  List<String> alllocales = LanguagePack.get().getAllLocales();

        laguage_text = findViewById(R.id.laguage_text);
        spinner_all_language = findViewById(R.id.spinner_all_language);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        alllocales); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner_all_language.setAdapter(spinnerArrayAdapter);

        spinner_all_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LanguagePack.get().setCurrentLocale(alllocales.get(position));
                laguage_text.setText(LanguagePack.get().getLocaleLanguage(
                        "Account already exists - please sign in and contact your school if you require further support."));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
