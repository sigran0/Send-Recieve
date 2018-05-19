package com.sigran0.sendreceive.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.SigninCallback;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    public static final String TAG = "fucking";
    UserManager userManager;
    DatabaseManager databaseManager;

    @BindView(R.id.a_main_cl_root)
    View mRootView;

    @BindView(R.id.a_main_bt_login_facebook)
    Button mBtLoginFacebook;

    @OnClick(R.id.a_main_bt_login_facebook)
    void onClick(){
        startProgress(mContext);
        if(userManager.isSignin()){
            userManager.signOut();
            stopProgress();
        } else {
            userManager.signinWithSNS(UserManager.SigninType.Facebook, mThis, new SigninCallback() {
                @Override
                public void success() {
//                    mBtLoginFacebook.setVisibility(View.INVISIBLE);
                    loadUserdata();
                }

                @Override
                public void failed() {
                    stopProgress();
                    Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    Context mContext;
    BaseActivity mThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userManager = UserManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        mContext = this;
        mThis = this;

        startProgress(this);

        if(userManager.isSignin()) {
            loadUserdata();
            mBtLoginFacebook.setVisibility(View.VISIBLE);
        } else {
            mBtLoginFacebook.setVisibility(View.VISIBLE);
            stopProgress();
        }
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeLayout(){

    }

    private void loadUserdata(){
        databaseManager.getUserData(new DatabaseManager.DataReceiveListener<ModelManager.UserData>() {
            @Override
            public void onReceive(ModelManager.UserData data) {
                if(data == null) {
                    Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                stopProgress();
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: " + message);
                stopProgress();
            }
        });
    }
}
