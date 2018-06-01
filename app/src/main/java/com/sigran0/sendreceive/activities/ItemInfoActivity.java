package com.sigran0.sendreceive.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.StaticDataManager;
import com.sigran0.sendreceive.recycler.adapter.ItemListAdapter;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

public class ItemInfoActivity extends BaseActivity {

    @BindViews({R.id.a_item_info_atv_name,
                R.id.a_item_info_atv_src, R.id.a_item_info_atv_dest,
                R.id.a_item_info_atv_category, R.id.a_item_info_atv_size})
    AutofitTextView[] atvs;

    @BindView(R.id.a_item_info_bt)
    Button bt;

    @OnClick(R.id.a_item_info_bt)
    void OnClick(){

        if(type.ordinal() == 0){
            DatabaseManager.getInstance().deleteItemData(data.getImageUrl());
            finish();
        } else if(type.ordinal() == 1) {

        }
    }

    @BindView(R.id.a_item_info_sdv)
    SimpleDraweeView sdv;

    ModelManager.ItemData data;
    ItemListHolder.TYPE type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_item_info;
    }

    @Override
    protected void initializeLayout(){

        startProgress(ItemInfoActivity.this);

        data = (ModelManager.ItemData) getIntent().getSerializableExtra("data");
        type = (ItemListHolder.TYPE) getIntent().getSerializableExtra("type");

        if(type.ordinal() == 1) {
            bt.setText("일 진행하기");
        }

        DatabaseManager.getInstance().downloadImage(data.getImageUrl(), new DataListner.DataReceiveListener<Uri>() {
            @Override
            public void success(Uri uri) {
                atvs[0].setText(data.getItemName());
                atvs[1].setText(data.getStartPos());
                atvs[2].setText(data.getEndPos());
                atvs[3].setText(StaticDataManager.getInstance().getCategoryList()[data.getCategory()]);
                atvs[4].setText(StaticDataManager.getInstance().getSizeList()[data.getSize()]);

                sdv.setImageURI(uri);

                stopProgress();
            }

            @Override
            public void fail(String message) {
                Toast.makeText(ItemInfoActivity.this, "알수없는 오류가 발생헀습니다. 다시 시도 해 주세요", Toast.LENGTH_SHORT).show();
                ItemInfoActivity.this.finish();
            }
        });
    }
}
