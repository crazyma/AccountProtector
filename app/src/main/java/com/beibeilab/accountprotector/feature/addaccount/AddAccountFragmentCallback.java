package com.beibeilab.accountprotector.feature.addaccount;

import android.view.View;

/**
 * Created by david on 2017/6/13.
 */

public interface AddAccountFragmentCallback {
    void onInsertSuccessfully();
    void onPickerButtonClicked(View view);
}
