package com.example.donghe.fragmentpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dong.he on 2015/7/23.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;

    public NewsAdapter(Context context, int textViewResourceId, List<News> objects) {
        super(context,textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);

        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }

        TextView textView = (TextView)view.findViewById(R.id.news_title);
        textView.setText(news.getTitle());

        return view;
    }
}
