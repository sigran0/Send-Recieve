package com.sigran0.sendreceive.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sigran0.sendreceive.R;
import com.sigran0.sendreceive.activities.ItemInfoActivity;
import com.sigran0.sendreceive.managers.ModelManager;
import com.sigran0.sendreceive.recycler.holder.BaseHolder;
import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import java.util.List;

import butterknife.OnClick;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListHolder> {

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
    public ItemListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_item_list, parent, false);
        ItemListHolder holder = new ItemListHolder(view, type);

        return holder;
    }

    @Override
    @NonNull
    public void onBindViewHolder(final ItemListHolder holder, final int pos){
        holder.setData(data.getItemDataList().get(pos));
        holder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemInfoActivity.class);
                intent.putExtra("data", data.getItemDataList().get(pos));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data != null) {
            return data.getSize();
        }
        return 0;
    }
}
