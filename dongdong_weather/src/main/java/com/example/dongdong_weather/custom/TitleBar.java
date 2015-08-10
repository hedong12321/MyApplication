package com.example.dongdong_weather.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dongdong_weather.R;

/**
 * Created by dong.he on 2015/7/21.
 */
public class TitleBar extends LinearLayout {
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);

        Button back = (Button)findViewById(R.id.title_back);
        Button menu = (Button)findViewById(R.id.title_menu);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You Clicked Menu Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
