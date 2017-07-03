package com.beibeilab.accountprotector.util;

import android.databinding.BindingAdapter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.beibeilab.accountprotector.R;

/**
 * Created by david on 2017/6/13.
 */

public class BindingUtil {
    @BindingAdapter("addTextChangedListener")
    public static void addTextChangedListener(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    @BindingAdapter({"ovalColor"})
    public static void setOvalDrawable(ImageView imageView, int color){
        imageView.setBackground(Util.createOvalDrawable(color, imageView.getLayoutParams().width));
    }
}
