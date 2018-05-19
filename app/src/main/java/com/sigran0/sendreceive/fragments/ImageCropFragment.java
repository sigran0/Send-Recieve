package com.sigran0.sendreceive.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.sigran0.sendreceive.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-05-20.
 */

public class ImageCropFragment extends BaseFragment {

    @BindView(R.id.f_image_crop_civ)
    CropImageView cropImageView;

    @OnClick(R.id.f_image_crop_bt_save)
    void OnClickSave(){

    }

    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_my_info, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

    }

    public void setCropImageView(Uri imageUri) {

    }
}
