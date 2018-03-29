package com.foxconn.beacon.salary.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author: F1331886
 * @date: 2017/11/25 0025.
 * @describe:
 */

public class ActivityUtils {

    public static void startActivity(Activity activity, Class clazz) {
        startActivity(activity, new Bundle(), clazz);
    }


    public static void startActivity(Activity activity, Bundle bundle, Class<Activity> clazz) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
