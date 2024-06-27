package com.example.myapplication.mvvm;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.ItemData;
import com.example.myapplication.network.IQueryDataInterface;
import com.example.myapplication.network.TaskResponse;
import com.example.myapplication.util.SchedulerManager;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author lili
 * @Date 2023/12/14-16:27
 */
public class MyModel {

    private Disposable mDisposable;

    MutableLiveData<List<ItemData>> _mItemDataList = new MutableLiveData<>();

    LiveData<List<ItemData>> mItemDataList = _mItemDataList;

    @SuppressLint("CheckResult")
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

        Observable.just("hello", "Rxjava", "!");

        Integer i = 0;
        Integer finalI = i;
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(finalI);
            }
        });

        i = 1;

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d("TAG", "onNext: i:" + integer);
                //此时打印出来的integer = 1;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                //接受的类型是Integer，返回一个String类型
                String s = integer.toString();
                return s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.concat(Observable.just(1, 2), Observable.just(3, 4), Observable.just(5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //最终打印出来123456
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.just(7, 8, 9)
                .startWith(Observable.just(4, 5, 6))
                .startWithArray(1, 2, 3, 4)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //最终打印出来123456789
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.just(1, 2, 3, 4)
                .count()
                //被观察者每发送一个事件就调用一次
                .doOnEach(new Consumer<Notification<TaskResponse>>() {
                    @Override
                    public void accept(Notification<TaskResponse> taskResponseNotification) throws Exception {

                    }
                })
                //执行next事件前调用
                .doOnNext(new Consumer<TaskResponse>() {
                    @Override
                    public void accept(TaskResponse taskResponse) throws Exception {

                    }
                })
                //执行next事件后调用
                .doAfterNext(new Consumer<TaskResponse>() {
                    @Override
                    public void accept(TaskResponse taskResponse) throws Exception {

                    }
                })
                //被观察者正常发送完后事件调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                //被观察者发送error事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
                //订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                //不管是正常发送事件还是因为异常终止发送事件，被观察者发送完事件之后调用
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                //最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .onErrorReturn(new Function<Throwable, TaskResponse>() {
                    @Override
                    public TaskResponse apply(Throwable throwable) throws Exception {
                        Log.d("tag", "错误事件: " + throwable.toString());
                        return new TaskResponse();//返回一个事件
                    }
                })
                //不设置发送无数次，设置后当执行完OnComplete后重新发送事件
                //默认在新的线程上工作
                .repeat(2)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //被观察者的数量是aLong
                    }
                });

        Observable.just(2, 4, 6, 8)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 5;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //结果是2，4
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.just(2, 4, 6, 8)
                .take(1)//指定观察者接受事件的数量为1
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //结果是2
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        Observable.just(2, 4, 6, 8)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //结果为2
                    }
                });

        Observable.just(2, 4, 6, 8)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return (integer < 5);
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        //不满足条件返回结果是false
                    }
                });

        Observable.interval(1, TimeUnit.SECONDS)
                .takeUntil(Observable.timer(4,TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        //收到的结果是0,1,2,3,
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}




