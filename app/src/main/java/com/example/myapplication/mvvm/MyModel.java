package com.example.myapplication.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.ItemData;
import com.example.myapplication.network.IQueryDataInterface;
import com.example.myapplication.network.TaskResponse;
import com.example.myapplication.util.SchedulerManager;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author lili
 * @Date 2023/12/14-16:27
 */
public class MyModel {

    private Disposable mDisposable;

    MutableLiveData<List<ItemData>> _mItemDataList = new MutableLiveData<>();

    LiveData<List<ItemData>> mItemDataList = _mItemDataList;

    public void queryData() {
        mDisposable = IQueryDataInterface.sIQueryDataInterface
                .getItemDates()
                .observeOn(SchedulerManager.MAIN)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<TaskResponse>() {
                    @Override
                    public void accept(TaskResponse taskResponse) throws Exception {
                        _mItemDataList.setValue(taskResponse.getItemDataList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
