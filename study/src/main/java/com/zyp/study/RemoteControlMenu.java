package com.zyp.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zyp on 2017/7/20.
 */

public class RemoteControlMenu extends View {
    private final Context mContext;
    private Region regionRight, regionBottom, regionLeft, regionTop;
    private Paint defaultPaint;
    private int mWidth;
    private int mHeight;
    private Paint rectPaint;
    private TouchPostion touchFlag = TouchPostion.OUTSIDE;
    private TouchPostion currectFlag = TouchPostion.OUTSIDE;
    int mDefauColor = 0xFF4E5268;
    int mTouchedColor = 0xFFDF9C81;
    Matrix mMapMatrix = null;
    public RemoteControlMenu(Context context) {
        this(context, null);
    }

    public RemoteControlMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    enum TouchPostion {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        OUTSIDE
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mMapMatrix.reset();
    }

    private void init() {
        defaultPaint = new Paint();
        defaultPaint.setColor(mDefauColor);
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(10f);

        regionRight = new Region();
        regionBottom = new Region();
        regionLeft = new Region();
        regionTop = new Region();

        mMapMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        if (mMapMatrix.isIdentity()) {
            canvas.getMatrix().invert(mMapMatrix);
        }
        Region canvasRegion = new Region(-mWidth, -mHeight, mWidth, mHeight);

        int minWidth = mWidth > mHeight ? mHeight : mWidth;
        minWidth *= 0.8;
        int br = minWidth / 2;
        int sr = minWidth / 4;
        RectF bigCircle = new RectF(-br, -br, br, br);
        RectF smallCircle = new RectF(-sr, -sr, sr, sr);
        canvas.drawRect(bigCircle, rectPaint);
        canvas.drawRect(smallCircle, rectPaint);
        float bigSweepAngle = 84;
        float smallSweepAngle = -80;
        canvas.drawCircle(0, 0, 150, defaultPaint);

        Path rightPath = new Path();
        rightPath.addArc(bigCircle, -40, bigSweepAngle);
        rightPath.arcTo(smallCircle, 40, smallSweepAngle);
        regionRight.setPath(rightPath, canvasRegion);
        canvas.drawPath(rightPath, defaultPaint);

        Path bottomPath = new Path();
        bottomPath.addArc(bigCircle, 50, bigSweepAngle);
        bottomPath.arcTo(smallCircle, 130, smallSweepAngle);
        regionBottom.setPath(bottomPath, canvasRegion);
        canvas.drawPath(bottomPath, defaultPaint);

        Path leftPath = new Path();
        leftPath.addArc(bigCircle, 140, bigSweepAngle);
        leftPath.arcTo(smallCircle, 220, smallSweepAngle);
        regionLeft.setPath(leftPath, canvasRegion);
        canvas.drawPath(leftPath, defaultPaint);

        Path topPath = new Path();
        topPath.addArc(bigCircle, 230, bigSweepAngle);
        topPath.arcTo(smallCircle, 310, smallSweepAngle, false);
        regionTop.setPath(topPath, canvasRegion);
        canvas.drawPath(topPath, defaultPaint);

        defaultPaint.setColor(mTouchedColor);
        switch (touchFlag){
            case TOP:
                canvas.drawPath(topPath,defaultPaint);
                break;
            case RIGHT:
                canvas.drawPath(rightPath,defaultPaint);
                break;
            case BOTTOM:
                canvas.drawPath(bottomPath,defaultPaint);
                break;
            case LEFT:
                canvas.drawPath(leftPath,defaultPaint);
                break;
            default:
                break;
        }
        defaultPaint.setColor(mDefauColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float[] pts = new float[2];
        pts[0] = event.getRawX();
        pts[1] = event.getRawY();
        mMapMatrix.mapPoints(pts);
        int temp_x = (int) pts[0];
        int temp_y = (int) pts[1];
/*
        int temp_x = (int) event.getX();
        int temp_y = (int) event.getY();
*/
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchedPath(temp_x, temp_y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchFlag = getTouchedPath(temp_x, temp_y);
                break;
            case MotionEvent.ACTION_UP:
                currectFlag = getTouchedPath(temp_x, temp_y);
                if (currectFlag == touchFlag && currectFlag != TouchPostion.OUTSIDE) {
                    menuTouchListener.onClick(currectFlag);
                }
                touchFlag = currectFlag = TouchPostion.OUTSIDE;
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private TouchPostion getTouchedPath(int x, int y) {
        if (regionRight.contains(x, y)) {
            return TouchPostion.RIGHT;
        } else if (regionBottom.contains(x, y)) {
            return TouchPostion.BOTTOM;
        } else if (regionLeft.contains(x, y)) {
            return TouchPostion.LEFT;
        } else if (regionTop.contains(x, y)) {
            return TouchPostion.TOP;
        }
        return TouchPostion.OUTSIDE;
    }

    private MenuTouchListener menuTouchListener;

    public void setMenuTouchListener(MenuTouchListener menuTouchListener) {
        this.menuTouchListener = menuTouchListener;
    }

    public interface MenuTouchListener{
        void onClick(TouchPostion touchPostion);
    }
}
