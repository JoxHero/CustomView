package com.zyp.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zyp on 2016/10/20.
 */

/**
 * 十字线
 */
public class ReticleView extends View {
    private Paint mPaint;
    private int centerX,centerY;
    private PointF startX,endX,startY,endY,control;
    public ReticleView(Context context) {
        super(context);
        init();
    }

    public ReticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        startX = new PointF(0,0);
        endX = new PointF(0,0);
        startY = new PointF(0,0);
        endY = new PointF(0,0);

        control = new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        startX.x = 0+20;
        startX.y = centerY;
        startY.x = centerX;
        startY.y = 0+20;

        endX.x = w - 20;
        endX.y = centerY;
        endY.x = centerX;
        endY.y = h - 20;

        control.x = startY.x;
        control.y = startX.y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(startX.x,startX.y,mPaint);
        canvas.drawPoint(endX.x,endX.y,mPaint);
        canvas.drawPoint(startY.x,startY.y,mPaint);
        canvas.drawPoint(endY.x,endY.y,mPaint);
        canvas.drawPoint(control.x,control.y,mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        canvas.drawLine(startX.x,startX.y,endX.x,endX.y,mPaint);
        canvas.drawLine(startY.x,startY.y,endY.x,endY.y,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startX.y = event.getY();
        endX.y = event.getY();

        startY.x = event.getX();
        endY.x = event.getX();

        control.x = startY.x;
        control.y = startX.y;
        invalidate();
        return true;
    }
}
