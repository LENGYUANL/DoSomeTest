package com.example.lengyuan.test4.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by lengyuan on 18-1-5.
 */

public class AppContext extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
