package com.winux.languagepack.util;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class DataUpdator extends JobIntentService {
    private static final String TAG = DataUpdator.class.getSimpleName();

    public static PendingIntent getReminderPendingIntent(Context context) {
        Intent action = new Intent(context, DataUpdator.class);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: received");
    }
}