package com.sigran0.sendreceive.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.sigran0.sendreceive.R;

public class SimpleProgressDialog  extends Dialog {
    private Context context;
    ProgressWheel mProgress;

    public SimpleProgressDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inf.inflate(R.layout.dialog_simple_progress, null);

        mProgress = (ProgressWheel) layout.findViewById(R.id.dialog_progresswheel);
        setContentView(layout);
        setCancelable(false);
    }

    @Override
    public void show() {
        try {
            super.show();
            if(mProgress.isSpinning()==false){
                mProgress.spin();
            }
        } catch(Exception e){}
    }

    @Override
    public void hide() {
        try{
            if(mProgress.isSpinning()==true){
                mProgress.stopSpinning();
            }
            super.hide();
        }catch(Exception e){}
    }

    @Override
    public void dismiss() {
        try{
            if(mProgress.isSpinning()==true){
                mProgress.stopSpinning();
            }
            super.dismiss();
        }catch(Exception e){}
    }
}