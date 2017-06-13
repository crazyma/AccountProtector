package com.beibeilab.accountprotector.viewmodel;

import android.view.View;
import android.widget.CheckedTextView;

/**
 * Created by david on 2017/6/13.
 */

public class PasswordGenerateViewModel {

    public void checkedTextViewClickListener(View view){
        ((CheckedTextView)view).toggle();
    }

}
