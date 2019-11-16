package com.wak.refresh.view;

import android.content.Context;
import android.util.AttributeSet;

import com.wak.refresh.refresh.BaseRefreshLayout;
import com.wak.refresh.refresh.OnRefreshListener;

public class TestRefresh extends BaseRefreshLayout implements OnRefreshListener {
    public TestRefresh(Context context) {
        super(context);
        init(context);
    }

    public TestRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnRefreshListener(this);
    }


    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isSuccess) {

    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {

    }
}
