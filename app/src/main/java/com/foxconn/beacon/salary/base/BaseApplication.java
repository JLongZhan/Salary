package com.foxconn.beacon.salary.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        LitePal.initialize(this);
        SQLiteDatabase liteDatabase = LitePal.getDatabase();
        sContext = getApplicationContext();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

}
