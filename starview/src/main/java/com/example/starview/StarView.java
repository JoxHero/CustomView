package com.example.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zyp on 2017/5/3.
 */

public class StarView extends View {

    private Context mContext;           // 上下文
    private int mWidth, mHeight;        // 宽高

    private Paint mPaint;
    float phase ;
    public StarView(Context context) {
        super(context, null);
        phase = 0;
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        phase = 0;
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }


    /**
     * View大小确定
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 绘制内容
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 移动坐标系到画布中央
        canvas.drawColor(Color.CYAN);
       // canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.moveTo(200, 600);
        path.lineTo(800, 600);
        PathMeasure measure = new PathMeasure(path, false);
        float length = measure.getLength();
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(10);
        PathEffect effects = new DashPathEffect(new float[] {length,length}, length -  phase * length);
        p.setPathEffect(effects);
        canvas.drawPath(path,p);
        phase = phase + 0.1f;
        // 重绘，产生动画效果
        if(phase<=1){
            invalidate();
        }
    }
}
