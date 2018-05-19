package com.sigran0.sendreceive.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.BinderManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-05-19.
 */

public class Send1Fragment extends BaseFragment{

    private final int GALLERY_CODE = 1112;

    @BindView(R.id.f_send_1_sdv)
    SimpleDraweeView simpleDraweeView;

    Uri imageUri = null;

    @OnClick(R.id.f_send_1_sdv)
    void OnClickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        getBaseActivity().startActivityForResult(intent, GALLERY_CODE);

        binderManager.bind("send_image", new BinderManager.BinderInterface<Uri>() {
            @Override
            public void update(Uri data) {
                simpleDraweeView.setImageURI(data);
                imageUri = data;
                binderManager.startUpdate("ok_send1", imageUri);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_send_1, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){

    }
}
