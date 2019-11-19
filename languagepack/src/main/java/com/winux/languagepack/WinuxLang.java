package com.winux.languagepack;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.winux.languagepack.exceptions.ConfigFailedException;
import com.winux.languagepack.exceptions.InstanceAlreadyCreatedException;
import com.winux.languagepack.exceptions.InstanceNotFoundException;
import com.winux.languagepack.models.LanguageInnerModel;
import com.winux.languagepack.models.LanguageModel;
import com.winux.languagepack.util.DataProccessor;
import com.winux.languagepack.util.DataUpdator;
import com.winux.languagepack.util.ResourceCreator;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class WinuxLang implements Cloneable {

    private static final String TAG = "WinuxLang";
    private static final String INSTANCE_ALREADY_CREATED = "Instance of this class is already created please use WinuxLang.init() to get instance";

    public static final int MODE_OFFLINE = 2;
    public static final int MODE_ONLINE = 1;


    private static WinuxLang instance;
    private int mode = 1;
    private String environment = ENVIRONMENT.PRODUCTION;
    private long updation_time = 1;
    private String package_name;
    private String account_id;
    private String app_id;
    private String locale = null;
    private boolean LOCALE_CHANGED = false;
    private Context context;
    private LanguageModel languageModel = null;
    private LanguageInnerModel languageInnerModel = null;


    public interface UPDATE_INTERVAL {
        long UPDATE_NOW = 0;
        long UPDATE_INTERVAL_1_HOUR = 1;
        long UPDATE_INTERVAL_2_HOUR = 2;
        long UPDATE_INTERVAL_4_HOUR = 4;
        long UPDATE_INTERVAL_6_HOUR = 6;
        long UPDATE_INTERVAL_12_HOUR = 12;
        long UPDATE_INTERVAL_24_HOUR = 24;
    }


    public interface ENVIRONMENT {
        String DEBUG = "debug";
        String PRODUCTION = "production";
    }

    private WinuxLang() {
        if (instance != null) {
            throw new InstanceAlreadyCreatedException("Failed : " + INSTANCE_ALREADY_CREATED);
        }
    }


    /**
     * to init Winux language pack
     */
    public static synchronized WinuxLang init() {
        if (instance == null) {
            instance = new WinuxLang();
        }
        return instance;
    }


    public static WinuxLang get() {
        if (instance == null) {
            throw new InstanceNotFoundException("Configuration failed : please build config (WinuxLang.init().setMode(WinuxLang.MODE_ONLINE).setAuth(this).build();)");
        }
        return instance;
    }


    /**
     * have two mode online or offline
     * online mode allows user to change values from admin control
     * in offline mode user have to download file from admin and should store it in res->raw folder
     *
     * @param mode use WinuxLang.MODE_OFFLINE/WinuxLang.MODE_ONLINE
     */
    public WinuxLang setMode(int mode) {
        this.mode = mode;
        return instance;
    }

    /**
     * have two environment DEBUG or PRODUCTION
     * DEBUG - key/value changes available in 1 minute
     * PRODUCTION - key/value changes available in 1 hour or you can change using UPDATE_INTERVAL interface
     *
     * @param environment use WinuxLang.ENVIRONMENT.PRODUCTION/WinuxLang.ENVIRONMENT.DEBUG
     */
    public WinuxLang setEnvironment(String environment) {
        this.environment = environment;
        return instance;
    }

    /**
     *
     */
    public WinuxLang setCurrentLocale(String locale) {

        if (this.locale == null) {
            this.locale = locale;
            return instance;
        }

        if (!this.locale.equalsIgnoreCase(locale)) {
            LOCALE_CHANGED = true;
            this.locale = locale;
        }
        return instance;
    }


    /**
     * flag for getting update time keys from server
     *
     * @param updation_time use UPDATE_INTERVAL interface (Time in hours)
     */
    public WinuxLang setUpdate(long updation_time) {
        if (environment.equals(ENVIRONMENT.DEBUG)) {
            return instance;
        }
        this.updation_time = updation_time < 1 ? 1 : updation_time;
        return instance;
    }


    /**
     * set authentication for this application
     *
     * @param account_id create account on admin and get account_id from portal
     * @param app_id     you can also find it after register your application package
     **/
    public WinuxLang setAuth(Context context, String account_id, String app_id) {
        this.account_id = account_id;
        this.app_id = app_id;
        this.context = context;
        return instance;
    }


    public WinuxLang build() {

        if (account_id == null || account_id.isEmpty()) {
            throw new ConfigFailedException("account_id not found");
        }

        if (app_id == null || app_id.isEmpty()) {
            throw new ConfigFailedException("app_id_ not found");
        }

        if (context == null) {
            throw new ConfigFailedException("context must not be null");
        }


        this.package_name = context.getPackageName();

        if (DataProccessor.getInstance(context).getString(DataProccessor.KEY_STORE_COMPLETE_JSON) != null) {
            long timeDiffinMillis = Calendar.getInstance().getTimeInMillis() -
                    DataProccessor.getInstance(context).getLong(DataProccessor.KEY_STORE_UPDATED_AT);
            long compareTime = environment == ENVIRONMENT.DEBUG
                    ? TimeUnit.MINUTES.toMillis(updation_time)
                    : TimeUnit.HOURS.toMillis(updation_time);
            if (timeDiffinMillis < compareTime) {

                Gson gson = new Gson();
                languageModel = gson.fromJson(DataProccessor.getInstance(context).getString(DataProccessor.KEY_STORE_COMPLETE_JSON), LanguageModel.class);
                return instance;
            }

        }

        if (mode == WinuxLang.MODE_OFFLINE) {
            readDataFromFile();
            return instance;
        }

        if (mode == WinuxLang.MODE_ONLINE) {
            int result = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.INTERNET);
            if (result != PackageManager.PERMISSION_GRANTED) {
                throw new ConfigFailedException("Internet permission required in manifest");
            }
            readDataFromServer();
            return instance;
        }
        return instance;
    }

    /**
     * read file form raw folder
     */
    private void readDataFromFile() {
        InputStream inputStream = context.getResources()
                .openRawResource(
                        ResourceCreator.create(
                                context, "language_pack_local", ResourceCreator.DEF_TYPE_RAW
                        )
                );
        try {
            if (inputStream != null) {
                Gson gson = new Gson();
                InputStreamReader reader = new InputStreamReader(inputStream);
                languageModel = gson.fromJson(reader, LanguageModel.class);
                DataProccessor.getInstance(context).setString(DataProccessor.KEY_STORE_COMPLETE_JSON, gson.toJson(languageModel));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


    /**
     * read file form Server
     */
    private void readDataFromServer() {
        LanguageServer languageServer = new LanguageServer();
        try {
            String jsonData = languageServer.execute("https://psimetryhubresources.blob.core.windows.net/psimetryhubresourcesblob/language_pack_local.json").get();
            Gson gson = new Gson();
            DataProccessor.getInstance(context).setString(DataProccessor.KEY_STORE_COMPLETE_JSON, jsonData);
            DataProccessor.getInstance(context).setLong(DataProccessor.KEY_STORE_UPDATED_AT, Calendar.getInstance().getTimeInMillis());
            languageModel = gson.fromJson(jsonData, LanguageModel.class);
            languageInnerModel = null;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setDailyAlarmOn(Context context, long alarmTime, long repeatTime) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent operation =
                DataUpdator.getReminderPendingIntent(context);

        if (Build.VERSION.SDK_INT >= 23) {

            manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, repeatTime, operation);
        } else {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, repeatTime, operation);
        }

    }

    @NonNull
    @Override
    protected Object clone() {
        throw new InstanceAlreadyCreatedException("Failed : " + INSTANCE_ALREADY_CREATED);
    }

    private void getDataModel(String loc) {
        for (int i = 0; i < languageModel.getAppData().size(); i++) {
            if (languageModel.getAppData().get(i).getLocales().contains(loc)) {
                languageInnerModel = languageModel.getAppData().get(i);
            }
        }
        LOCALE_CHANGED = false;
    }


    public String getDefaultLanguage() {
        return languageModel.getDefault_language();
    }


    public String getLocaleLanguage(String key) {
        if (locale == null) {
            locale = context.getResources().getConfiguration().locale.getLanguage();
        }

        if (languageInnerModel == null || LOCALE_CHANGED) {
            getDataModel(locale);
        }

        return (languageInnerModel != null && languageInnerModel.getData().containsKey(key))
                ? languageInnerModel.getData().get(key)
                : key;
    }

    public List<String> getAllLocales() {
        List<String> alllocales = new ArrayList<>();
        for (int i = 0; i < languageModel.getAppData().size(); i++) {
            alllocales.addAll(languageModel.getAppData().get(i).getLocales());
        }
        return alllocales;
    }


}
