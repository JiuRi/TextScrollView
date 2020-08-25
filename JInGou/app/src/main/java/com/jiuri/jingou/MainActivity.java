package com.jiuri.jingou;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextScrollView mTextScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextScrollView = findViewById(R.id.textScrollView);
        int[] ints = {Color.RED,Color.BLUE,Color.YELLOW};
        mTextScrollView.setTextColorArrays(ints);
        Timer timer = new Timer();
        timer.schedule(new MyTimeTask(),2000,2000);

    }

    private int num = 0;
    private class MyTimeTask extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextScrollView.setText(" 我是第几个数据  "+num);
                    num++;
                }
            });

        }
    }
}