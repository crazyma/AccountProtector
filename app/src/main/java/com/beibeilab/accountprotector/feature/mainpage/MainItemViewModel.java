package com.beibeilab.accountprotector.feature.mainpage;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.beibeilab.accountprotector.util.Util;

/**
 * Created by david on 2017/5/29.
 */

public class MainItemViewModel {
    private String textName;
    private Drawable drawableIcon;
    private int resOauthIcon;

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

    public int getResOauthIcon() {
        return resOauthIcon;
    }

    public void setResOauthIcon(int resOauthIcon) {
        this.resOauthIcon = resOauthIcon;
    }

    @BindingAdapter({"ovalColor"})
    public static void setOvalBackground(ImageView imageView, int color){
        Drawable drawable = Util.createOvalDrawable(color, imageView.getLayoutParams().width);
        imageView.setBackground(drawable);
    }

    @BindingAdapter({"oauthIcon"})
    public static void setOauthIcon(ImageView imageView, int resId){
        if(resId == 0){
            imageView.setVisibility(View.GONE);
        }else{
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(resId);
        }
    }
}
