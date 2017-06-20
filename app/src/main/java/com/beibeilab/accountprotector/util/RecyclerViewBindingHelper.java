package com.beibeilab.accountprotector.util;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beibeilab.accountprotector.feature.mainpage.RecyclerViewAdapter;

import java.util.List;

/**
 * Created by david on 2017/6/6.
 */

public class RecyclerViewBindingHelper {

    @BindingAdapter({"numberInRow","layout_vertical"})
    public static void bindLayoutManager(@NonNull RecyclerView recyclerView, int numberInRow, boolean vertical) {
        int orientation = vertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL;
        RecyclerView.LayoutManager layoutManager;

        if(numberInRow == 1){
            layoutManager = new LinearLayoutManager(
                    recyclerView.getContext(), orientation, false);
        }else{
            layoutManager = new GridLayoutManager(
                    recyclerView.getContext(), orientation, numberInRow, false);
        }

        recyclerView.setLayoutManager(layoutManager);
    }

    @BindingAdapter({"item", "item_layout", "bind_resource"})
    public static void setRecyclerViewAdapter(@NonNull RecyclerView recyclerView, List items, @LayoutRes int layoutResId, int bindResId){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(layoutResId, bindResId);
        adapter.setMainItemModelList(items);
        recyclerView.setAdapter(adapter);
    }



}
