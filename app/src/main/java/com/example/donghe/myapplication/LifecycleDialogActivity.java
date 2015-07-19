package com.example.donghe.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by dong.he on 2015/7/19.
 */
public class LifecycleDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lifecycle_dialog);
    }
}
