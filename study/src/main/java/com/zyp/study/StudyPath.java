package com.zyp.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyp on 2017/5/4.
 */

public class StudyPath extends View {
    private int mWidth, mHeight;
    private Paint mPaint;

    public StudyPath(Context context) {
        super(context,null);
        init();
    }

    public StudyPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawColor(Color.CYAN);
        Path linePath = new Path();
        linePath.lineTo(200,200);
        linePath.setLastPoint(200,100);
        linePath.moveTo(200,0);
        canvas.drawPath(linePath,mPaint);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(1);
        PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        p.setPathEffect(effects);
        canvas.drawLine(0, 40, mWidth, 40, p);
    }
}
