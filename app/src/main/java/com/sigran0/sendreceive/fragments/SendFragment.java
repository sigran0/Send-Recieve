package com.sigran0.sendreceive.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.BinderManager;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.pagerAdapter.SendPagerAdapter;
import com.sigran0.sendreceive.views.LockableViewPager;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-05-19.
 */

public class SendFragment extends BaseFragment{

    private final int GALLERY_CODE=1112;
    private Uri imageUri = null;
    private LatLng startLatLng = null;

    private SendPagerAdapter sendPagerAdapter;

    @BindViews({R.id.f_send_mtf_start_pos, R.id.f_send_mtf_end_pos, R.id.f_send_mtf_price, R.id.f_send_mtf_estimate_price})
    MaterialTextField[] mtfs;

    @BindView(R.id.f_send_sdv_image)
    SimpleDraweeView sdvImage;

    @BindView(R.id.f_send_ms_category)
    MaterialSpinner msCategory;

    @BindView(R.id.f_send_ms_size)
    MaterialSpinner msSize;

    @BindView(R.id.f_send_sv_wrapper)
    ScrollView svWrapper;

    @OnClick(R.id.f_send_bt_submit)
    void OnClickSubmit() {
        String customerUid = userManager.getUID();
        String senderUid = null;
        String startPosition = mtfs[0].getEditText().getText().toString();
        String endPosition = mtfs[1].getEditText().getText().toString();
        int price = Integer.parseInt(mtfs[2].getEditText().getText().toString());
        int estimate_price = Integer.parseInt(mtfs[3].getEditText().getText().toString());
        int category = msCategory.getSelectedIndex();
        int size = msSize.getSelectedIndex();
        int processState = 0;

        ModelManager.ItemData data = new ModelManager.ItemData();
        data.setCustomerUid(customerUid);
        data.setSenderUid(senderUid);
        data.setStartPos(startPosition);
        data.setEndPos(endPosition);
        data.setPrice(price);
        data.setEstimatePrice(estimate_price);
        data.setCategory(category);
        data.setSize(size);
        data.setProcessState(processState);
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

    @Override
    protected void initializeLayout(){

        for (final MaterialTextField mtf : mtfs) {
            mtf.expand();
            mtf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mtf.isExpanded()) {
                        mtf.getEditText().setText("");
                        mtf.toggle();
                    } else {
                        mtf.toggle();
                    }
                }
            });
        }

        mtfs[0].getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    Toast.makeText(SendFragment.this.getContext(), "good", Toast.LENGTH_SHORT).show();
            }
        });

        msCategory.setHint("물품 카테고리");
        msCategory.setItems("일반", "식품", "냉동품", "깨지기 쉬운것", "전자제품", "취급주의");

        msSize.setHint("물품 크기");
        msSize.setItems("아주 작음", "작음", "보통", "큼", "아주 큼");

        hideKeyboard();

        svWrapper.fullScroll(View.FOCUS_UP);
    }
}
