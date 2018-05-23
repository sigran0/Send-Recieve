package com.sigran0.sendreceive.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.recycler.holder.BaseHolder;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<BaseHolder>{

    private Context context;
    private ItemListHolder.TYPE type;
    private ModelManager.ItemDataList data;

    public ItemListAdapter(Context context, ItemListHolder.TYPE type) {
        this.context = context;
        this.type = type;
    }

    public void setData(ModelManager.ItemDataList data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_item_list, parent, false);
        ItemListHolder holder = new ItemListHolder(view, type);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseHolder holder, final int pos){
        holder.setData(data.getItemDataList().get(pos));
    }

    @Override
    public int getItemCount() {
        if(data != null)
            return data.getSize();
        return 0;
    }
}
