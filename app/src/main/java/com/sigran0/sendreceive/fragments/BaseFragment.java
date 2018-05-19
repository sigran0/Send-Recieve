package com.sigran0.sendreceive.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.sigran0.sendreceive.activities.BaseActivity;
import com.sigran0.sendreceive.dialogs.SimpleProgressDialog;
import com.sigran0.sendreceive.tools.RecycleUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements Parcelable{

    private static final String TAG = "BaseFragment";

    protected View rootView;
    private SimpleProgressDialog mProgress;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        unbinder = ButterKnife.bind(this, rootView);
        initializeLayout();
        return rootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        hideKeyboard();
        stopProgress();

        if(rootView != null)
            RecycleUtils.recursiveRecycle(rootView.getRootView());
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();

        if(unbinder != null)
            unbinder.unbind();
    }

    protected abstract void initializeLayout();

    protected void setRoot(int pId, LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(pId, container, false);
    }

    public BaseActivity getBaseActivity(){
        Activity activity = getActivity();

        if(activity instanceof BaseActivity)
            return (BaseActivity) activity;
        return null;
    }

    public void startProgress() {
        if (mProgress == null) {
            mProgress = new SimpleProgressDialog(getActivity());
        }
        if (mProgress.isShowing() == false) {
            mProgress.show();
        }
    }

    public void stopProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Throwable e) {
//            SMMLog.e(e);
        }
    }

    public void finishNoAnimation(){

        BaseActivity activity = getBaseActivity();

        if(activity != null)
            getBaseActivity().setFinishActivityAnimation(false);

        activity.finish();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(getClass());
        for(Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(
                        Modifier.isFinal(field.getModifiers()) == false &&
                                Modifier.isStatic(field.getModifiers()) == false &&
                                Modifier.isAbstract(field.getModifiers()) == false &&
                                Modifier.isInterface(field.getModifiers()) == false &&
                                Modifier.isStrict(field.getModifiers()) == false &&
                                Modifier.isSynchronized(field.getModifiers()) == false &&
                                Modifier.isTransient(field.getModifiers()) == false &&
                                Modifier.isVolatile(field.getModifiers()) == false &&
//                                Modifier.isPublic(field.getModifiers()) == true &&
                                field.getName().startsWith("a") == true ) {
                    parcel.writeValue(field.get(this));
                }
            } catch (Exception e) {

            }
        }
    }

    public static final Creator<BaseFragment> CREATOR = new Creator<BaseFragment>() {
        @SuppressWarnings("unchecked")
        @Override
        public BaseFragment createFromParcel(Parcel in) {
//            ClassLoader loader = JMProject_AndroidApp.getApplication().getAppContext().getClassLoader();
            Class<? extends BaseFragment> lFragmentClass = (Class<? extends BaseFragment>) in.readValue(null);
            BaseFragment lFragment = null;
            try {
                Constructor<? extends BaseFragment> lConstructor = lFragmentClass.getConstructor();
                //lContent = lContentClass.newInstance();
                lConstructor.setAccessible(true);
                lFragment = lConstructor.newInstance();
                for(Field field : lFragment.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if(
                            Modifier.isFinal(field.getModifiers()) == false &&
                                    Modifier.isStatic(field.getModifiers()) == false &&
                                    Modifier.isAbstract(field.getModifiers()) == false &&
                                    Modifier.isInterface(field.getModifiers()) == false &&
                                    Modifier.isStrict(field.getModifiers()) == false &&
                                    Modifier.isSynchronized(field.getModifiers()) == false &&
                                    Modifier.isTransient(field.getModifiers()) == false &&
                                    Modifier.isVolatile(field.getModifiers()) == false &&
//                                    Modifier.isPublic(field.getModifiers()) == true &&
                                    field.getName().startsWith("a") == true ) {
                        field.set(lFragment, in.readValue(null));
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return lFragment;
        }

        @Override
        public BaseFragment[] newArray(int i) {
            return new BaseFragment[i];
        }
    };
}
