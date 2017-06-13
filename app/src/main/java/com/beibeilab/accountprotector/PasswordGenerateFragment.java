package com.beibeilab.accountprotector;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.PasswordGenerateBinding;
import com.beibeilab.accountprotector.viewmodel.PasswordGenerateViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordGenerateFragment extends DialogFragment {

    private PasswordGenerateBinding mBinding;
    private PasswordGenerateViewModel mViewModel;

    public PasswordGenerateFragment() {
        // Required empty public constructor
    }

    public static PasswordGenerateFragment newInstance() {
        PasswordGenerateFragment f = new PasswordGenerateFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mViewModel = new PasswordGenerateViewModel();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_generate, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

}
