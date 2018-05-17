package com.sigran0.sendreceive.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.SigninCallback;
import com.sigran0.sendreceive.managers.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    public static final String TAG = "fucking";
    UserManager userManager;

    @BindView(R.id.a_main_cl_root)
    View mRootView;

    @BindView(R.id.a_main_bt_login_facebook)
    Button mBtLoginFacebook;

    @OnClick(R.id.a_main_bt_login_facebook)
    void onClick(){
        Toast.makeText(mContext, "fucking", Toast.LENGTH_SHORT).show();
        userManager.signinWithSNS(UserManager.SigninType.Facebook, mThis, new SigninCallback() {
            @Override
            public void success() {
                Toast.makeText(mContext, "로그인 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failed() {
                Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Context mContext;
    BaseActivity mThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userManager = UserManager.getInstance();
        mContext = this;
        mThis = this;

        startProgress(this);

        if(userManager.isSignin()) {
            //  로그인이 되있을 경우
            //  메인화면으로 보내기
        } else {
            //  로그인이 안되있을 경우
            //  로그인 화면으로 보내기
            Snackbar.make(mRootView, "로그인이 필요합니다.", Snackbar.LENGTH_SHORT)
                    .setAction("close", null)
                    .show();
            stopProgress();
        }
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_main;
    }

    @Override
    protected void initializeLayout(){

    }
}
