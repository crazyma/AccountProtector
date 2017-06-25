package com.beibeilab.accountprotector.feature.account;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.AccountFragmentBinding;
import com.beibeilab.accountprotector.feature.addaccount.AccountViewModel;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements AccountViewModel.PasswordButtonClickListener {

    private AccountViewModel mAccountViewModel;
    private AccountFragmentBinding mBinding;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(AccountViewModel accountViewModel) {
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
        mBinding.setPasswordClickListener(this);
        Timber.d("account view model : " + mAccountViewModel.toString());
        return mBinding.getRoot();
    }

    @Override
    public void onPasswordButtonClick(View view) {
        Toast.makeText(getContext(), "COPY", Toast.LENGTH_LONG).show();
        copyToClipboard(mAccountViewModel.getPassword());
    }

    private void copyToClipboard(String str) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("text label", str);
        clipboard.setPrimaryClip(clip);
    }
}
