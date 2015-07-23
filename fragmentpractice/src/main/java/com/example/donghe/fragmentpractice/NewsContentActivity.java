package com.example.donghe.fragmentpractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class NewsContentActivity extends Activity {

    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("n_title", newsTitle);
        intent.putExtra("n_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_content);

        String n_title = getIntent().getStringExtra("n_title");
        String n_content = getIntent().getStringExtra("n_content");

        NewsContentFragment fragment = (NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
        fragment.refresh(n_title, n_content);
    }
}
