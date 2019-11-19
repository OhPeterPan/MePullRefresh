package com.wak.refresh.head;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wak.refresh.R;

public class RingView extends View {


    private Bitmap arrowBit;
    private int width;
    private int height;
    private int arcColor = Color.GRAY;//环形的颜色
    private int strokeWidth = 5;
    private int radius;
    private int centerX;
    private int centerY;
    private Paint paint;
    private boolean isShow = true;
    private int progress = 0;
    private int angleMAX = 50;//圆环最大转的系数

    public RingView(Context context) {
        this(context, null);

    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        arrowBit = BitmapFactory.decodeResource(getResources(), R.drawable.jiantou);
/*        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.jiantou, options);*/
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(arcColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) width = dp2px(30);
        else width = widthSize;
        if (heightMode == MeasureSpec.AT_MOST) height = dp2px(30);
        else height = heightSize;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //确定View的宽高
        width = w;
        height = h;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = (getMeasuredWidth() - strokeWidth) / 2;
        //获取中心点的位置
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isShow)
            canvas.drawBitmap(arrowBit, centerX - arrowBit.getWidth() / 2, centerY - arrowBit.getHeight() / 2, paint);

        drawArc(canvas);

    }

    private void drawArc(Canvas canvas) {
        RectF rect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rect, -90f, (-360 * progress) / angleMAX, false, paint);
    }

    public void setProgress(int progress) {

        if (progress <= 0) progress = 0;
        if (progress >= angleMAX) progress = angleMAX;
        this.progress = progress;
        postInvalidate();
    }

    private int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public void showArrowImage(boolean isShow) {
        this.isShow = isShow;
    }
}
