package com.beibeilab.accountprotector.feature.mainpage;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beibeilab.accountprotector.BR;
import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.MainFragmentBinding;
import com.beibeilab.accountprotector.feature.account.AccountFragment;
import com.beibeilab.accountprotector.feature.addaccount.AccountViewModel;
import com.beibeilab.accountprotector.room2.AccountDatabase;
import com.beibeilab.accountprotector.room2.AccountEntity;
import com.github.ajalt.reprint.core.AuthenticationResult;
import com.github.ajalt.reprint.core.Reprint;
import com.github.ajalt.reprint.rxjava2.RxReprint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends LifecycleFragment {

    private MainFragmentBinding mBinding;
    private MainListViewModel viewModel;
    private AccountDatabase accountDatabase;
    private LiveData<List<AccountEntity>> liveData;

    private AlertDialog mFingerprintDialog;
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
        ((MainActivity) getActivity()).showFAB();

        mRecyclerView = mBinding.recyclerViewMain;
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        testQuery();
    }

    private void testQuery() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> stringList =
                        AccountDatabase.getInstance(getContext())
                                .getAccountDao()
                                .getDistinctAccountEntity();
                Timber.d("-----------------");
                for (String s : stringList) {
                    if (s == null) {
                        Timber.d("null is null");
                    } else {
                        Timber.d("???? %s", s);
                    }
                }
                Timber.d("-----------------");

            }
        }).start();
    }

    private void setupRecyclerView() {
        setupRecyclerViewWithLiveData();
//        setupRecyclerViewWithSingleDaoInThread();
    }

    private void setupRecyclerViewWithLiveData() {
        accountDatabase = AccountDatabase.getInstance(getContext());
        liveData = accountDatabase.getAccountDao().getAllFromLiveData();
        liveData.observe(MainFragment.this, new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(@Nullable List<AccountEntity> accountEntities) {

                List<MainItemViewModel> mainItemViewModelList = viewModel.getMainItemViewModelList();
                mainItemViewModelList.clear();

                for (int i = 0; i < accountEntities.size(); i++) {
                    AccountEntity accountEntity = accountEntities.get(i);

                    MainItemViewModel viewModel = new MainItemViewModel(accountEntity);
                    viewModel.setItemClickListener(itemClickListener);
                    viewModel.setItemLongClickListener(itemLongClickListener);
                    viewModel.setPosition(i);
                    mainItemViewModelList.add(viewModel);
                }

                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void setupRecyclerViewWithSingleDaoInThread() {
        accountDatabase = AccountDatabase.getInstance(getContext());
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
                for (int i = 0; i < accountEntityList.size(); i++) {
                    AccountEntity accountEntity = accountEntityList.get(i);

                    MainItemViewModel viewModel = new MainItemViewModel(accountEntity);
                    viewModel.setItemClickListener(itemClickListener);
                    viewModel.setPosition(i);
                    mainItemViewModelList.add(viewModel);
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

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int index = (int) view.getTag();
            showFingerprintDialog();
            RxReprint.authenticate()
                    .subscribeWith(new DisposableSubscriber<AuthenticationResult>() {
                        @Override
                        public void onNext(AuthenticationResult authenticationResult) {
                            switch (authenticationResult.status) {
                                case SUCCESS:
                                    Timber.d("!!!!!!!  SUCCESS");
                                    jump2AccountFragment(index);
                                    break;
                                case NONFATAL_FAILURE:
                                    Timber.d("!!!!!!!  NONFATAL_FAILURE");
                                    break;
                                case FATAL_FAILURE:
                                    Timber.d("!!!!!!!  FATAL_FAILURE");
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onComplete() {
                            mFingerprintDialog.dismiss();
                        }
                    });
        }
    };

    private View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
            createDeleteDialog((int) view.getTag());
            return true;
        }
    };

    private void jump2AccountFragment(int index) {
        List<AccountEntity> accountEntityList = liveData.getValue();

        AccountViewModel accountViewModel = new AccountViewModel(accountEntityList.get(index));

        AccountFragment fragment = AccountFragment.newInstance(accountViewModel);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showFingerprintDialog() {
        mFingerprintDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title)
                .setMessage("請使用指紋辨識")
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Reprint.cancelAuthentication();
                    }
                })
                .create();
        mFingerprintDialog.show();
    }

    private void createDeleteDialog(final int index) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<AccountEntity> accountEntityList = liveData.getValue();
                        deleteAccount(accountEntityList.get(index));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void deleteAccount(final AccountEntity accountEntity) {

        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                accountDatabase.getAccountDao().delete(accountEntity);
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Delete Fail", Toast.LENGTH_LONG).show();
                    }
                });
    }


}
