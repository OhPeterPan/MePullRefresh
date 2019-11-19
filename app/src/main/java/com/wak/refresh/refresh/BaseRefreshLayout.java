package com.wak.refresh.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wak.refresh.R;

public class BaseRefreshLayout extends ViewGroup {

    protected View header;
    protected int lastYMove;
    protected int lastInterceptYMove;
    private Status status = Status.DEFAULT;
    private TextView tvfreshInfo;
    private RecyclerView recyclerView;
    private boolean isRefreshSuccess;
    private OnRefreshListener mOnHeaderListener;
    private boolean isRefreshing;
    private OnRefreshingListener refreshingListener;

    public BaseRefreshLayout(Context context) {
        super(context);
    }

    public BaseRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View header = LayoutInflater.from(getContext()).inflate(R.layout.base_head, null);
        tvfreshInfo = header.findViewById(R.id.vHeader);
        setHeader(header);

        recyclerView = findViewById(R.id.recyclerView);
    }

    public void setOnRefreshListener(OnRefreshListener mOnHeaderListener) {
        this.mOnHeaderListener = mOnHeaderListener;

    }

    public void setHeader(View header) {
        this.header = header;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(header, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int contentHeight = 0;
        int childCount = getChildCount();
        View view;
        for (int i = 0; i < childCount; i++) {
            view = getChildAt(i);

            if (view == header) {
                view.layout(0, -view.getMeasuredHeight(), view.getMeasuredWidth(), 0);
            } else {
                view.layout(0, contentHeight, view.getMeasuredWidth(), view.getMeasuredHeight() + contentHeight);
                contentHeight += view.getMeasuredHeight();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int y = (int) ev.getY();

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastYMove = y;
                intercept = false;//down不能拦截，因为拦截了之后子view会收不到所有的事件序列
                break;

            case MotionEvent.ACTION_MOVE:
                if (y > lastInterceptYMove) {//证明是在做下拉操作
                    intercept = getRefreshState();
                } else {
                    intercept = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                //lastInterceptYMove = 0;
                intercept = false;
                break;
        }
        lastInterceptYMove = y;
        return intercept;
    }

    /**
     * 判断是否能拦截事件进行下拉操作了
     * 目前只支持RecyclerView一个...
     *
     * @return
     */
    private boolean getRefreshState() {

        if (recyclerView != null) {
            // RecyclerView view = (RecyclerView) header;
            System.out.println(recyclerView.computeVerticalScrollOffset());
            if (recyclerView.computeVerticalScrollOffset() <= 0) {//此时证明view已经滑动到顶部了,此时应该拦截事件
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                int disY = y - lastYMove;
                if (getScrollY() < 0) {
                    if (header != null && !isRefreshing) {
                        goToRefresh(disY);
                    }
                    //  goToRefresh(disY);
                } else {
                    if (disY >= 0) {
                        goToRefresh(disY);
                    }
                }

                lastYMove = y;
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("up" + status);
                switch (status) {
                    //下拉刷新
                    case REFRESH_BEFORE:
                        scrollToDefaultStatus(Status.REFRESH_CANCEL);
                        break;
                    //松手刷新
                    case REFRESH_AFTER:
                        scrollToRefreshStatus();
                        break;
                }
                break;
        }
        lastInterceptYMove = 0;
        return true;
    }

    private void scrollToDefaultStatus(final Status status) {
        int start = getScrollY();
        int end = 0;
        anim(start, end, new AnimListener() {
            @Override
            public void onGoing() {
                updateStatus(status);
            }

            @Override
            public void onEnd() {
                updateStatus(Status.REFRESH_BEFORE);
            }
        });
    }

    private void scrollToRefreshStatus() {
        isRefreshing = true;
        int start = getScrollY();
        int end = -header.getMeasuredHeight();
        anim(start, end, new AnimListener() {
            @Override
            public void onGoing() {
                updateStatus(Status.REFRESH_READY);
            }

            @Override
            public void onEnd() {
                updateStatus(Status.REFRESH_FRESHING);
            }
        });
    }

    private void anim(int start, int end, final AnimListener animListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(300).start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(0, value);
                animListener.onGoing();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animListener.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void stopRefresh(boolean isSuccess) {
        this.isRefreshSuccess = isSuccess;
        isRefreshing = false;
        scrollToDefaultStatus(Status.REFRESH_COMPLETE);
    }

    interface AnimListener {
        void onGoing();

        void onEnd();
    }

    public void setOnRefreshingListener(OnRefreshingListener listener) {
        this.refreshingListener = listener;
    }

    public interface OnRefreshingListener {
        void onRefresh();
    }

    private void goToRefresh(int disY) {
        scrollBy(0, (int) (-disY * 0.5));
        if (Math.abs(getScrollY()) >= header.getMeasuredHeight()) {
            updateStatus(status.REFRESH_AFTER);
        } else {
            updateStatus(status.REFRESH_BEFORE);
        }
    }

    private void updateStatus(Status status) {
        this.status = status;
        int scrollY = getScrollY();
        switch (status) {
            //默认状态
            case DEFAULT:
                onDefault();
                break;
            //下拉刷新
            case REFRESH_BEFORE:
                mOnHeaderListener.onRefreshBefore(scrollY, header.getMeasuredHeight(), header.getMeasuredHeight());
                tvfreshInfo.setText("下拉刷新");
                break;
            //松手刷新
            case REFRESH_AFTER:
                mOnHeaderListener.onRefreshAfter(scrollY, header.getMeasuredHeight(), header.getMeasuredHeight());
                tvfreshInfo.setText("松手刷新");
                break;
            //准备刷新
            case REFRESH_READY:
                mOnHeaderListener.onRefreshReady(scrollY, header.getMeasuredHeight(), header.getMeasuredHeight());
                break;
            //刷新中
            case REFRESH_FRESHING:
                mOnHeaderListener.onRefreshing(scrollY, header.getMeasuredHeight(), header.getMeasuredHeight());
                tvfreshInfo.setText("刷新.....");
                refreshingListener.onRefresh();
                break;
            //刷新完成
            case REFRESH_COMPLETE:
                mOnHeaderListener.onRefreshComplete(scrollY, header.getMeasuredHeight(), header.getMeasuredHeight(), isRefreshSuccess);
                tvfreshInfo.setText("刷新完成");
                break;
            //取消刷新
            case REFRESH_CANCEL:
                mOnHeaderListener.onRefreshCancel(scrollY, header.getMeasuredHeight(), header.getMeasuredHeight());
                tvfreshInfo.setText("取消刷新");
                break;
        }
    }

    private void onDefault() {
        isRefreshSuccess = false;
    }

    @Override
    public void scrollBy(int x, int y) {
        if ((getScrollY() + y) >= 0) {
            y = -getScrollY();
        }

        super.scrollBy(x, y);
    }
}
