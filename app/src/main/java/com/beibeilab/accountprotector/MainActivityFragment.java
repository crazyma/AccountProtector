package com.beibeilab.accountprotector;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.viewmodel.MainItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MainAdapter mMainAdapter;
    private RecyclerView mRecyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycer_view_main);

        setupRecyclerView();
    }

    private void setupRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        List<MainItemViewModel> mainItemViewModelList = new ArrayList<>();

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

        mMainAdapter = new MainAdapter();
        mRecyclerView.setAdapter(mMainAdapter);

        mMainAdapter.setMainItemModelList(mainItemViewModelList);
        mMainAdapter.notifyDataSetChanged();
    }
}
