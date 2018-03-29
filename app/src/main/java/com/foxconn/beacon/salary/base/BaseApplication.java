package com.foxconn.beacon.salary.base;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public class BaseApplication extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        sContext = getApplicationContext();
    }
}
