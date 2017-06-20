package com.beibeilab.accountprotector.feature.mainpage;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.beibeilab.accountprotector.BR;

import java.util.List;

/**
 * Created by david on 2017/6/5.
 */

public class MainListViewModel extends BaseObservable {
    private List<MainItemViewModel> mainItemViewModelList;
    private int itemBindingResId;

    @Bindable
    public List<MainItemViewModel> getMainItemViewModelList() {
        return mainItemViewModelList;
    }

    public void setMainItemViewModelList(List<MainItemViewModel> mainItemViewModelList) {
        this.mainItemViewModelList = mainItemViewModelList;
        notifyPropertyChanged(BR.listViewModel);
    }

    public int getItemBindingResId() {
        return itemBindingResId;
    }

    public void setItemBindingResId(int itemBindingResId) {
        this.itemBindingResId = itemBindingResId;
    }
}
