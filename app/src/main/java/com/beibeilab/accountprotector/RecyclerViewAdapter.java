package com.beibeilab.accountprotector;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beibeilab.accountprotector.databinding.MainItemBinding;

import java.util.List;

/**
 * Created by david on 2017/5/28.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemBindingHolder> {

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

    private int mLayoutResId, mBindResId;

    public RecyclerViewAdapter(@LayoutRes int layoutResId, int bindResId){
        mLayoutResId = layoutResId;
        mBindResId = bindResId;
    }

    private List mList;

    @Override
    public ItemBindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MainItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                mLayoutResId,
                viewGroup,
                false);
        ItemBindingHolder holder = new ItemBindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemBindingHolder holder, int position) {
        Object viewModel = mList.get(position);
        holder.getBinding().setVariable(mBindResId, viewModel);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    public void setMainItemModelList(List itemModelList) {
        this.mList = itemModelList;
    }
}
