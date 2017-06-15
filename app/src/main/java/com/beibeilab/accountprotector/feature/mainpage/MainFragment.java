package com.beibeilab.accountprotector.feature.mainpage;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.BR;
import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.MainFragmentBinding;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntity;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private MainFragmentBinding mBinding;
    private MainListViewModel viewModel;
    private AccountDatabase accountDatabase;

    private RecyclerView mRecyclerView;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new MainListViewModel();
        viewModel.setItemBindingResId(BR.itemModel);

        List<MainItemViewModel> mainItemViewModelList = new ArrayList<>();

        viewModel.setMainItemViewModelList(mainItemViewModelList);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        mBinding.setListViewModel(viewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = mBinding.recyclerViewMain;

        setupRecyclerView();
    }

    private void setupRecyclerView(){
        accountDatabase = AccountDatabase.getInstance(getContext());
//        accountDatabase.getAccountDao().getAllFlowable()
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Function<List<AccountEntity>, Publisher<AccountEntity>>() {
//                    @Override
//                    public Publisher<AccountEntity> apply(List<AccountEntity> accountEntities) throws Exception {
//                        return Flowable.fromIterable(accountEntities);
//                    }
//                })
//                .map(new Function<AccountEntity, MainItemViewModel>() {
//                    @Override
//                    public MainItemViewModel apply(AccountEntity accountEntity) throws Exception {
//                        MainItemViewModel viewModel = new MainItemViewModel(accountEntity);
//                        return viewModel;
//                    }
//                })
//                .doOnNext(new Consumer<MainItemViewModel>() {
//                    @Override
//                    public void accept(MainItemViewModel mainItemViewModel) throws Exception {
//                        Timber.d("view model title : " + mainItemViewModel.getTextName());
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSubscriber<MainItemViewModel>() {
//                    @Override
//                    public void onNext(MainItemViewModel mainItemViewModel) {
//                        Timber.d("view model : " + mainItemViewModel.getTextName());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Timber.e(t.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Timber.d("on complete");
//                    }
//                });
//                .toList()
//                .doOnSuccess(new Consumer<List<MainItemViewModel>>() {
//                    @Override
//                    public void accept(List<MainItemViewModel> mainItemViewModels) throws Exception {
//                        Timber.d("XDDDDDDDD " + mainItemViewModels.size());
//                    }
//                })
//                .doOnError(new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Timber.e(throwable.toString());
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<MainItemViewModel>>() {
//                    @Override
//                    public void onSuccess(List<MainItemViewModel> mainItemViewModels) {
//                        Timber.d("AAAAAAAAAAAAAAAAAAA");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e("EEEEEEEEEEEEEEEEEEEEE");
//                    }
//                });
//                .subscribeWith(new DisposableSingleObserver<List<MainItemViewModel>>() {
//                    @Override
//                    public void onSuccess(List<MainItemViewModel> mainItemViewModels) {
//                        List<MainItemViewModel> mainItemViewModelList = viewModel.getMainItemViewModelList();
//                        mainItemViewModelList.clear();
//                        mainItemViewModelList.addAll(mainItemViewModelList);
//                        mRecyclerView.getAdapter().notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e(e.toString());
//                    }
//                });
//                .subscribeWith(new DisposableSubscriber<AccountEntity>() {
//                    @Override
//                    public void onNext(AccountEntity accountEntity) {
//                        Timber.d("result : " + accountEntity);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Timber.e(t.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<MainItemViewModel> mainItemViewModelList = viewModel.getMainItemViewModelList();
                mainItemViewModelList.clear();

                List<AccountEntity> accountEntityList = accountDatabase.getAccountDao().getAll();
                for (AccountEntity accountEntity : accountEntityList) {
                    mainItemViewModelList.add(new MainItemViewModel(accountEntity));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
