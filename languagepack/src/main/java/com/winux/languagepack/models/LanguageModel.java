package com.winux.languagepack.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguageModel {

    @SerializedName("pack_version")
    private String version;

    @SerializedName("default")
    private String default_language;

    @SerializedName("languagePack")
    private List<LanguageInnerModel> appData;


    public String getVersion() {
        return version;
    }

    public String getDefault_language() {
        return default_language;
    }

    public List<LanguageInnerModel> getAppData() {
        return appData;
    }
}
