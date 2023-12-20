package com.example.myapplication;

import android.graphics.drawable.Drawable;

/**
 * @Author lili
 * @Date 2023/12/13-15:16
 */
public class ItemData {

    private static final String TAG = "ItemData";

    public String mText;
    public Drawable mPhoto;

    public ItemData(String mText, Drawable mPhoto) {
        this.mText = mText;
        this.mPhoto = mPhoto;
    }
}
