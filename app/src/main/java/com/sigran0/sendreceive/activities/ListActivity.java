package com.sigran0.sendreceive.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
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

    private void hasItem(boolean yes){
        if(yes){
            rv.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
    }

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

        DataListner.DataReceiveListener<ModelManager.ItemDataList> listener = new DataListner.DataReceiveListener<ModelManager.ItemDataList>() {
            @Override
            public void success(ModelManager.ItemDataList result) {
                data = result;
                adapter.setData(data);

                if(result.getSize() <= 0)
                    hasItem(false);
                else
                    hasItem(true);
            }

            @Override
            public void fail(String message) {
                stopProgress();
                showToast("알 수 없는 오류가 발생했습니다 ㅠ-ㅠ 다시 시도해 주세요.");
                finish();
            }
        };

        //  클라이언트면
        if (type.ordinal() == 0)
            DatabaseManager.getInstance().getMyItemList(userManager.getUID(), listener);
        //  딜리버면
        else if(type.ordinal() == 1)
            DatabaseManager.getInstance().getMyWorkList(userManager.getUID(), listener);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
