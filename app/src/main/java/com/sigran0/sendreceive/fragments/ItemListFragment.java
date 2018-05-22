package com.sigran0.sendreceive.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sigran0.sendreceive.R;
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

    private ItemListHolder.TYPE aType;
    private List<ModelManager.ItemData> aData;

    public ItemListFragment(){

    }

    public void setType(ItemListHolder.TYPE type) {
        Log.d(TAG, "setType: " + type);
        aType = type;
    }

    public void setData(List<ModelManager.ItemData> data) {
        Log.d(TAG, "setData: " + data.toString());
        aData = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_item_list, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

        Log.d(TAG, "initializeLayout: " + aType + ", " + aData.toString());
        if(aType == null)
            throw new NullPointerException("you must call setType");

        if(aData == null)
            throw new NullPointerException("you must call setData");

        ItemListAdapter itemListAdapter = new ItemListAdapter(getContext(), aType);
        rv.setAdapter(itemListAdapter);
    }
}
