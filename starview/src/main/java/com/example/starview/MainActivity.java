package com.example.starview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PathView path_view = (PathView) findViewById(R.id.path);
        path_view.init();
        //path_view.setPhase(0.5f);
        path_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                path_view.init();
            }
        });
    }
}
