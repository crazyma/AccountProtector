package com.beibeilab.accountprotector.feature.mainpage;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.feature.account.AccountUnit;
import com.beibeilab.accountprotector.room2.AccountEntity;
import com.beibeilab.accountprotector.util.Util;

import timber.log.Timber;

/**
 * Created by david on 2017/5/29.
 */

public class MainItemViewModel {
    private String textName;
    private Drawable drawableIcon;
    private int resOauthIcon, position, color;
    private View.OnClickListener itemClickListener;
    private View.OnLongClickListener itemLongClickListener;

    public MainItemViewModel() {
    }

    public MainItemViewModel(AccountEntity accountEntity) {
        setByAccountEntity(accountEntity);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

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

    public View.OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public View.OnLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    public void setItemLongClickListener(View.OnLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setByAccountEntity(AccountEntity accountEntity){
        textName = accountEntity.getServiceName();
        color = accountEntity.getColor();
        if(accountEntity.getOauth() != null) {
            switch (accountEntity.getOauth()) {
                case AccountUnit.OAUTH_GOOGLE:
                    resOauthIcon = R.drawable.icon_google_small;
                    break;
                case AccountUnit.OAUTH_FACEBOOK:
                    resOauthIcon = R.drawable.icon_facebook_small;
                    break;
                case AccountUnit.OAUTH_TWITTER:
                    resOauthIcon = R.drawable.icon_twitter_small;
                    break;
                case AccountUnit.OAUTH_GITHUB:
                    resOauthIcon = R.drawable.icon_github_small;
                    break;
                default:
            }
        }
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

    @BindingAdapter("itemClickListener")
    public static void setOnItemClickListener(View view, View.OnClickListener clickListener){
        Timber.d("setup item click listener  |  " + clickListener.toString());
        view.setOnClickListener(clickListener);
    }

    @BindingAdapter("itemLongClickListener")
    public static void setOnLongClickListener(View view, View.OnLongClickListener onLongClickListener){
        view.setOnLongClickListener(onLongClickListener);
    }
}
