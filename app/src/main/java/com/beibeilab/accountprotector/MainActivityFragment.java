package com.beibeilab.accountprotector;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.MainFragmentBinding;
import com.beibeilab.accountprotector.viewmodel.MainItemViewModel;
import com.beibeilab.accountprotector.viewmodel.MainListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MainFragmentBinding mBinding;
    private MainListViewModel viewModel;

    private RecyclerView mRecyclerView;

    public MainActivityFragment() {
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
