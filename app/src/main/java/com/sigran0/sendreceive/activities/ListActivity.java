package com.sigran0.sendreceive.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.fragments.BaseFragment;
import com.sigran0.sendreceive.fragments.MyInfoFragment;
import com.sigran0.sendreceive.fragments.SendStartFragment;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.pagerAdapter.MainPagerAdapter;
import com.sigran0.sendreceive.recycler.adapter.ItemListAdapter;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import butterknife.BindView;

public class ListActivity extends BaseActivity {

    @BindView(R.id.a_item_list_rv)
    RecyclerView rv;

    @BindView(R.id.a_item_list_tv)
    TextView tv;

    ItemListAdapter adapter;
    LinearLayoutManager layoutManager;

    ModelManager.ItemDataList data;
    ItemListHolder.TYPE type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_list;
    }

    @Override
    protected void initializeLayout(){
        type = (ItemListHolder.TYPE) getIntent().getSerializableExtra("type");

        layoutManager = new LinearLayoutManager(this);
        adapter = new ItemListAdapter(this, type);

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume(){
        super.onResume();

        startProgress(ListActivity.this);

        DatabaseManager.getInstance().getMySendListData(new DataListner.DataReceiveListener<ModelManager.ItemDataList>() {
            @Override
            public void success(ModelManager.ItemDataList data) {
                if(data == null || data.getSize() == 0)
                    tv.setVisibility(View.VISIBLE);
                adapter.setData(data);
                stopProgress();
            }

            @Override
            public void fail(String message) {
                Log.d("fucking", "fail: " + message);
                Toast.makeText(ListActivity.this, "원인불명의 오류가 발생했습니다 ㅠ_ㅠ\n다시 시도 해 주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
