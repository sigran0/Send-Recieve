package com.sigran0.sendreceive.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
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

    @BindView(R.id.f_item_list_tv)
    TextView tv;

    ItemListHolder.TYPE type;
    LinearLayoutManager layoutManager;


    public ItemListFragment(){
    }

    public void setType(ItemListHolder.TYPE type){
        this.type = type;
    }

    private void hasItem(boolean yes){
        if(yes){
            rv.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_item_list, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

        Log.d(TAG, "initializeLayout: call fucking init");

        final ItemListAdapter itemListAdapter = new ItemListAdapter(getContext(), type);
        layoutManager = new LinearLayoutManager(ItemListFragment.this.getContext());
        rv.setAdapter(itemListAdapter);
        rv.setLayoutManager(layoutManager);

        databaseManager.getNotProceedItemListData2(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ModelManager.ItemDataList result = new ModelManager.ItemDataList();

                for(DataSnapshot item : dataSnapshot.getChildren()) {
                    ModelManager.ItemData temp = item.getValue(ModelManager.ItemData.class);
                    result.getItemDataList().add(temp);
                }

                result.setSize(result.getItemDataList().size());
                itemListAdapter.setData(result);

                if(result.getSize() <= 0)
                    hasItem(false);
                else
                    hasItem(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
