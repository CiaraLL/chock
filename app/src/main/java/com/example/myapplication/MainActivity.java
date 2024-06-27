package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.mvvm.MyModel;
import com.example.myapplication.mvvm.MyViewModel;
import com.example.myapplication.recycler.ItemClickListener;
import com.example.myapplication.recycler.MyAdapter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author lili
 * @Date 2023/12/13-15:09
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private MyViewModel mMyViewModel = new MyViewModel();
    private MyModel mMymodel = new MyModel();


    private ItemClickListener mItemClickListener = new ItemClickListener() {
        @Override
        public void clickItem(int position) {
            ////
        }

        @Override
        public void clickItemPhoto(int position) {
            ///
        }

        @Override
        public void clickItemText(int position) {
            ///
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMymodel.queryData();
        mAdapter = new MyAdapter(mMyViewModel.mItemDataList.getValue());
        mAdapter.setItemClickListener(mItemClickListener);
        mRecyclerView.setAdapter(mAdapter);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Request request =   new Request.Builder()
                .url("xxxx")
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}

