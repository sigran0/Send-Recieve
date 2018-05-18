package com.sigran0.sendreceive.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.UserManager;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import lombok.ToString;

public class SigninActivity extends BaseActivity {

    private static final String TAG = "fucking";

    Context context;

    @BindViews({R.id.a_signin_mtf_email, R.id.a_signin_mtf_phonenumber, R.id.a_signin_mtf_username})
    MaterialTextField[] materialTextFields;

    @OnClick(R.id.a_signin_bt_signin)
    void onClickSignin(){
        String username = materialTextFields[2].getEditText().getText().toString();
        String email = materialTextFields[0].getEditText().getText().toString();
        String phonenumber = materialTextFields[1].getEditText().getText().toString();
        String uid = userManager.getUID();

        if(username.length() < 1){
            Toast.makeText(context, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.length() < 1){
            Toast.makeText(context, "이메일을 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phonenumber.length() < 1){
            Toast.makeText(context, "핸드폰 번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        ModelManager.UserData userData = new ModelManager.UserData();
        userData.setEmail(email);
        userData.setPhoneNumber(phonenumber);
        userData.setUsername(username);
        userData.setBirthDateTimestamp(999);
        userData.setProfileImageUrl("hello");
        userData.setUid(uid);
        userData.setWorker(false);

        dbManager.saveUserData(userData);
        Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show();
        SigninActivity.this.finish();
    }

    UserManager userManager;
    DatabaseManager dbManager;

    @OnClick({R.id.a_signin_mtf_email, R.id.a_signin_mtf_phonenumber, R.id.a_signin_mtf_username})
    void onClickMTF(View v){
        Log.d(TAG, "onClickMTF: " + v.getId());
        switch(v.getId()) {
            case R.id.a_signin_mtf_email: {
                materialTextFields[0].toggle();
                materialTextFields[0].getEditText().setText("");
                break;
            }
            case R.id.a_signin_mtf_phonenumber: {
                materialTextFields[1].toggle();
                materialTextFields[1].getEditText().setText("");
                break;
            }
            case R.id.a_signin_mtf_username: {
                materialTextFields[2].toggle();
                materialTextFields[2].getEditText().setText("");
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
    }

    @Override
    protected int getLayoutResourceId(){
        return R.layout.activity_signin;
    }

    @Override
    protected void initializeLayout(){
        userManager = UserManager.getInstance();
        dbManager = DatabaseManager.getInstance();

        String username = userManager.getUsername();
        String email = userManager.getUserEmail();

        if(username != null){
            materialTextFields[2].getEditText().setText(username);
            materialTextFields[2].toggle();
        }

        if(email != null){
            materialTextFields[0].getEditText().setText(email);
            materialTextFields[0].toggle();
        }
    }
}

