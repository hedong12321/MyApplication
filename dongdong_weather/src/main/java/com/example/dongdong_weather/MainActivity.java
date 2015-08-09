package com.example.dongdong_weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.dongdong_weather.util.HttpCallbackListener;
import com.example.dongdong_weather.util.Utility;

public class MainActivity extends Activity  {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Utility.checkExists()) {
            //Toast.makeText(MainActivity.this, Environment.getDataDirectory().getAbsolutePath(), Toast.LENGTH_LONG);
            initAreaCodeData();
        }
    }



    private void initAreaCodeData() {
        showProgressDialog();
        Utility.initAreaCodeData(this, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(MainActivity.this, "初始化成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(MainActivity.this, "初始化失败，请重新运行程序！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在初始化...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
