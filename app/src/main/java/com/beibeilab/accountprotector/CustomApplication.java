package com.beibeilab.accountprotector;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.github.ajalt.reprint.core.Reprint;

import timber.log.Timber;

/**
 * Created by david on 2017/6/9.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Stetho.initializeWithDefaults(this);
        Reprint.initialize(this, new Reprint.Logger() {
            @Override
            public void log(String message) {
                Log.d("Reprint", message);
            }

            @Override
            public void logException(Throwable throwable, String message) {
                Log.e("Reprint", message, throwable);
            }
        });
    }
}
