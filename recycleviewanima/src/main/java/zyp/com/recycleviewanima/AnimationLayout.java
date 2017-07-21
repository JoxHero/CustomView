package zyp.com.recycleviewanima;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by zyp on 2017/7/7.
 */

public class AnimationLayout extends FrameLayout {
    public AnimationLayout(@NonNull Context context) {
        super(context);
    }

    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs);
        init(context,attrs);
    }

    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs){

    }
}
