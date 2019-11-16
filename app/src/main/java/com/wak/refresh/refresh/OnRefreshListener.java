package com.wak.refresh.refresh;

public interface OnRefreshListener {
    void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight);

    void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight);

    void onRefreshReady(int scrollY, int refreshHeight, int headerHeight);

    void onRefreshing(int scrollY, int refreshHeight, int headerHeight);

    /**
     * 刷新完成的状态
     *
     * @param scrollY       刷新头滚动的距离
     * @param refreshHeight 刷新的高度
     * @param headerHeight  刷新头的总高度
     * @param isSuccess     刷新时成功还是失败了
     */
    void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isSuccess);

    //当下拉刷新的距离没有超过刷新头的高度时，此时松开手机就会走取消状态
    void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight);
}
