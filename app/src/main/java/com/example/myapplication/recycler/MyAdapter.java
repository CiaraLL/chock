package com.example.myapplication.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ItemData;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ViewItemBinding;

import java.util.List;

/**
 * @Author lili
 * @Date 2023/12/13-15:17
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<ItemData> mItemDataList;
    private ItemClickListener mItemClickListener;

    public MyAdapter(List<ItemData> itemDataList) {
        mItemDataList = itemDataList;
    }

    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewItemBinding viewItemBinding = ViewItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(viewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemData itemData = mItemDataList.get(position);
        holder.bind(itemData,position);
        holder.setItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }
}
