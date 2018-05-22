package com.sigran0.sendreceive.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    protected View rootView;

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        rootView = itemView;

        initializeLayout();
    }

    protected abstract void initializeLayout();
    public abstract void setData(T data);
    public View getRoot(){
        return rootView;
    }
}
