package com.beibeilab.accountprotector;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.MainItemBinding;
import com.beibeilab.accountprotector.viewmodel.MainItemViewModel;

import java.util.List;

/**
 * Created by david on 2017/5/28.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemBindingHolder> {

    public static class ItemBindingHolder extends RecyclerView.ViewHolder {
        private MainItemBinding binding;

        public ItemBindingHolder(View itemView) {
            super(itemView);
        }

        public MainItemBinding getBinding() {
            return binding;
        }

        public void setBinding(MainItemBinding binding) {
            this.binding = binding;
        }
    }

    private List<MainItemViewModel> mItemModelList;

    @Override
    public ItemBindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MainItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_main,
                viewGroup,
                false);
        ItemBindingHolder holder = new ItemBindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemBindingHolder holder, int position) {
        MainItemViewModel mainItemViewModel = mItemModelList.get(position);
        holder.getBinding().setVariable(BR.itemModel, mainItemViewModel);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (mItemModelList != null)
            return mItemModelList.size();
        return 0;
    }

    public void setMainItemModelList(List<MainItemViewModel> itemModelList) {
        this.mItemModelList = itemModelList;
    }
}
