package com.example.dongdong_weather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dongdong_weather.activity.SettingActivity;
import com.example.dongdong_weather.activity.SignUpActivity;
import com.example.dongdong_weather.activity.WeatherActivity;
import com.example.dongdong_weather.custom.ClearEditText;
import com.example.dongdong_weather.custom.PinyinComparator;
import com.example.dongdong_weather.custom.SideBar;
import com.example.dongdong_weather.custom.SlidingMenu;
import com.example.dongdong_weather.custom.SortAdapter;
import com.example.dongdong_weather.custom.TitleBuilder;
import com.example.dongdong_weather.db.WeatherDB;
import com.example.dongdong_weather.model.AreaCode;
import com.example.dongdong_weather.util.HttpCallbackListener;
import com.example.dongdong_weather.util.Utility;

public class MainActivity extends Activity  {

	/* 滑动菜单 */
	private SlidingMenu mMenu;
    private ProgressDialog progressDialog;

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    private List<AreaCode> areaCodeList;
    private PinyinComparator pinyinComparator;

    private WeatherDB weatherDB;
    
    /* 设置 */
    private TextView appSetting;
    /* 注册 */
    private View rl;

    /**
     * 是否从WeatherActivity中跳转过来。
     */
    private boolean isFromWeatherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // 已经选择了城市且不是从WeatherActivity跳转过来，才会直接跳转到WeatherActivity
        if (!TextUtils.isEmpty(prefs.getString("preferenceCity", "")) && !isFromWeatherActivity) {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        /**
         * 初始化标题
         */
        initTitle();
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);

        if (!Utility.checkExists()) {
            //Toast.makeText(MainActivity.this, Environment.getDataDirectory().getAbsolutePath(), Toast.LENGTH_LONG);
            initAreaCodeData();
        }
        else {
            initViews();
        }
    }
    
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件
         * 2.设置中间TextView显示的文字
         * 3.设置右边的图片按钮显示，并设置事件
         */
    	TitleBuilder title = new TitleBuilder(this).setMiddleTitleText("城市选择")
    			.setLeftImageRes(R.drawable.layout_right_menu)
    			.setLeftTextOrImageListener(leftCilckListener);
    	
    	if (isFromWeatherActivity) {
    		title.setRightText("返回").setRightTextOrImageListener(rightCilckListener);
    	}

    }
    
    private View.OnClickListener leftCilckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**
             * 也可根据上面的配置进行监听 此处做演示使用
             */
            if (v.getId() == R.id.title_left_imageview) {
                /**
                 * 左边图片点击动作-打开或关闭滑动菜单
                 */
            	mMenu.toggle();
            }
        }
    };
    private View.OnClickListener rightCilckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.title_right_textview) {
                /**
                 * 右边文字点击动作-返回
                 */
            	Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    
    /**
     * 复制数据库模板，显示进度条，复制成功后显示主Activity
     */
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
                        initViews();
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

    /* sortListView */
    private void initViews() {
        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplication(), ((AreaCode)adapter.getItem(position)).getNameZh(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra("county_code", ((AreaCode)adapter.getItem(position)).getAreaId());
                startActivity(intent);
                finish();
            }
        });

        weatherDB = WeatherDB.getInstance(this);
        areaCodeList = weatherDB.loadCities();
        Collections.sort(areaCodeList, pinyinComparator);
        adapter = new SortAdapter(this, areaCodeList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        
        appSetting = (TextView) findViewById(R.id.app_setting);
        appSetting.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                //finish();
	        }
	    });
        rl = findViewById(R.id.menu_sign_up);
        rl.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                //finish();
	        }
	    });
    }

    private void filterData(String filterStr){
        List<AreaCode> filterDateList = new ArrayList<AreaCode>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = areaCodeList;
        }else{
            filterDateList.clear();
            for(AreaCode areaCode : areaCodeList){
                String name = areaCode.getNameZh();
                if(name.indexOf(filterStr.toString()) != -1 || areaCode.getNameEn().startsWith(filterStr.toString())){
                    filterDateList.add(areaCode);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}
