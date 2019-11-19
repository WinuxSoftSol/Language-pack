package com.winux.languagepack.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class LanguageInnerModel {
    @SerializedName("locales")
    private List<String> locales;

    @SerializedName("text")
    private Map<String, String> data;

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
