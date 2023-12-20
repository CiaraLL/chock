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
    }
}
