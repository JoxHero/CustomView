package com.zyp.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by zyp on 2016/9/5.
 */

public class RiskLineView extends View {
    private final String TAG = "LineView";
    private final int GREEN = 0;
    private final int YELLOW = 1;
    private final int ORANGE = 2;
    private final int RED = 3;
    private Context context;
    private Paint linePaint; //线
    private Paint hintPaint; //提示框
    private Paint textPaint; //外面字
    private Paint trianglePaint; //三角
    private Paint riskTextPaint; // 提示框内部字
    private Paint hintCirclePaint; //提示框圆

    private int width;
    private int height;
    public int startLine;
    public int stopLine;
    public int startTop;
    public int lineStartTop;
    public int lineHeight;
    public int lineBottom;
    private float beginPosition;
    private float average; //100份 平均每一份的宽

    private int textSize;
    private int riskTextSize;

    private int riskTotalCopies = 600;
    private float riskCopies; // risk占总的分数
    private float riskWidth;
    private int greenColor = Color.rgb(78, 194, 155);
    private int yellowColor = Color.rgb(252, 176, 76);
    private int orangeColor = Color.rgb(252, 154, 25);
    private int redColor = Color.rgb(252, 101, 101);
    private int colorHintBg = Color.rgb(83, 83, 83);
    private int colorHintPeople = Color.rgb(136, 136, 136);
    private double risk;
    private Paint hintBgPaint;
    private int color;

    public RiskLineView(Context context) {
        super(context);
        this.context = context;
    }


    public RiskLineView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        //setLineWidth(550);
    }

    public RiskLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    private void init() {
        if (width == 0) {
            width = getWidth();
        }
        if (height == 0) {
            height = getHeight();
        }

        if (startLine == 0) {
            startLine = dpToPx(16);
        }

        if (stopLine == 0) {
            stopLine = width - dpToPx(25);
        }

        if (startTop == 0) {
            startTop = 10;
        }

        if (lineStartTop == 0) {
            lineStartTop = dpToPx(50);
        }

        lineHeight = dpToPx(10);
        textSize = dpToPx(14);
        riskTextSize = dpToPx(14);

        lineBottom = lineStartTop + lineHeight;
        beginPosition = startLine + 5 * textSize;
        average = (stopLine - beginPosition) / 100;

        riskWidth = riskCopies * average;
    }

    private void initPaint() {
        linePaint = new Paint();
        //linePaint.setColor(Color.rgb(252, 176, 76));
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);
        if (risk < 0 || risk > 600) {
            return;
        }

        switch (color) {
            case GREEN:
                linePaint.setColor(greenColor);
                break;
            case YELLOW:
                linePaint.setColor(yellowColor);
                break;
            case ORANGE:
                linePaint.setColor(orangeColor);
                break;
            case RED:
                linePaint.setColor(redColor);
                break;
        }

        hintPaint = new Paint();
        hintPaint.setColor(Color.rgb(238, 238, 238));
        hintPaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setStrokeWidth(2);
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        trianglePaint = new Paint();
        trianglePaint.setColor(Color.rgb(252, 176, 76));
        trianglePaint.setStrokeWidth(1);
        trianglePaint.setAntiAlias(true);

        hintCirclePaint = new Paint();
        hintCirclePaint.setColor(colorHintPeople);
        hintCirclePaint.setStrokeWidth(1);
        hintCirclePaint.setAntiAlias(true);

        hintBgPaint = new Paint();
        hintBgPaint.setColor(colorHintBg);
        hintBgPaint.setStrokeWidth(1);
        hintBgPaint.setAntiAlias(true);

        riskTextPaint = new Paint();
        riskTextPaint.setColor(Color.rgb(255, 255, 255));
        riskTextPaint.setTextSize(riskTextSize);
        riskTextPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        initPaint();
        Log.d(TAG, "startLine: " + startLine + "\n" + "stopLine : " + stopLine);
        canvas.drawText("资金风险：", startLine, lineBottom, textPaint);

        canvas.drawRect(beginPosition, lineStartTop, stopLine, lineBottom, hintPaint);// 长方形

        canvas.drawRect(beginPosition, lineStartTop, beginPosition + riskWidth, lineBottom, linePaint);// 长方形

        drawTriangle(canvas, 400);
        trianglePaint.setColor(Color.rgb(252, 154, 25));
        drawTriangle(canvas, 550);
        trianglePaint.setColor(Color.rgb(252, 101, 101));
        drawTriangle(canvas, 600);

        drawHint(canvas);
        //canvas.drawBitmap(context.getdR, x, y, null);
    }

    private void drawHint(Canvas canvas) {
        //先画对话框底部的三角形
        trianglePaint.setColor(colorHintBg);
        Path hintTrianglePath = new Path();
        hintTrianglePath.setLastPoint(beginPosition + riskWidth, lineStartTop - dpToPx(3));
        hintTrianglePath.lineTo(beginPosition + riskWidth - getTriangleBottom(dpToPx(12)), lineStartTop - dpToPx(12));
        hintTrianglePath.lineTo(beginPosition + riskWidth + getTriangleBottom(dpToPx(12)), lineStartTop - dpToPx(12));
        hintTrianglePath.close();
        canvas.drawPath(hintTrianglePath, trianglePaint);
        //画对话框
        float hintTop = lineStartTop - dpToPx(35);
        float hintBottom = lineStartTop - dpToPx(12);
        float hintLeft = beginPosition + riskWidth - getTriangleBottom(dpToPx(12)) - dpToPx(22);
        float hintRight = beginPosition + riskWidth + getTriangleBottom(dpToPx(12)) + dpToPx(22);
        RectF rectF = new RectF(hintLeft, hintTop, hintRight, hintBottom);
        canvas.drawRoundRect(rectF, dpToPx(5), dpToPx(5), hintBgPaint);

        //画文字
        textPaint.setTextSize(50);
        String riskStr = formatChange(risk, 2) + "%";
        Log.d(TAG, "riskStr.length: " + riskStr.length());
        if (riskStr.length() > 6) {
            canvas.drawText(riskStr, hintLeft + dpToPx(4), hintBottom - (hintBottom - hintTop) / 2 + (riskTextSize / 2), riskTextPaint);
        } else {
            canvas.drawText(riskStr, hintLeft + dpToPx(10), hintBottom - (hintBottom - hintTop) / 2 + (riskTextSize / 2), riskTextPaint);

        }
    }

    private void drawTriangle(Canvas canvas, int position) {
        //400 时 三角形的顶点
        Path trianglePath = new Path();
        float triangleOneX = getLineWidth(position);
        trianglePath.setLastPoint(beginPosition + triangleOneX, lineBottom);
        Log.d(TAG, "drawTriangle: triangleOneX : " + triangleOneX + " triangleOneX-getTriangleBottom() : " + (triangleOneX - getTriangleBottom(35)));
        trianglePath.lineTo(beginPosition + triangleOneX - getTriangleBottom(dpToPx(12)), lineBottom + dpToPx(12));
        trianglePath.lineTo(beginPosition + triangleOneX + getTriangleBottom(dpToPx(12)), lineBottom + dpToPx(12));
        trianglePath.close();
        canvas.drawPath(trianglePath, trianglePaint);
    }

    private int getTriangleBottom(double triangleH) {
        return(int) Math.sqrt(Math.pow(triangleH, 2) / 3);
    }

    public void setLineWidth(double risk, int color) {
        this.risk = risk;
        this.color = color;
        riskCopies = (float) risk * 100 / riskTotalCopies;
        postInvalidate();
    }

    public float getLineWidth(int w) {
        return w * 100 / riskTotalCopies * average;
    }

    /**
     * 将指定的字符串转换成制定小数点位数的String字符串
     *
     * @param d
     * @param scales
     * @return
     */
    public static String formatChange(double d, int scales) {
        try {
            String dStr = String.valueOf(d);
            BigDecimal bd1 = new BigDecimal(dStr);
            bd1 = bd1.setScale(scales, BigDecimal.ROUND_HALF_UP);
            return bd1.toString();
        } catch (Exception e) {
            return "0.00";
        }
    }

    public int dpToPx(int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public float dpToPx(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

}
