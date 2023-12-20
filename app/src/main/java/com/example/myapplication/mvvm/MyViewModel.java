package com.example.myapplication.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ItemData;

import java.util.List;

/**
 * @Author lili
 * @Date 2023/12/14-16:27
 */
public class MyViewModel  {


    private MutableLiveData<List<ItemData>> _mItemDataList = new MutableLiveData<>();

    public LiveData<List<ItemData>> mItemDataList = _mItemDataList;
    private MyModel mMyModel;

    public MyViewModel() {
        mMyModel = new MyModel();
        mMyModel.mItemDataList.observeForever(new Observer<List<ItemData>>() {
            @Override
            public void onChanged(List<ItemData> itemData) {
                _mItemDataList.setValue(itemData);
            }
        });
    }
}
