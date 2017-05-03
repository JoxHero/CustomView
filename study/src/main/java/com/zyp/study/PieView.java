package com.zyp.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by zyp on 2017/5/3.
 * 扇形图
 */

public class PieView extends View{
    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null == mData){
            return;
        }

        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth / 2,mHeight / 2 );
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rect = new RectF(-r,-r,r,r);
        for(int i = 0 ;i<mData.size();i++){
            PieData pie = mData.get(i);
            canvas.drawArc(rect,currentStartAngle,pie.angle,true,mPaint);
            currentStartAngle += pie.angle;
        }
    }

    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();   // 刷新
    }

    // 设置数据
    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    // 初始化数据
    private void initData(ArrayList<PieData> mData) {
        if (null == mData || mData.size() == 0)   // 数据有问题 直接返回
            return;

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.value;       //计算数值和

            int j = i % mColors.length;       //设置颜色
            pie.color = (mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.value / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度

            pie.percentage = percentage;                  // 记录百分比
            pie.angle = angle;                            // 记录角度大小
            sumAngle += angle;
        }
    }

    public static class PieData {
        // 用户关心数据
        public String name;        // 名字
        public float value;        // 数值
        public float percentage;   // 百分比

        // 非用户关心数据
        public int color = 0;      // 颜色
        public float angle = 0;    // 角度

        public PieData(@NonNull String name, @NonNull float value) {
            this.name = name;
            this.value = value;
        }


    }
}