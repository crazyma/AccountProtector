package com.beibeilab.accountprotector.viewmodel;

import android.graphics.drawable.Drawable;

/**
 * Created by david on 2017/5/29.
 */

public class MainItemViewModel {
    private String textName;
    private Drawable drawableIcon, drawableOauthIcon;

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public Drawable getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(Drawable drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public Drawable getDrawableOauthIcon() {
        return drawableOauthIcon;
    }

    public void setDrawableOauthIcon(Drawable drawableOauthIcon) {
        this.drawableOauthIcon = drawableOauthIcon;
    }
}
