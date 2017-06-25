package com.beibeilab.accountprotector.feature.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.AccountFragmentBinding;
import com.beibeilab.accountprotector.feature.addaccount.AccountViewModel;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private AccountViewModel mAccountViewModel;
    private AccountFragmentBinding mBinding;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(AccountViewModel accountViewModel){
        AccountFragment fragment = new AccountFragment();
        fragment.mAccountViewModel = accountViewModel;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        mAccountViewModel.setEditable(false);
        mBinding.setAccountViewModel(mAccountViewModel);
        Timber.d("account view model : " + mAccountViewModel.toString());
        return mBinding.getRoot();
    }

}
