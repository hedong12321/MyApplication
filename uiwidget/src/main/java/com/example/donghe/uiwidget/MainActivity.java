package com.example.donghe.uiwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initFruits(); // 初始化水果数据
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        // 为ListView的子项添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFruits() {
        Fruit apple = new Fruit("Apple", R.mipmap.apple);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.mipmap.banana);
        fruitList.add(banana);
        Fruit orange = new Fruit("Orange", R.mipmap.orange);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.mipmap.watermelon);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear", R.mipmap.pear);
        fruitList.add(pear);
        Fruit grape = new Fruit("Grape", R.mipmap.grape);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.mipmap.pineapple);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.mipmap.strawberry);
        fruitList.add(strawberry);
        Fruit cherry = new Fruit("Cherry", R.mipmap.cherry);
        fruitList.add(cherry);
        Fruit mango = new Fruit("Mango", R.mipmap.mango);
        fruitList.add(mango);
        Fruit peach = new Fruit("Peach", R.mipmap.peach);
        fruitList.add(peach);
        Fruit wiki = new Fruit("Wiki Fruit", R.mipmap.wikifruit);
        fruitList.add(wiki);
        Fruit litchi = new Fruit("Litchi", R.mipmap.litchi);
        fruitList.add(litchi);
    }
}
