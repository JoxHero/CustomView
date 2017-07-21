package com.zyp.customview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by zyp on 2017/5/11.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d("zyp", "onActivityCreated: ");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d("zyp", "onActivityStarted: ");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d("zyp", "onActivityResumed: ");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d("zyp", "onActivityPaused: ");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d("zyp", "onActivityStopped: ");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d("zyp", "onActivitySaveInstanceState: ");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d("zyp", "onActivityDestroyed: ");
            }
        });
    }
}
