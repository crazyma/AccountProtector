package com.beibeilab.accountprotector;

import android.accounts.Account;
import android.arch.persistence.room.Room;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.AndroidRuntimeException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.AddAccountBinding;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntity;
import com.beibeilab.accountprotector.viewmodel.AccountViewModel;
import com.facebook.stetho.Stetho;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
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

        accountDatabase = AccountDatabase.getInstance(getContext());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<AccountEntity> list = accountDatabase.getAccountDao().getAll();
//                for (AccountEntity accountEntity : list) {
//                    Timber.d("result:  " + accountEntity);
//                }
//
//
//            }
//        }).start();

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
}
