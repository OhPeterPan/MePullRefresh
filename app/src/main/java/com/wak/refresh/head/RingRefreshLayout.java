package com.wak.refresh.head;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.wak.refresh.R;
import com.wak.refresh.refresh.BaseRefreshLayout;
import com.wak.refresh.refresh.OnRefreshListener;

public class RingRefreshLayout extends BaseRefreshLayout implements OnRefreshListener {

    private RingView ringView;

    public RingRefreshLayout(Context context) {
        super(context);
    }

    public RingRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RingRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ringView = header.findViewById(R.id.ringView);
        setOnRefreshListener(this);
    }

    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        // System.out.println("before:" + scrollY);
        ringView.showArrowImage(true);
        ringView.setProgress((int) Math.floor(Math.abs(scrollY) / (1.0f * headerHeight) * 50));
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
        ringView.showArrowImage(false);
        ringView.setProgress((int) Math.floor(Math.abs(scrollY) / (1.0f * headerHeight) * 50));
    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isSuccess) {
        ringView.showArrowImage(true);
        ringView.setProgress((int) Math.floor(Math.abs(scrollY) / (1.0f * headerHeight) * 50));

    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        ringView.showArrowImage(true);
        ringView.setProgress((int) Math.floor(Math.abs(scrollY) / (1.0f * headerHeight) * 50));

    }
}
