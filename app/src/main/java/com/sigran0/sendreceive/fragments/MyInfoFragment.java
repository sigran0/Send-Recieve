package com.sigran0.sendreceive.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.activities.ListActivity;
import com.sigran0.sendreceive.activities.SubActivity;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.StaticDataManager;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-05-19.
 */

public class MyInfoFragment extends BaseFragment {

    public static final String TAG = "MyInfoFragment";

    @BindView(R.id.f_my_info_tv_username)
    TextView tvUsername;

    @BindView(R.id.f_my_info_tv_type)
    TextView tvType;

    @BindView(R.id.f_my_info_sdv_profile)
    SimpleDraweeView sdvProfile;

    @BindView(R.id.f_my_info_bt_list)
    Button btList;

    @BindView(R.id.f_my_info_tv_money)
    TextView tvMoney;

    StaticDataManager sdm = StaticDataManager.getInstance();

    private void setTvMoney(int money){
        tvMoney.setText(String.format("%d 캐시 보유중 입니다.", money));
    }

    @OnClick(R.id.f_my_info_bt_list)
    void OnClickList(){

        Intent intent = new Intent(MyInfoFragment.this.getActivity(), ListActivity.class);

        if(sdm.getUserData().getType() == 0)
            intent.putExtra("type", ItemListHolder.TYPE.CLIENT);
        else if(sdm.getUserData().getType() == 1)
            intent.putExtra("type", ItemListHolder.TYPE.DELIVERER);

        startActivity(intent);
    }

    @OnClick(R.id.f_my_info_bt_logout)
    void OnClickLogout() {
        userManager.signOut();
        finishNoAnimation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_my_info, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){
        startProgress();
        Log.d(TAG, "initializeLayout: fucking start init");

        if(sdm.isInitialized()){
            ModelManager.UserData data = sdm.getUserData();

            final String username = data.getUsername();
            final String usertype;

            if(data.getType() == 0) {
                usertype = "일반회원";
                btList.setText("보낸 물건 내역 조회하기");
            } else {
                btList.setText("배송 업무 내역 조회하기");
                usertype = "퀵배송 요원";
            }

            setTvMoney(data.getMoney());

            String imageId = data.getImageUrl();

            Log.d(TAG, "success: fucking download image");

            databaseManager.downloadImage(imageId, new DataListner.DataReceiveListener<Uri>() {
                @Override
                public void success(Uri data) {
                    sdvProfile.setImageURI(data);
                    tvUsername.setText(username);
                    tvType.setText(usertype);
                    Log.d(TAG, "success: fucking complete");
                    stopProgress();
                }

                @Override
                public void fail(String message) {
                    stopProgress();
                    showToast("원인불명의 오류로 불러오기에 실패했습니다 ㅜ_ㅜ.\n다시 시도해 주세요.");
                    Log.e(TAG, "fail: fucking" + message);
                }
            });

        } else {
            sdm.initialize(new DataListner.DataInitializeListener() {
                @Override
                public void success() {
                    ModelManager.UserData data = sdm.getUserData();

                    final String username = data.getUsername();
                    final String usertype;

                    if(data.getType() == 0) {
                        usertype = "일반회원";
                        btList.setText("보낸 물건 내역 조회하기");
                    } else {
                        btList.setText("배송 업무 내역 조회하기");
                        usertype = "퀵배송 요원";
                    }

                    setTvMoney(data.getMoney());

                    String imageId = data.getImageUrl();

                    Log.d(TAG, "success: fucking download image");

                    databaseManager.downloadImage(imageId, new DataListner.DataReceiveListener<Uri>() {
                        @Override
                        public void success(Uri data) {
                            sdvProfile.setImageURI(data);
                            tvUsername.setText(username);
                            tvType.setText(usertype);
                            Log.d(TAG, "success: fucking complete");
                            stopProgress();
                        }

                        @Override
                        public void fail(String message) {
                            stopProgress();
                            showToast("원인불명의 오류로 불러오기에 실패했습니다 ㅜ_ㅜ.\n다시 시도해 주세요.");
                            Log.e(TAG, "fail: fucking" + message);
                        }
                    });
                }

                @Override
                public void fail(String message) {
                    stopProgress();
                    showToast("원인불명의 오류로 불러오기에 실패했습니다 ㅜ_ㅜ.\n다시 시도해 주세요.");
                    Log.e(TAG, "fail: fucking" + message);
                }
            });
        }
    }
}
