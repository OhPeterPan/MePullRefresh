package com.wak.refresh.refresh;

public enum Status {
    DEFAULT,//默认事件
    REFRESH_BEFORE,//手指正在向下拉的状态
    REFRESH_AFTER,//放开刷新状态
    REFRESH_READY,//手指放开的时候的位置距离刷新头高度的之间的一段距离的状态
    REFRESH_FRESHING,//正在刷新状态
    REFRESH_COMPLETE,//刷新完成状态
    REFRESH_CANCEL//取消刷新状态 因为没有超过头部的距离
}
