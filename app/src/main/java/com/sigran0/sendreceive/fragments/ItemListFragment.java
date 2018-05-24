package com.sigran0.sendreceive.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.recycler.adapter.ItemListAdapter;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

public class ItemListFragment extends BaseFragment {
    
    public static final String TAG = "fucking";

    @BindView(R.id.f_item_list_rv)
    RecyclerView rv;

    ItemListHolder.TYPE type;
    LinearLayoutManager layoutManager;


    public ItemListFragment(){
    }

    public void setType(ItemListHolder.TYPE type){
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_item_list, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

        final ItemListAdapter itemListAdapter = new ItemListAdapter(getContext(), type);
        layoutManager = new LinearLayoutManager(ItemListFragment.this.getContext());
        rv.setAdapter(itemListAdapter);
        rv.setLayoutManager(layoutManager);
        startProgress();
        databaseManager
                .getNotProceedItemListData(
                        new DataListner.DataReceiveListener<ModelManager.ItemDataList>() {
            @Override
            public void success(ModelManager.ItemDataList data) {
                Log.d(TAG, "success: " + data);
                itemListAdapter.setData(data);
                stopProgress();
            }

            @Override
            public void fail(String message) {
                Log.d(TAG, "fail: " + message);
                stopProgress();
            }
        });}
}
