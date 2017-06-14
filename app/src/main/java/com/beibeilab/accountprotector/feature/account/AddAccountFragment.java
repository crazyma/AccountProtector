package com.beibeilab.accountprotector.feature.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.feature.password.PasswordGenerateFragment;
import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.AddAccountBinding;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntity;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountFragment extends Fragment implements PasswordGenerateFragment.PasswordGenerateListener, AddAccountFragmentCallback{

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
        mBinding.setCallback(this);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountDatabase = AccountDatabase.getInstance(getContext());

        accountDatabase.getAccountDao().getAllFlowable()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<AccountEntity>, Publisher<AccountEntity>>() {
                    @Override
                    public Publisher<AccountEntity> apply(List<AccountEntity> accountEntities) throws Exception {
                        return Flowable.fromIterable(accountEntities);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<AccountEntity>() {
                    @Override
                    public void onNext(AccountEntity accountEntity) {
                        Timber.d("result : " + accountEntity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onPasswordGenerate(String password) {
        viewModel.setPassword(password);
    }

    @Override
    public void onCreatePGDialog(View view) {
        FragmentActivity activity = getActivity();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = PasswordGenerateFragment.newInstance();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(ft, "dialog");
    }
}
