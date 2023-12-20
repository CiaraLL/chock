package com.example.myapplication.recycler;

/**
 * @Author lili
 * @Date 2023/12/13-15:42
 */
public interface ItemClickListener {
    void clickItem(int position);

    void clickItemPhoto(int position);

    void clickItemText(int position);
}
