package com.sigran0.sendreceive.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.ModelManager;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

public class ItemListHolder extends BaseHolder<ModelManager.ItemData> {

    public static enum TYPE {
        CLIENT,
        DELIVERER
    }

    public static final String TAG = "fucking";

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
            atvs[4].setText("배송 상태");
        } else {
            atvs[0].setText("상품 이름");
            atvs[2].setText("신청자 정보");
            atvs[4].setText("배송 상태");
        }
    }

    @Override
    public void setData(ModelManager.ItemData data) {
        this.data = data;
        atvs[1].setText(data.getItemName());

        if(this.type == TYPE.CLIENT) {
            if(data.getProcessState() == ModelManager.ItemState.NO_ONE_ACCEPT.ordinal()) {
                atvs[3].setText("");
                atvs[5].setText("아직 처리되지 않았습니다.");
            } else if(data.getProcessState() == ModelManager.ItemState.DELIVERING.ordinal()) {
                atvs[3].setText(data.getDelivererName());
                atvs[5].setText("배달중 입니다.");
            } else if(data.getProcessState() == ModelManager.ItemState.COMPLETE.ordinal()) {
                atvs[3].setText(data.getDelivererName());
                atvs[5].setText("배달 완료");
            }
        } else {
            if(data.getProcessState() == ModelManager.ItemState.NO_ONE_ACCEPT.ordinal()) {
                atvs[3].setText("");
                atvs[5].setText("아직 처리되지 않았습니다.");
            } else if(data.getProcessState() == ModelManager.ItemState.DELIVERING.ordinal()) {
                atvs[3].setText(data.getDelivererName());
                atvs[5].setText("배달중 입니다.");
            } else if(data.getProcessState() == ModelManager.ItemState.COMPLETE.ordinal()) {
                atvs[3].setText(data.getDelivererName());
                atvs[5].setText("배달 완료");
            }
        }
    }
}
