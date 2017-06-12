package com.beibeilab.accountprotector.util;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by david on 2017/6/5.
 */

public class Util {

    public static GradientDrawable createOvalDrawable(int color, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(radius, radius);
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    public static boolean validString(String string){
        return string != null && !string.trim().equals("");
    }

}
