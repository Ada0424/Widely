package com.zqw.widely;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqw.widely.util.BottomNavigationViewEx;
import com.zqw.widely.view.activity.LoginActivity;
import com.zqw.widely.view.fragments.JokeFragment;
import com.zqw.widely.view.fragments.NewsFragment;
import com.zqw.widely.view.fragments.PersonFragment;
import com.zqw.widely.view.fragments.PicFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.navigation)
    BottomNavigationViewEx navigation;
    @Bind(R.id.container)
    LinearLayout container;
    private NewsFragment newsfragment;
    private JokeFragment jokefragment;
    private PicFragment picfragment;
    private PersonFragment personfragment;
private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Bmob.initialize(this, "a340adcdaedbaeda7d35a24c20f69eda");
        newsfragment = new NewsFragment();
        replaceFragment(newsfragment);
        initView();

    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    newsfragment = new NewsFragment();
                    replaceFragment(newsfragment);
                    return true;
                case R.id.navigation_joke:
                    jokefragment = new JokeFragment();
                    replaceFragment(jokefragment);
                    return true;
                case R.id.navigation_picture:
                    picfragment = new PicFragment();
                    replaceFragment(picfragment);
                    return true;
                case R.id.navigation_person:
                   personfragment = new PersonFragment();
                    replaceFragment(personfragment);
                    return true;
            }
            return false;
        }
    };
    private void replaceFragment(Fragment fragment) {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }


   private void initView() {
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



}
