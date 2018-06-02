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

        startProgress(ListActivity.this);

        DatabaseManager.getInstance().getMyItemList(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                stopProgress();

                ModelManager.ItemDataList result = new ModelManager.ItemDataList();

                for(DataSnapshot item : dataSnapshot.getChildren()) {
                    ModelManager.ItemData temp = item.getValue(ModelManager.ItemData.class);
                    result.getItemDataList().add(temp);
                }

                result.setSize(result.getItemDataList().size());
                data = result;
                adapter.setData(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                stopProgress();
                throw databaseError.toException();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
