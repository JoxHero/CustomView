package com.zyp.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PieView pieView;
    private StarView starView;
    private RemoteControlMenu rcmView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcmView = (RemoteControlMenu) findViewById(R.id.rcm);
        /*pieView = (PieView) findViewById(R.id.v_pie);
        starView = (StarView)findViewById(R.id.v_star);
        List<PieView.PieData> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PieView.PieData data = new PieView.PieData(""+i,((i % 2 == 0) ? i + 10f : i-10f));
            datas.add(data);
        }
        pieView.setData((ArrayList<PieView.PieData>) datas);

        starView.check();*/
        rcmView.setMenuTouchListener(new RemoteControlMenu.MenuTouchListener() {
            @Override
            public void onClick(RemoteControlMenu.TouchPostion touchPostion) {
                switch (touchPostion){
                    case TOP:
                        Toast.makeText(MainActivity.this,"点击了上边",Toast.LENGTH_SHORT).show();
                        break;
                    case RIGHT:
                        Toast.makeText(MainActivity.this,"点击了右边",Toast.LENGTH_SHORT).show();
                        break;
                    case BOTTOM:
                        Toast.makeText(MainActivity.this,"点击了底边",Toast.LENGTH_SHORT).show();
                        break;
                    case LEFT:
                        Toast.makeText(MainActivity.this,"点击了左边",Toast.LENGTH_SHORT).show();
                        break;
                    case OUTSIDE:
                        break;
                }
            }
        });
    }
}
