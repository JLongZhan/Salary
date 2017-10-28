package com.foxconn.beacon.salary.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Author: JLow
 * Date: 2017/9/17 0017.
 * Time:10:19
 * Describe:
 */

public class AnimationUtil {

    public static void upToDownTranslateAnim(View view) {
        TranslateAnimation ta = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1
        );
        ta.setFillAfter(true);
        ta.setDuration(1000);
        view.startAnimation(ta);
    }

    public static void downToUpTranslateAnim( View view) {
        TranslateAnimation ta = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0
        );
        ta.setFillAfter(true);
        ta.setDuration(1000);
        view.startAnimation(ta);
    }
}
