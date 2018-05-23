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

    private ModelManager.TempData aContainer;

    public ItemListFragment(){

    }

    public void setData(ModelManager.TempData data) {
        Log.d(TAG, "setData: " + data.toString());
        aContainer = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_item_list, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

        Log.d(TAG, "initializeLayout: " + aContainer.toString());

        if(aContainer == null)
            throw new NullPointerException("you must call setData");

        ItemListAdapter itemListAdapter = new ItemListAdapter(getContext(), aContainer.getType());
        itemListAdapter.setData(aContainer.getData());
        rv.setAdapter(itemListAdapter);
    }
}
