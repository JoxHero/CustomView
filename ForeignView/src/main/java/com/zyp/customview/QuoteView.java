package com.zyp.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zyp on 2016/10/10.
 */

public class QuoteView extends RelativeLayout implements View.OnClickListener {

    private final String TAG = "ForeignMarketView";
    public static final int QUOTE_STATUS_RIST = 1;
    public static final int QUOTE_STATUS_FALL = 2;
    public static final int QUOTE_STATUS_INVAR = 3;

    private TextView tvQuoteA;
    private TextView tvQuoteB;
    private TextView tvAt;

    private QuoteBuyOnClickListenter quoteBuyOnClickListenter;
    private QuoteSellOnClickListenter quoteSellOnClickListenter;

    public interface QuoteSellOnClickListenter{
        void onSellClick();
    }

    public interface QuoteBuyOnClickListenter{
        void onBuyClick();
    }

    public QuoteView(Context context) {
        super(context);
        initView();
    }

    public QuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_foreign_market, this);
        this.tvQuoteA = (TextView) findViewById(R.id.tv_quote_a);
        this.tvQuoteB = (TextView) findViewById(R.id.tv_quote_b);
        this.tvAt = (TextView) findViewById(R.id.tv_at);
        this.tvQuoteA.setOnClickListener(this);
        this.tvQuoteB.setOnClickListener(this);
        this.tvAt.setOnClickListener(this);
    }

    public void setParameter(int statusA, int statusB, float quoteA, float quoteB, int at) {
        tvQuoteA.setText(getSpannableString(quoteA));
        tvQuoteB.setText(getSpannableString(quoteB));
        tvAt.setText(String.valueOf(at));
        switch (statusA) {
            case QUOTE_STATUS_RIST:
                tvQuoteA.setBackgroundResource(R.drawable.bg_quote_rise_selector);
                //tvQuoteB.setBackgroundResource(R.drawable.bg_quote_rise_selector);
                break;
            case QUOTE_STATUS_FALL:
                tvQuoteA.setBackgroundResource(R.drawable.bg_quote_fall_selector);
                tvQuoteA.setBackgroundResource(R.drawable.bg_quote_fall_selector);
                break;
            case QUOTE_STATUS_INVAR:
                tvQuoteA.setBackgroundResource(R.drawable.bg_quote_invar_selector);
                tvQuoteB.setBackgroundResource(R.drawable.bg_quote_invar_selector);
                break;
        }

        switch (statusB) {
            case QUOTE_STATUS_RIST:
                tvQuoteB.setBackgroundResource(R.drawable.bg_quote_rise_selector);
                tvAt.setTextColor(getResources().getColor(R.color.quote_rise_pressed));
                break;
            case QUOTE_STATUS_FALL:
                tvQuoteB.setBackgroundResource(R.drawable.bg_quote_fall_selector);
                tvAt.setTextColor(getResources().getColor(R.color.quote_fall_pressed));
                break;
            case QUOTE_STATUS_INVAR:
                tvQuoteB.setBackgroundResource(R.drawable.bg_quote_invar_selector);
                tvAt.setTextColor(getResources().getColor(R.color.quote_invar_pressed));
                break;
        }
    }

    @NonNull
    private SpannableString getSpannableString(float quote) {
        String quoteStr = String.valueOf(quote);
        int start = quoteStr.length()-3;
        int end = quoteStr.length() -1;

        SpannableString msp = new SpannableString(quoteStr);
        msp.setSpan(new AbsoluteSizeSpan(19,true),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public void setQuoteOnClickListener(QuoteBuyOnClickListenter quoteBuyOnClickListenter,QuoteSellOnClickListenter quoteSellOnClickListenter){
        this.quoteBuyOnClickListenter = quoteBuyOnClickListenter;
        this.quoteSellOnClickListenter = quoteSellOnClickListenter;
    }

    @Override
    public void onClick(View view) {
      int id = view.getId();
        switch (id){
            case R.id.tv_quote_a:
                quoteBuyOnClickListenter.onBuyClick();
                break;
            case R.id.tv_quote_b:
                quoteSellOnClickListenter.onSellClick();
                break;
            default:
                break;
        }
    }
}
