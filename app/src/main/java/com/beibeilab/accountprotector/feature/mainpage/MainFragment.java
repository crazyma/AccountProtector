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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<MainItemViewModel> mainItemViewModelList = viewModel.getMainItemViewModelList();

                        for(int i=0;i<15;i++){
                            MainItemViewModel mainItemViewModel = new MainItemViewModel();
                            mainItemViewModel.setTextName("item " + i);
                            if(i%3 == 0){
                                mainItemViewModel.setResOauthIcon(R.drawable.facebook);
                            }
                            if(i%7 == 0){
                                mainItemViewModel.setResOauthIcon(R.drawable.github);
                            }
                            mainItemViewModelList.add(mainItemViewModel);
                        }

                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
