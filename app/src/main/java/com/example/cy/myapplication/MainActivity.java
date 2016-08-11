package com.example.cy.myapplication;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;

import layout.MapFragment;
import layout.BlankFragment;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener {

    //    Button btn1;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        //隐藏标题栏
        getSupportActionBar().hide();

        //这样写，按钮功能还可以用，但其他地方就无法使用这个按钮了
//        Button btn1 = (Button) findViewById(R.id.button1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "发生地方水电费", Toast.LENGTH_LONG).show();
//            }
//        });

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "扫码"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "发现"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "我的"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

                if (fragments != null) {
                    if (position < fragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(position);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.layFrame, fragment);
                        } else {
                            ft.add(R.id.layFrame, fragment);
                        }
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {

                if (fragments != null) {
                    if (position < fragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(position);
                        ft.remove(fragment);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabReselected(int position) {
            }
        });


        fragments = getFragments();
        setDefaultFragment();

    }


    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.layFrame, HomeFragment.newInstance("Home"));
        transaction.replace(R.id.layFrame, MapFragment.newInstance("首页","地图"));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(HomeFragment.newInstance("Home"));
        fragments.add(MapFragment.newInstance("首页","地图"));

        fragments.add(BookFragment.newInstance("扫码"));
        fragments.add(BlankFragment.newInstance("发现","其他扩展功能"));
        fragments.add(TvFragment.newInstance("我的"));
        return fragments;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this,uri.getQuery(),Toast.LENGTH_LONG).show();
    }
}
