package com.sigran0.sendreceive.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.dialogs.SimpleProgressDialog;
import com.sigran0.sendreceive.fragments.BaseFragment;
import com.sigran0.sendreceive.managers.BinderManager;
import com.sigran0.sendreceive.managers.UserManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity{

    public static String FRAGMENT_INTENT_TAG = "fdsafsao1431fdm10y43204fdsafklasdflkf12319098510";

    private SimpleProgressDialog mProgress;
    private int mStartActivityAnimationEnterResId = -1, mStartActivityAnimationExitResId = -1;
    private boolean mStartActivityAnimation = false;
    private int mFinishActivityAnimationEnterResId = -1, mFinishActivityAnimationExitResId = -1;
    private boolean mFinishActivityAnimation = false;

    private BinderManager binderManager = BinderManager.getInstance();

    private final int GALLERY_CODE = 1112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        if(savedInstanceState != null){
            finish();
            return;
        }

        ButterKnife.bind(this);
//        setStartActivityAnimation(R.anim.slide_right_in, R.anim.slide_right_out);
//        setFinishActivityAnimation(R.anim.slide_left_in, R.anim.slide_left_out);

        initializeLayout();

        if (getLayoutResourceId() == R.layout.activity_sub) {
            BaseFragment lFragment;
            if (getIntent() != null) {
                lFragment = getIntent().getParcelableExtra(FRAGMENT_INTENT_TAG);
                if (lFragment != null) {
                    //setBackPressForFragment(lFragment);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.a_sub_fl_root, lFragment);
                    transaction.commit();
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        BinderManager.getInstance().removeAll();
        if (mFinishActivityAnimation) {
            overridePendingTransition(mFinishActivityAnimationEnterResId, mFinishActivityAnimationExitResId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgress();
    }

    protected abstract int getLayoutResourceId();
    protected abstract void initializeLayout();

    public void startActivityWithFragment(Class<? extends BaseActivity> clazz, BaseFragment fragment){
        Intent intent = new Intent(getApplicationContext(), clazz).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BaseActivity.FRAGMENT_INTENT_TAG, fragment);
        startActivity(intent);
    }

    public void setStartActivityAnimation(int pEnterId, int pExitId) {
        mStartActivityAnimationEnterResId = pEnterId;
        mStartActivityAnimationExitResId = pExitId;
        mStartActivityAnimation = true;
    }

    public void setFinishActivityAnimation(int pEnterId, int pExitId) {
        mFinishActivityAnimationEnterResId = pEnterId;
        mFinishActivityAnimationExitResId = pExitId;
        mFinishActivityAnimation = true;
    }

    public void setFinishActivityAnimation(boolean pB) {
        mFinishActivityAnimation = pB;
    }

    public void startProgress(Context context) {
        if (mProgress == null) {
            mProgress = new SimpleProgressDialog(context);
        }

        if (mProgress.isShowing() == false) {
            mProgress.show();
        }
    }

    public void stopProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
            mProgress = null;
        }
    }

    public void hideKeyboard(){
        try {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Throwable e) {
//            SMMLog.e(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case GALLERY_CODE:
                    Uri imageUri = data.getData();
                    binderManager.startUpdate("send_image", imageUri);
                    break;
                default: {
                    UserManager userManager = UserManager.getInstance();

                    if(userManager.getSigninState() == UserManager.SigninState.Waiting){

                        if(userManager.getSigninType() == UserManager.SigninType.Twitter) {

                        } else {
                            userManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
                        }
                    }

                    break;
                }
            }
        }
    }

    protected void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
