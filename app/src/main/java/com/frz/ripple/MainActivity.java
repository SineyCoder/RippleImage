package com.frz.ripple;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frz.rippleimage.RippleImage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RippleImage image = findViewById(R.id.image);
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private int i = 0;
            @Override
            public void run() {
                if(i < 100){
                    i++;
                    image.setMask(i+"");
                    h.postDelayed(this, 10);
                }else{
                    image.animation();
                }
            }
        }, 1000);
    }
}
