package com.zqw.widely.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zqw.widely.R;
import com.zqw.widely.bean.Picture;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.zqw.widely.R.mipmap.pic;

public class PicActivity extends AppCompatActivity {
    private Intent intent;
    private Picture picture;
    @Bind(R.id.wv_pic)
    WebView wvPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        intent = getIntent();
        Bundle bundle = new Bundle();
        picture = intent.getExtras().getParcelable("pic");
        wvPic.loadUrl(picture.getPicUrl());
        WebSettings setting = wvPic.getSettings();
        setting.setJavaScriptEnabled(true);
        wvPic.setWebViewClient(new WebViewClient());
        wvPic.setWebChromeClient(new WebChromeClient());

    }
}
