package com.sigran0.sendreceive.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.StaticDataManager;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

public class ItemInfoActivity extends BaseActivity {

    @BindViews({R.id.a_item_info_atv_name,
                R.id.a_item_info_atv_src, R.id.a_item_info_atv_dest,
                R.id.a_item_info_atv_category, R.id.a_item_info_atv_size,
                R.id.a_item_info_atv_user_name,
                R.id.a_item_info_atv_phone})
    AutofitTextView[] atvs;

    @BindView(R.id.a_item_info_bt)
    Button bt;

    @OnClick(R.id.a_item_info_bt)
    void OnClick(){

        if(type.ordinal() == 0){

        } else if(type.ordinal() == 1) {

        }
    }

    @BindView(R.id.a_item_info_sdv)
    SimpleDraweeView sdv;

    ModelManager.ItemData data;
    ItemListHolder.TYPE type;

    private DataListner.DataSendListener getSendListener(final String successString) {
        return new DataListner.DataSendListener() {
            @Override
            public void success() {
                Toast.makeText(ItemInfoActivity.this, successString, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail(String message) {
                Toast.makeText(ItemInfoActivity.this, "알수없는 오류가 있습니다. ㅠ-ㅠ 다시 시도 해 주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }

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
        data = (ModelManager.ItemData) getIntent().getSerializableExtra("data");
        type = (ItemListHolder.TYPE) getIntent().getSerializableExtra("type");

        dbManager.getItemData(data.getImageUrl(), new DataListner.DataReceiveListener<ModelManager.ItemData>() {
            @Override
            public void success(final ModelManager.ItemData data) {

                if(data == null){
                    finish();
                    return;
                }
                atvs[0].setText(data.getItemName());
                atvs[1].setText(data.getStartPos());
                atvs[2].setText(data.getEndPos());
                atvs[3].setText(StaticDataManager.getInstance().getCategoryList()[data.getCategory()]);
                atvs[4].setText(StaticDataManager.getInstance().getSizeList()[data.getSize()]);

                DataListner.DataReceiveListener<ModelManager.UserData> phoneNumberListener =
                        new DataListner.DataReceiveListener<ModelManager.UserData>() {
                            @Override
                            public void success(ModelManager.UserData data) {
                                Log.d("fucking", "success: fucking data " + data.toString());
                                if(data.getUid() != null) {
                                    atvs[5].setText(data.getUsername());
                                    atvs[6].setText(data.getPhoneNumber());
                                } else {
                                    atvs[5].setText("아직 신청자가 없어요 ㅠ-ㅠ");
                                    atvs[6].setText("아직 신청자가 없어요 ㅠ-ㅠ");
                                }
                            }

                            @Override
                            public void fail(String message) {
                                Toast.makeText(ItemInfoActivity.this, "알수없는 오류가 발생헀습니다. 다시 시도 해 주세요", Toast.LENGTH_SHORT).show();
                            }
                        };

                //  배송 버튼 관련 설정
                //  클라이언트이면
                if(type.ordinal() == 0) {
                    dbManager.getUserData(data.getDelivererUid(), phoneNumberListener);

                    if(data.getProcessState() == 1) {
                        bt.setText("배송 진행중 입니다 ^-^");
                        bt.setClickable(false);
                    } else if(data.getProcessState() == 0){
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseManager.getInstance().deleteItemData(data.getImageUrl());
                                showToast("삭제하였습니다.");
                                finish();
                            }
                        });
                    } else if(data.getProcessState() == 2){
                        bt.setText("인계 확인");
                        //  TODO    인계확인 프로세스 만들어야함
                    } else if(data.getProcessState() == 3){
                        bt.setText("물품 전달이 완료되었습니다 ^-^");
                        bt.setClickable(false);
                    }
                    //  배송자이면
                } else {
                    dbManager.getUserData(data.getCustomerUid(), phoneNumberListener);
                    if(data.getProcessState() == 0) {
                        bt.setText("일 진행하기");
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //  배송자가 일감을 받았을 때
                                dbManager.changeUserDataState(data, ModelManager.ITEM_STATE.PROCESSING, getSendListener("일감을 받았습니다."));
                            }
                        });
                    } else if(data.getProcessState() == 1 && data.getDelivererUid().equals(userManager.getUID())) {
                        bt.setText("배송 완료하기");
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dbManager.changeUserDataState(data, ModelManager.ITEM_STATE.DELIVERY_COMPLETE, getSendListener("배송 완료하였습니다."));
                            }
                        });
                    } else if(data.getProcessState() == 2 && data.getDelivererUid().equals(userManager.getUID())){
                        bt.setText("인계 확인이 끝날때 까지 기다려 주세요 ㅠ-ㅠ");
                        bt.setClickable(false);
                    } else if(data.getProcessState() == 3 && data.getDelivererUid().equals(userManager.getUID())) {
                        bt.setText("인계 확인이 끝났습니다. 감사합니다 ^-^");
                        bt.setClickable(false);
                    } else {
                        bt.setVisibility(View.INVISIBLE);
                    }
                }


                DatabaseManager.getInstance().downloadImage(data.getImageUrl(), new DataListner.DataReceiveListener<Uri>() {
                    @Override
                    public void success(Uri uri) {
                        sdv.setImageURI(uri);
                    }

                    @Override
                    public void fail(String message) {
                        Toast.makeText(ItemInfoActivity.this, "알수없는 오류가 발생헀습니다. 다시 시도 해 주세요", Toast.LENGTH_SHORT).show();
                        ItemInfoActivity.this.finish();
                    }
                });
            }

            @Override
            public void fail(String message) {
                Toast.makeText(ItemInfoActivity.this, "알수없는 오류가 있습니다. ㅠ-ㅠ 다시 시도 해 주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
