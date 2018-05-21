package com.sigran0.sendreceive.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.DatabaseManager;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.managers.UserManager;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import lombok.ToString;

public class SigninActivity extends BaseActivity {

    private static final String TAG = "fucking";

    Context context;

    @BindViews({R.id.a_signin_mtf_email, R.id.a_signin_mtf_phonenumber, R.id.a_signin_mtf_username, R.id.a_signin_mtf_date})
    MaterialTextField[] materialTextFields;

    @BindView(R.id.a_signin_rg)
    RadioGroup radioGroup;

    @OnClick(R.id.a_signin_bt_signin)
    void onClickSignin(){
        String username = materialTextFields[2].getEditText().getText().toString();
        String email = materialTextFields[0].getEditText().getText().toString();
        String phonenumber = materialTextFields[1].getEditText().getText().toString();
        String birthDate = materialTextFields[3].getEditText().getText().toString();
        String uid = userManager.getUID();

        int checkedId = radioGroup.getCheckedRadioButtonId();

        int check = -1;

        if(checkedId == R.id.a_signin_rb_0)
            check = 0;
        else
            check = 1;

        Log.d(TAG, "onClickSignin: " + checkedId);

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

        if(birthDate.length() < 1){
            Toast.makeText(context, "핸드폰 번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkedId == -1) {
            Toast.makeText(context, "가입유형을 선택해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        ModelManager.UserData userData = new ModelManager.UserData();
        userData.setEmail(email);
        userData.setPhoneNumber(phonenumber);
        userData.setUsername(username);
        userData.setBirthDate(birthDate);
        userData.setProfileImageUrl("hello");
        userData.setUid(uid);
        userData.setType(check);

        dbManager.saveUserData(userData);
        Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show();
        SigninActivity.this.finish();
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        startActivity(intent);
    }

    UserManager userManager;
    DatabaseManager dbManager;

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

        for(int c = 0; c < 3; c++) {

            final int index = c;
            materialTextFields[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialTextFields[index].toggle();
                    materialTextFields[index].getEditText().setText("");
                }
            });
        }

        materialTextFields[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(SigninActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                String dateString = String.format("%d-%d-%d", year, monthOfYear, dayOfMonth);
                                materialTextFields[3].getEditText().setText(dateString);
                            }
                        }, 2018, 5, 20);

                dialog.show();

            }
        });


        materialTextFields[3].getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(SigninActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                String dateString = String.format("%d-%d-%d", year, monthOfYear, dayOfMonth);
                                materialTextFields[3].getEditText().setText(dateString);
                            }
                        }, 2018, 5, 20);

                dialog.show();

            }
        });

        for(MaterialTextField mtf : materialTextFields){
            mtf.expand();
        }

        if(username != null){
            materialTextFields[2].getEditText().setText(username);
        }

        if(email != null){
            materialTextFields[0].getEditText().setText(email);
        }
    }
}

