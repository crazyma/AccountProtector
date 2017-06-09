package com.beibeilab.accountprotector;

import android.accounts.Account;
import android.arch.persistence.room.Room;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.AddAccountBinding;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntity;
import com.beibeilab.accountprotector.viewmodel.AccountViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountFragment extends Fragment {

    private AddAccountBinding mBinding;
    private AccountViewModel viewModel;

    private AccountDatabase accountDatabase;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                AccountDatabase.class, "db-account").build();


        new Thread(new Runnable() {
            @Override
            public void run() {
//                accountDatabase.getAccountDao().insert(
//                        new AccountEntity("david@25sprout.com","password","crazyma","david@25sprout.com","")
//                );

                List<AccountEntity> list = accountDatabase.getAccountDao().getAll();
                Timber.d("XDDDD  " + list.get(0).getAccount() + " " + list.get(0).getPassword());

            }
        }).start();


    }
}
