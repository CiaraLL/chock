package com.example.myapplication.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.ItemData;
import com.example.myapplication.databinding.ViewItemBinding;

/**
 * @Author lili
 * @Date 2023/12/13-15:13
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    private ViewItemBinding mViewItemBinding;

    private ItemClickListener mItemClickListener;
    private int mPosition = -1;

    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public MyViewHolder(@NonNull ViewItemBinding itemBinding) {
        super(itemBinding.getRoot());
        mViewItemBinding = itemBinding;

        if (mPosition != -1){
            mViewItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.clickItem(mPosition);
                }
            });

            mViewItemBinding.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.clickItemPhoto(mPosition);

                }
            });

            mViewItemBinding.itemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.clickItemText(mPosition);

                }
            });
        }

    }


    public void bind(ItemData itemData, int position) {
        mPosition = position;
        Glide.with(mViewItemBinding.getRoot().getContext())
                .load(itemData.mPhoto)
                .into(mViewItemBinding.itemImage);

        mViewItemBinding.itemText.setText(itemData.mText);
    }

}
