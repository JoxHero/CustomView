package com.example.starview;



import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
/**
 * Created by zyp on 2017/5/4.
 */
public class PathView extends View
{
    Path path;
    Paint paint;
    Paint startPaint;
    float length;
    float startR = 30;
    private Path startPath;

    public PathView(Context context)
    {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        startPaint = new Paint();
        startPaint.setColor(Color.YELLOW);
        startPaint.setStrokeWidth(2);
        startPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
        path.moveTo(100, 100);
        //path.addCircle(100,100,startR, Path.Direction.CW);
        path.lineTo(300, 200);
        //path.addCircle(300,200,startR, Path.Direction.CW);
        path.lineTo(400, 400);
        //path.addCircle(400,400,startR, Path.Direction.CW);
        path.lineTo(600, 500);
        //path.addCircle(600,500,startR, Path.Direction.CW);
        path.lineTo(500, 700);
        //path.addCircle(500,700,startR, Path.Direction.CW);
        path.lineTo(900,800);
        //path.addCircle(900,800,startR, Path.Direction.CW);
        path.lineTo(1100,600);

        //path.addCircle(1100,600,startR, Path.Direction.CW);
        //path.quadTo(360,100,400,310);

        // Measure the path
        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(PathView.this, "phase", 0.0f, 1.0f);
        animator.setDuration(6000);
        animator.start();
    }

    //is called by animtor object
    public void setPhase(float phase)
    {
        Log.d("pathview", "setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();//will calll onDraw
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                pathLength - phase * pathLength);
//        return new DashPathEffect(new float[] { phase*pathLength, pathLength },
//               0);
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawColor(Color.BLACK);
        c.drawCircle(100,100,startR,startPaint);
        c.drawCircle(300, 200,startR,startPaint);
        c.drawCircle(400, 400,startR,startPaint);
        c.drawCircle(600, 500,startR,startPaint);
        c.drawCircle(500, 700,startR,startPaint);
        c.drawCircle(900,800,startR,startPaint);
        c.drawCircle(1100,600,startR,startPaint);

        c.drawPath(path, paint);
    }
}
