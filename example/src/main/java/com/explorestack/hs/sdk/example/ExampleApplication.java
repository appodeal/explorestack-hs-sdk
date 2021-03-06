package com.explorestack.hs.sdk.example;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.explorestack.hs.sdk.HSApp;
import com.explorestack.hs.sdk.HSAppConfig;
import com.explorestack.hs.sdk.HSAppInitializeListener;
import com.explorestack.hs.sdk.HSError;
import com.explorestack.hs.sdk.HSLogger;
import com.explorestack.hs.sdk.connector.appodeal.HSAppodealConnector;
import com.explorestack.hs.sdk.service.appsflyer.HSAppsflyerService;
import com.explorestack.hs.sdk.service.facebook.HSFacebookService;
import com.explorestack.hs.sdk.service.firebase.HSFirebaseService;

import java.util.List;

public class ExampleApplication extends MultiDexApplication {

    private static final String TAG = ExampleApplication.class.getSimpleName();

    private static boolean isInitializingHsApp = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize HSApp
        initializeHSApp(this);
    }

    static void initializeHSApp(@NonNull Context context) {
        if (isInitializingHsApp) {
            return;
        }
        isInitializingHsApp = true;

        //Enable HSApp, services and connectors logs
        HSLogger.setEnabled(true);

        //Create connector for Appodeal
        HSAppodealConnector appodealConnector = new HSAppodealConnector();

        //Create service for AppsFlyer
        HSAppsflyerService appsflyerService = new HSAppsflyerService("ewVfXy4eavTcRaRzrsKWAA");

        //Create service for Firebase
        HSFirebaseService firebaseService = new HSFirebaseService();

        //Create service for Facebook
        HSFacebookService facebookService = new HSFacebookService();

        //Create HSApp configuration
        HSAppConfig appConfig = new HSAppConfig()
                .setDebugEnabled(true)
                .withConnectors(appodealConnector)
                .withServices(appsflyerService, facebookService, firebaseService);

        //Initialize HSApp
        HSApp.initialize(context, appConfig, new HSAppInitializeListener() {
            @Override
            public void onAppInitialized(@Nullable List<HSError> errors) {
                if (errors != null) {
                    for (HSError error : errors) {
                        Log.e(TAG, "HSApp: [Error]: " + error.toString());
                    }
                }
                //HSApp initialization finished, now you can initialize required SDK
                isInitializingHsApp = false;
            }
        });
    }
}
