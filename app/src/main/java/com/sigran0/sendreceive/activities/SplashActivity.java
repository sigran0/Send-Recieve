package com.sigran0.sendreceive.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.interfaces.DataListner;
import com.sigran0.sendreceive.interfaces.SigninCallback;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.UserManager;

import java.util.ArrayList;

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
            onStop();
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

        mContext = this;
        mThis = this;

        startProgress(this);

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        userManager = UserManager.getInstance();
                        databaseManager = DatabaseManager.getInstance();

                        mBtLoginFacebook.setVisibility(View.INVISIBLE);

                        if(userManager.isSignin()) {
                            loadUserdata();
                        } else {
                            Log.d(TAG, "onCreate: 로그인 실패");
                            mBtLoginFacebook.setVisibility(View.VISIBLE);
                            stopProgress();
                        }
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(SplashActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setDeniedMessage("권한이 필요해요 ㅠ_ㅠ [Setting] > [Permission] > Send-Recieve 에 가서 권한을 주세요")
                .setPermissions(Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeLayout(){

    }

    private void loadUserdata(){
        databaseManager.getUserData(new DataListner.DataReceiveListener<ModelManager.UserData>() {
            @Override
            public void success(ModelManager.UserData data) {
                if(data == null) {
                    Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                    startActivity(intent);
                } else {
                    if(data.getType() == 0) {
                        Intent intent = new Intent(SplashActivity.this, ReceiverMainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, SenderMainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
                stopProgress();
            }

            @Override
            public void fail(String message) {
                Log.d(TAG, "fail: " + message);
                stopProgress();
            }
        });
    }
}
