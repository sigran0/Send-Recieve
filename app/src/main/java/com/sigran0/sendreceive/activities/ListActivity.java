package com.sigran0.sendreceive.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.fragments.BaseFragment;
import com.sigran0.sendreceive.fragments.MyInfoFragment;
import com.sigran0.sendreceive.fragments.SendStartFragment;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.pagerAdapter.MainPagerAdapter;
import com.sigran0.sendreceive.recycler.adapter.ItemListAdapter;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import butterknife.BindView;

public class ListActivity extends BaseActivity {

    @BindView(R.id.a_item_list_rv)
    RecyclerView rv;

    ItemListAdapter adapter;
    ModelManager.ItemData data;
    ItemListHolder.TYPE type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        data = (ModelManager.ItemData) getIntent().getSerializableExtra("data");
        type = (ItemListHolder.TYPE) getIntent().getSerializableExtra("type");
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_list;
    }

    @Override
    protected void initializeLayout(){
        adapter = new ItemListAdapter(this, type);
        rv.setAdapter(adapter);
    }
}
