package zyp.com.recycleviewanima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "zyp";
    public LinearLayout linearLayout;
    private AnimationView mHeader;
    private float mTouchStartY;
    private float mTouchCurY;
    private float mPullHeight;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.ll_coutent);
        mHeader = (AnimationView) findViewById(R.id.animationView);
        mPullHeight =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = event.getY();
                Log.d(TAG, "ACTION_DOWN: mTouchStartY = "+mTouchStartY);
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchCurY = event.getY();
                Log.d(TAG, "ACTION_MOVE: mTouchCurY = "+mTouchCurY);
                float dy = mTouchCurY - mTouchStartY;
                    Log.d(TAG, "ACTION_MOVE: dy = " +dy);
                    Log.d(TAG, "ACTION_MOVE: mHeader.getLayoutParams().height 1= " + mHeader.getLayoutParams().height);
                    int headerH = mHeader.getLayoutParams().height;
                    headerH = (int) (dy+ headerH);
                    Log.d("zyp222", "dy: "+dy);
                    mHeader.setChange(dy/2);
                    //mHeader.requestLayout();
                    Log.d(TAG, "ACTION_MOVE: mHeader.getLayoutParams().height 2= " + mHeader.getLayoutParams().height);
                break;
            case MotionEvent.ACTION_UP:
                mHeader.releaseDrag();
                break;
        }
        return true;
    }
}
