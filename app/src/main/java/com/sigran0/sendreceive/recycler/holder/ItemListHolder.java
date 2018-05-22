package com.sigran0.sendreceive.recycler.holder;

import android.view.View;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.ModelManager;

import butterknife.BindViews;
import butterknife.ButterKnife;
import me.grantland.widget.AutofitTextView;

public class ItemListHolder extends BaseHolder<ModelManager.ItemData> {

    public static enum TYPE {
        CLIENT,
        DELIVERER
    }

    @BindViews({
        R.id.h_item_list_title_item_name, R.id.h_item_list_item_name,
        R.id.h_item_list_title_user_name, R.id.h_item_list_user_name,
        R.id.h_item_list_title_info, R.id.h_item_list_info})
    AutofitTextView[] atvs;
    private TYPE type;
    private ModelManager.ItemData data;

    public ItemListHolder(View view, TYPE type) {
        super(view);
        this.type = type;
    }

    @Override
    protected void initializeLayout(){

        if(this.type == TYPE.CLIENT) {
            atvs[0].setText("상품 이름");
            atvs[2].setText("배송자 정보");
            atvs[4].setText("상품 처리 정보");
        } else {
            atvs[0].setText("상품 이름");
            atvs[2].setText("신청자 정보");
            atvs[4].setText("배송완료 확인");
        }
    }

    @Override
    public void setData(ModelManager.ItemData data) {
        this.data = data;

        if(this.type == TYPE.CLIENT) {
            atvs[1].setText(data.getItemName());

        } else {

        }
    }
}
