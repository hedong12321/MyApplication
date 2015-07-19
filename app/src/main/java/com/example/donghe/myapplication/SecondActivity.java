package com.example.donghe.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by dong.he on 2015/7/19.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("SecondActivity", "onCreate");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        Log.d("SecondActivity", intent.getStringExtra("ext_data"));

        Button button2 = (Button)findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("rtn_data", "Pass data to previous activity success");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // 体验活动的生命周期
        Button lifecycle_n = (Button)findViewById(R.id.lifecycle_normal);
        Button lifecycle_d = (Button)findViewById(R.id.lifecycle_dialog);

        lifecycle_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, LifecycleNormalActivity.class);
                startActivity(intent);
            }
        });

        lifecycle_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, LifecycleDialogActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 处理点击了返回键回传数据
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("rtn_data", "Pass data to previous activity success");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SecondActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SecondActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SecondActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SecondActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SecondActivity", "onRestart");
    }
}
