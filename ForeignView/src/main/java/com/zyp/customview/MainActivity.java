package com.zyp.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ForeignView foreignView;
    private QuoteView foreignMarketView;
    private RiskLineView riskLineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("zyp", "onCreate: ");
        foreignView = (ForeignView)findViewById(R.id.foreign_view);
        foreignMarketView = (QuoteView)findViewById(R.id.foreign_market_view);
        foreignView.setType("ff");
        //foreignView.setCurrency("USA","JPY");
        foreignView.setFutures("hkd");
        foreignMarketView.setParameter(1,2,1.07335f,1.0733f,12);
        foreignMarketView.setQuoteOnClickListener(new QuoteView.QuoteBuyOnClickListenter() {
            @Override
            public void onBuyClick() {
                Toast.makeText(MainActivity.this,"buy",Toast.LENGTH_SHORT).show();
            }
        }, new QuoteView.QuoteSellOnClickListenter() {
            @Override
            public void onSellClick() {
                Toast.makeText(MainActivity.this,"sell",Toast.LENGTH_SHORT).show();
            }
        });

        riskLineView = (RiskLineView)findViewById(R.id.view_risk);
        riskLineView.setLineWidth(400,2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zyp", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("zyp", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zyp", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("zyp", "onDestroy: ");
    }
}
