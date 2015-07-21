package com.example.donghe.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by dong.he on 2015/7/21.
 */
public class TitleBar extends LinearLayout {
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);

        Button back = (Button)findViewById(R.id.title_back);
        Button edit = (Button)findViewById(R.id.title_edit);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You Clicked Edit Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
