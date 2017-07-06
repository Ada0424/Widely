package com.zqw.widely.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zqw.widely.R;
import com.zqw.widely.bean.News;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.wv_detail)
    WebView wvDetail;
    private WebView wv_detail;
    private Intent intent;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setEvent();

    }
    //这里是在登录界面label上右上角添加三个点，里面可添加其他功能
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
        return true;
    }
    private void setEvent() {
        intent = getIntent();
        news = intent.getExtras().getParcelable("news");
        wvDetail.loadUrl(news.getUrl());
        WebSettings setting = wvDetail.getSettings();
        setting.setJavaScriptEnabled(true);
        wvDetail.setWebViewClient(new WebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
    }

}
