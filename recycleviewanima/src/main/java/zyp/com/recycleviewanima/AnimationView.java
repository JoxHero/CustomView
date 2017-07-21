package zyp.com.recycleviewanima;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhanglei on 15/7/18.
 */
public class AnimationView extends View{

    private static final String TAG = "zyp";

    public int PULL_HEIGHT;
    private int PULL_DELTA;
    private float mWidthOffset;

    private Paint mBackPaint;
    private Path mPath;

    private AnimatorStatus mAniStatus = AnimatorStatus.DEFAULT;
    private float changeH = 100;
    private int DEFAULT_ARC_HEIGHT = 100;
    private float MAX_ARC_HEIGHT = 200;
    private int defaut_h;
    private Bitmap bgBitMap = null;
    private Context mContext;

    enum AnimatorStatus {
        DEFAULT,
        DRAG_DOWN,
        REL_DRAG, DRAG_UP;

        @Override
        public String toString() {
            switch (this) {
                case DEFAULT:
                    return "pull down";
                case DRAG_DOWN:
                    return "drag down";
                case REL_DRAG:
                    return "release drag!";
                default:
                    return "unknown state";
            }
        }
    }

    public AnimationView(Context context) {
        this(context, null, 0);
    }

    public AnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        PULL_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics());
        PULL_DELTA = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        mWidthOffset = 0.5f;
        mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(Color.argb(255,246,204,58));
        mBackPaint.setAlpha(225);
        mPath = new Path();

    }

    private int mWidth;
    private int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
       /* if (height > PULL_DELTA + PULL_HEIGHT) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(PULL_DELTA + PULL_HEIGHT, MeasureSpec.getMode(heightMeasureSpec));
        }*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: changed : "+changed);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();

           /* if (mHeight < PULL_HEIGHT) {
                mAniStatus = AnimatorStatus.DEFAULT;
            }

            switch (mAniStatus) {
                case DEFAULT:
                case REL_DRAG:
                    if (mHeight >= PULL_HEIGHT) {
                        mAniStatus = AnimatorStatus.DRAG_DOWN;
                    }
                    break;
            }*/
        }

        /*if (mAniStatus == AnimatorStatus.START || mAniStatus == AnimatorStatus.DONE){
            mAniStatus = AnimatorStatus.DRAG_DOWN;
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: mAniStatus : "+mAniStatus);
        switch (mAniStatus) {
            case DEFAULT:
            case REL_DRAG:
                drawInitialDrag(canvas);
                break;
            case DRAG_DOWN:
                drawDrag(canvas);
                break;
            default:
                break;
        }

        Drawable db = getResources().getDrawable(R.drawable.no_user_head);
        BitmapDrawable drawable = (BitmapDrawable)db;

        bgBitMap = drawable.getBitmap();

        if(bgBitMap != null){
            Rect src = new Rect(0,0,bgBitMap.getWidth(),bgBitMap.getHeight());
            Rect dst = new Rect(0,0,mWidth,PULL_HEIGHT);
            canvas.drawBitmap(bgBitMap,src,dst,new Paint());
        }
        defaut_h = mHeight;
    }

    public void setBgBitMap(Bitmap bgBitMap) {
        this.bgBitMap = bgBitMap;
        invalidate();
    }

    private void drawInitialDrag(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, PULL_HEIGHT, mBackPaint);
        mPath.reset();
        mPath.moveTo(0, PULL_HEIGHT);
        mPath.quadTo(mWidthOffset * mWidth, PULL_HEIGHT + DEFAULT_ARC_HEIGHT,
                mWidth, PULL_HEIGHT);
        canvas.drawPath(mPath, mBackPaint);
    }

    public void drawDrag(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, PULL_HEIGHT, mBackPaint);
        mPath.reset();
        mPath.moveTo(0, PULL_HEIGHT);
        mPath.quadTo(mWidthOffset * mWidth, PULL_HEIGHT + DEFAULT_ARC_HEIGHT + changeH,
                mWidth, PULL_HEIGHT);
        canvas.drawPath(mPath, mBackPaint);
    }

    public void setChange(float dy) {
        mAniStatus = AnimatorStatus.DRAG_DOWN;
        if(dy <= MAX_ARC_HEIGHT){
            if(dy <= 0){
                if(DEFAULT_ARC_HEIGHT - (int)Math.abs(dy) >= 0){
                  changeH = (int)dy;
                }
            }else{
                changeH = (int)dy;
            }
            getLayoutParams().height = PULL_HEIGHT + DEFAULT_ARC_HEIGHT + (int) changeH;
            requestLayout();
            //invalidate();
        }
    }

    public void releaseDrag() {
        mAniStatus = AnimatorStatus.REL_DRAG;
        getLayoutParams().height = PULL_HEIGHT + DEFAULT_ARC_HEIGHT;
        //invalidate();
        requestLayout();
    }

    public void setAniBackColor(int color) {
        mBackPaint.setColor(color);
    }

    public void setAniForeColor(int color) {
      /*  mBallPaint.setColor(color);
        mOutPaint.setColor(color);*/
        setBackgroundColor(color);
    }
}
