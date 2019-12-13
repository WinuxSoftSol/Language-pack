package com.winux.languagepack.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class LanguageInnerModel {
    @SerializedName("locales")
    private Map<String, String> locales;

    @SerializedName("text")
    private Map<String, String> data;

    @SerializedName("language_name")
    private String language_name;

    public Map<String, String> getLocales() {
        return locales;
    }

    public void setLocales(Map<String, String> locales) {
        this.locales = locales;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }


    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }
}
