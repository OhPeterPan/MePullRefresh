package com.wak.refresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.wak.refresh.adapter.TestAdapter;
import com.wak.refresh.head.RingRefreshLayout;
import com.wak.refresh.refresh.BaseRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RingRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshingListener(new BaseRefreshLayout.OnRefreshingListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.stopRefresh(true);
                    }
                }, 3000);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestAdapter(this));

    }

}
