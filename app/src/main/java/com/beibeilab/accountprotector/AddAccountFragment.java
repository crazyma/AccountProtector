package com.beibeilab.accountprotector;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.AddAccountBinding;
import com.beibeilab.accountprotector.viewmodel.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountFragment extends Fragment {

    private AddAccountBinding mBinding;
    private AccountViewModel viewModel;

    public AddAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new AccountViewModel();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_account, container, false);
        mBinding.setAccountViewModel(viewModel);

        return mBinding.getRoot();
    }

}
