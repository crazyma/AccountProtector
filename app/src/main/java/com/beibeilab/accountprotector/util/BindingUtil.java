package com.beibeilab.accountprotector.util;

import android.databinding.BindingAdapter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by david on 2017/6/13.
 */

public class BindingUtil {
    @BindingAdapter("addTextChangedListener")
    public static void addTextChangedListener(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}
