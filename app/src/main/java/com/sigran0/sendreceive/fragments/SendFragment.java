package com.sigran0.sendreceive.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.BinderManager;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.pagerAdapter.SendPagerAdapter;
import com.sigran0.sendreceive.views.LockableViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-05-19.
 */

public class SendFragment extends BaseFragment{

    @BindView(R.id.f_send_bt_next)
    Button btNext;

    @BindView(R.id.f_send_lvp)
    LockableViewPager vp;

    @OnClick(R.id.f_send_bt_next)
    void onClickNext(){

        //  이미지 선택 패이지
        if(vp.getCurrentItem() == 0){
            if(imageUri != null)
                vp.setCurrentItem(1, true);
            else
                Toast.makeText(SendFragment.this.getContext(), "상품 이미지를 선택 해 주세요", Toast.LENGTH_SHORT).show();
        } else if(vp.getCurrentItem() == 1) {
            if(startLatLng != null)
                vp.setCurrentItem(2, true);
            else
                Toast.makeText(SendFragment.this.getContext(), "시작 위치를 선택 해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri imageUri = null;
    private LatLng startLatLng = null;

    private SendPagerAdapter sendPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_send, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){
        BaseFragment send1 = new Send1Fragment();
        BaseFragment send2 = new Send2Fragment();
        BaseFragment send3 = new Send3Fragment();

        sendPagerAdapter = new SendPagerAdapter(getChildFragmentManager());

        sendPagerAdapter.addPage(send1);
        sendPagerAdapter.addPage(send2);
        sendPagerAdapter.addPage(send3);

        vp.setAdapter(sendPagerAdapter);
        vp.setSwipeLocked(true);

        binderManager.bind("ok_send1", new BinderManager.BinderInterface<Uri>() {
            @Override
            public void update(Uri data) {
                imageUri = data;
            }
        });

        binderManager.bind("ok_send2", new BinderManager.BinderInterface<LatLng>() {
            @Override
            public void update(LatLng data) {
                startLatLng = data;
            }
        });
    }
}
