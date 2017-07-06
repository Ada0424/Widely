package com.zqw.widely.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zqw.widely.R;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SetActivity extends AppCompatActivity {


    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.tv_lo)
    TextView tvLo;
    @Bind(R.id.tv_new)
    TextView tvNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_lo, R.id.tv_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_lo:
                loginout();
                break;
            case R.id.tv_new:
                break;
        }
    }

    private void loginout() {
        BmobUser.logOut(SetActivity.this);
        PreferencesManager.getInstance(SetActivity.this).put(Common.IS_LOGIN, false);
        SetActivity.this.finish();
    }

}
