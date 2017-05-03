package com.zyp.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PieView pieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieView = (PieView) findViewById(R.id.v_pie);

        List<PieView.PieData> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PieView.PieData data = new PieView.PieData(""+i,((i % 2 == 0) ? i + 10f : i-10f));
            datas.add(data);
        }
        pieView.setData((ArrayList<PieView.PieData>) datas);
    }
}
