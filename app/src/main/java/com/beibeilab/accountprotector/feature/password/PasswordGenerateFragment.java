package com.beibeilab.accountprotector.feature.password;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.PasswordGenerateBinding;
import com.beibeilab.accountprotector.feature.account.AddAccountFragment;
import com.beibeilab.accountprotector.util.PasswordGenerator;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordGenerateFragment extends DialogFragment implements PasswordGenerateViewModel.PGViewModelListener {

    public interface PasswordGenerateListener{
        void onPasswordGenerate(String password);
    }

    private PasswordGenerateListener mListener;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddAccountFragment) getTargetFragment();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mViewModel = new PasswordGenerateViewModel();
        mViewModel.setListener(this);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_generate, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void viewModelCallback(int length, boolean[] ruleArray) {
        PasswordGenerator generator = new PasswordGenerator(length, ruleArray);
        if(mListener != null) {
            mListener.onPasswordGenerate(generator.generate());
        }
        dismiss();
    }
}
