package com.beibeilab.accountprotector.feature.addaccount;

import android.view.MenuItem;

import com.beibeilab.accountprotector.R;

import static com.beibeilab.accountprotector.feature.addaccount.AddAccountActivity.PARAM_ACCOUNT_VIEW_MODEL;

/**
 * Created by david on 2017/7/1.
 */

public class EditAccountFragment extends AddAccountFragment {

    @Override
    protected AccountViewModel getAccountViewModel() {
        return getActivity().getIntent().getParcelableExtra(PARAM_ACCOUNT_VIEW_MODEL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save_account) {
            accountViewModel.commitAccount(getContext(), false);
        }
        return true;
    }
}
