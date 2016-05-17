package com.example.dongdong_weather.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dongdong_weather.R;
import com.example.dongdong_weather.custom.TitleBuilder;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initTitle();
		
		if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
	}
	
	private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件
         * 2.设置中间TextView显示的文字
         * 3.设置右边的图片按钮显示，并设置事件
         */
    	new TitleBuilder(this).setMiddleTitleText("设置")
    	    .setRightText("返回").setRightTextOrImageListener(rightCilckListener);
    }
	
	private View.OnClickListener rightCilckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.title_right_textview) {
                /**
                 * 右边文字点击动作-返回
                 */
                finish();
            }
        }
    };

	
	/**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
