package com.zyp.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.HashMap;

/**
 * Created by zyp on 2016/10/9.
 */

public class ForeignView extends View {
    private final String TAG = "ForeignView";
    private int type = 2; //view 类型 1 ：期货， 2： 外汇

    private Context mContext;
    private int widthSize;
    private int heightSize;
    private Paint rectPaint;
    private Paint textPaint;
    private int textSize = 35;

    private HashMap<String, Bitmap> currencyMap;
    private Bitmap[] currencyPair;//货币对
    private String futuresName; //期货 合约名
    private int[] futuresColor;
    private float backgroundWidth;
    private float backgroundHeight;

    public ForeignView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ForeignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ForeignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthSize = w;
        heightSize = h;
    }

    private void init() {
        currencyMap = new HashMap<>();
        currencyMap.put("AUD", getBitmap(R.drawable.australia));// flag_australia
        currencyMap.put("CAD", getBitmap(R.drawable.canada)); //flag_canada
        currencyMap.put("GBP", getBitmap(R.drawable.england)); //flag_england
        currencyMap.put("EUR", getBitmap(R.drawable.european_union));//european
        currencyMap.put("HKD", getBitmap(R.drawable.hk)); //flag_hk
        currencyMap.put("JPY", getBitmap(R.drawable.japan));//flag_japan
        currencyMap.put("NZD", getBitmap(R.drawable.new_zealand));//flag_new_zealand
        currencyMap.put("SGD", getBitmap(R.drawable.singapore));//flag_singapore
        currencyMap.put("GHF", getBitmap(R.drawable.switzerland));//flag_switzerland
        currencyMap.put("USA", getBitmap(R.drawable.usa));//flag_usa

        futuresColor = new int[]{Color.rgb(135, 118, 227), Color.rgb(6, 181, 255)};
    }

    private void initPaint() {
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        textPaint = new Paint();
        if (futuresName == null) {
            return;
        } else if (futuresName.equalsIgnoreCase("hkd")) {
            futuresName = futuresName.toUpperCase();
            rectPaint.setColor(futuresColor[0]);
        } else if (futuresName.equalsIgnoreCase("usd")) {
            rectPaint.setColor(futuresColor[1]);
        }
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(textSize);
        backgroundWidth = textPaint.measureText(futuresName);
        Log.d(TAG, "backgroundWidth: " + backgroundWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        switch (type) {
            case 1:
                Log.d(TAG, "type: ff");
                drawFutures(canvas);
                break;
            case 2:
                Log.d(TAG, "type: fe");
                drawCurrency(canvas);
                break;
            default:
                break;
        }
    }

    private void drawCurrency(Canvas canvas) {
        if (currencyPair != null) {
            //canvas.drawBitmap(currencyPair[0], new Matrix(), new Paint());
            //画货币A
            Rect srcA = new Rect(0, 0, currencyPair[0].getWidth(), currencyPair[0].getHeight());
            Rect dstA = new Rect(0, 0, 100, 70);
            canvas.drawBitmap(currencyPair[0], srcA, dstA, null);

            canvas.translate(30, 25);
            //画货币B
            Rect srcB = new Rect(0, 0, currencyPair[1].getWidth(), currencyPair[0].getHeight());
            Rect dstB = new Rect(0, 0, 100, 70);
            canvas.drawBitmap(currencyPair[1], srcB, dstB, null);
        }
    }

    private void drawFutures(Canvas canvas) {
        //画背景
        if(futuresName.equalsIgnoreCase("hkd")){
            rectPaint.setColor(futuresColor[0]);
        }else if(futuresName.equalsIgnoreCase("usd")){
            rectPaint.setColor(futuresColor[1]);
        }
        RectF rectF = new RectF(0, 0, widthSize, heightSize);
        canvas.drawRoundRect(rectF, 6, 6, rectPaint);
        //画文字
        canvas.drawText(futuresName, (widthSize - backgroundWidth)/2, ((heightSize - textSize)/2+textSize - 5), textPaint);
    }

    /**
     * 设置类型
     *
     * @param type "ff" 外盘期货 "fe" 外汇
     */
    public void setType(String type) {
        if (type.equals("ff")) {
            this.type = 1;
        } else if (type.equals("fe")) {
            this.type = 2;
        }
    }

    /**
     * 设置货币对
     *
     * @param currencyA
     * @param currencyB
     */
    public void setCurrency(String currencyA, String currencyB) {

        if (currencyMap == null) {
            Log.d(TAG, "currencyMap is null!");
            return;
        }

        if ((!currencyMap.containsKey(currencyA)) && (!currencyMap.containsKey(currencyB))) {
            Log.d(TAG, "currencyMap no contanins currency key ");
            return;
        }
        currencyPair = new Bitmap[]{currencyMap.get(currencyA), currencyMap.get(currencyB)};
        postInvalidate();
    }

    public void setFutures(String futuresName) {
        this.futuresName = futuresName;
        postInvalidate();

    }

    private Bitmap getBitmap(int res) {
        return BitmapFactory.decodeResource(mContext.getResources(), res);
    }
}
