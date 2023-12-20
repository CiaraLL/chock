package com.example.myapplication.network;

import com.example.myapplication.ItemData;

import java.util.List;

/**
 * @Author lili
 * @Date 2023/12/13-16:09
 */
public class TaskResponse {
    public int code;

    public List<ItemData> itemDataList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ItemData> getItemDataList() {
        return itemDataList;
    }

    public void setItemDataList(List<ItemData> itemDataList) {
        this.itemDataList = itemDataList;
    }
}
