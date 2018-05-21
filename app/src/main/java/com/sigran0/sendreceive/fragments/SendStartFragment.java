package com.sigran0.sendreceive.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.activities.BaseActivity;
import com.sigran0.sendreceive.activities.MainActivity;
import com.sigran0.sendreceive.activities.SubActivity;

import butterknife.OnClick;

public class SendStartFragment extends BaseFragment {

    @OnClick(R.id.f_send_start_sdv)
    void onClickButton(){
        //Intent intent = new Intent(getBaseActivity(), BaseActivity.class);
        SendFragment fragment = new SendFragment();
        getBaseActivity().startActivityWithFragment(SubActivity.class, fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_send_start, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

    }
}
