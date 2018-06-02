package com.sigran0.sendreceive.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.BinderManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.StaticDataManager;
import com.sigran0.sendreceive.pagerAdapter.SendPagerAdapter;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-05-19.
 */

public class SendFragment extends BaseFragment{
    
    public static final String TAG = "fucking";

    private final int GALLERY_CODE=1112;
    private Uri imageUri = null;

    private SendPagerAdapter sendPagerAdapter;

    @BindViews({
            R.id.f_send_mtf_start_pos,
            R.id.f_send_mtf_end_pos,
            R.id.f_send_mtf_price,
            R.id.f_send_mtf_estimate_price,
            R.id.f_send_mtf_name,
            R.id.f_send_mtf_deposit})
    MaterialTextField[] mtfs;

    @BindView(R.id.f_send_sdv_image)
    SimpleDraweeView sdvImage;

    @BindView(R.id.f_send_ms_category)
    MaterialSpinner msCategory;

    @BindView(R.id.f_send_ms_size)
    MaterialSpinner msSize;

    @BindView(R.id.f_send_sv_wrapper)
    ScrollView svWrapper;

    StaticDataManager sdm = StaticDataManager.getInstance();

    @OnClick(R.id.f_send_bt_submit)
    void OnClickSubmit() {
        final String customerUid = sdm.getUserData().getUid();
        String customeName = sdm.getUserData().getUsername();
        String senderUid = "";
        String senderName= "";

        String startPosition = mtfs[0].getEditText().getText().toString();

        if(startPosition.length() <= 0) {
            showToast("배송 시작 지점을 입력 해 주세요");
            return;
        }

        String endPosition = mtfs[1].getEditText().getText().toString();

        if(endPosition.length() <= 0) {
            showToast("배송 목적 지점을 입력 해 주세요");
            return;
        }

        if(mtfs[2].getEditText().getText().toString().length() <= 0) {
            showToast("물품의 가격을 입력 해 주세요");
            return;
        }

        int price = Integer.parseInt(mtfs[2].getEditText().getText().toString());

        if(mtfs[3].getEditText().getText().toString().length() <= 0) {
            showToast("물품의 예상 가격을 입력 해 주세요");
            return;
        }

        String name = mtfs[4].getEditText().getText().toString();

        if(name.length() <= 0) {
            showToast("물품의 이름을 입력 해 주세요");
            return;
        }

        int category = msCategory.getSelectedIndex();
        int size = msSize.getSelectedIndex();
        int processState = 0;
        long timestamp = System.currentTimeMillis() / 1000;
        final int fee = estimateFee(category, size);
        final int desposit = estimateDeposit(fee);

        ModelManager.ItemData data = new ModelManager.ItemData();
        data.setTimestamp(timestamp);
        data.setItemName(name);
        data.setCustomerUid(customerUid);
        data.setCustomerName(customeName);
        data.setDelivererUid(senderUid);
        data.setDelivererName(senderName);
        data.setStartPos(startPosition);
        data.setEndPos(endPosition);
        data.setPrice(price);
        data.setEstimatePrice(fee);
        data.setCategory(category);
        data.setSize(size);
        data.setProcessState(processState);

        if(desposit < 0){
            showToast("예치금이 부족합니다 ㅠ-ㅠ");
            return;
        }

        startProgress();

        databaseManager.saveItemData(data, imageUri, new DataListner.DataSendListener() {
            @Override
            public void success() {
                databaseManager.addUserMoney(customerUid, fee * -1, new DataListner.DataSendListener() {
                    @Override
                    public void success() {
                        showToast("배송 요청이 완료되었습니다.");
                        finishNoAnimation();
                        stopProgress();
                    }

                    @Override
                    public void fail(String message) {
                        Log.d(TAG, "fail: " + message);
                        showToast("오류가 발생했습니다.");
                        finishNoAnimation();
                        stopProgress();
                    }
                });
            }

            @Override
            public void fail(String message) {
                stopProgress();
                Log.d(TAG, "fail: " + message);
                showToast("오류가 발생했습니다.");
                finishNoAnimation();
            }
        });
    }

    @OnClick(R.id.f_send_sdv_image)
    void OnClickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        getBaseActivity().startActivityForResult(intent, GALLERY_CODE);

        binderManager.unbind("send_image");
        binderManager.bind("send_image", new BinderManager.BinderInterface<Uri>() {
            @Override
            public void update(Uri data) {
                imageUri = data;
                sdvImage.setImageURI(imageUri);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_send, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setFeeText(int fee) {
        int deposit = estimateDeposit(fee);

        mtfs[3].getEditText().setText(String.format("%d 원", fee));

        if(deposit >= 0) {
            mtfs[5].getEditText().setText(String.format("%d 원", deposit));
            mtfs[5].getEditText().setTextColor(Color.WHITE);
        } else {
            mtfs[5].getEditText().setText(String.format("%d 원", deposit));
            mtfs[5].getEditText().setTextColor(Color.RED);
        }

    }

    private int estimateFee(int category, int size){
        return StaticDataManager.estimateFee(category, size);
    }

    private int estimateDeposit(int fee) {
        return sdm.getUserData().getMoney() - fee;
    }

    @Override
    protected void initializeLayout(){

        MaterialSpinner.OnItemSelectedListener listener = new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                int fee = estimateFee(msCategory.getSelectedIndex(), msSize.getSelectedIndex());
                setFeeText(fee);
            }
        };

        msCategory.setOnItemSelectedListener(listener);
        msSize.setOnItemSelectedListener(listener);

        setFeeText(estimateFee(0, 0));

        int count = 0;
        for (final MaterialTextField mtf : mtfs) {
            if(count == 3 || count == 5){
                mtf.toggle();
                mtf.setClickable(false);
                mtf.getEditText().setClickable(false);
                mtf.getEditText().setFocusable(false);
            } else {
                mtf.expand();
                mtf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mtf.isExpanded()) {
                            mtf.getEditText().setText("");
                            mtf.toggle();
                        } else {
                            mtf.toggle();
                        }
                    }
                });
            }
            count += 1;
        }

        msCategory.setHint("물품 카테고리");
        msCategory.setItems(StaticDataManager.getInstance().getCategoryList());

        msSize.setHint("물품 크기");
        msSize.setItems(StaticDataManager.getInstance().getSizeList());

        hideKeyboard();

        svWrapper.fullScroll(View.FOCUS_UP);
    }
}
