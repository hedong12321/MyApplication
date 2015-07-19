package com.example.donghe.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 点击button开启第二个activity
        Button button1 = (Button)findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("ext_data", "Pass params to second activity success");
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "You Clicked the Setting Item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_item:
                Toast.makeText(this, "You Clicked the Add Item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You Clicked the Remove Item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_item:
                finish();
                break;
            default:

        }

        return true;

        //return super.onOptionsItemSelected(item);
    }

    /**
     * 处理被调用activity返回的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Log.d("MainActivity", data.getStringExtra("rtn_data"));
                }
                break;
            default:

        }
    }
}
