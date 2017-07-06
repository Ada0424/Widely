package com.zqw.widely.view.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zqw.widely.R;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.PreferencesManager;
import com.zqw.widely.util.ImageLoader;
import com.zqw.widely.util.ShareUtil;
import com.zqw.widely.view.activity.LoginActivity;
import com.zqw.widely.view.activity.SetActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonFragment extends Fragment {


    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.iv_tx)
    RoundedImageView ivTx;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.btn_load)
    Button btnLoad;
    @Bind(R.id.tv_his)
    TextView tvHis;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.tv_share)
    TextView tvShare;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_load, R.id.tv_his, R.id.tv_set, R.id.tv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_his:
                break;
            case R.id.tv_set:
                startActivity(intent = new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.tv_share:
                ShareUtil.showShare(
                        getActivity(),
                        "路飞",
                        "love you",
                        "http://img4.imgtn.bdimg.com/it/u=3989485496,1571340750&fm=23&gp=0.jpg",
                        "https://www.baidu.com");
                break;
            case R.id.btn_load:
                startActivity(intent = new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }


    protected void initData() {
        if (PreferencesManager.getInstance(getActivity()).get(Common.IS_LOGIN, false)) {
            ivTx.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.VISIBLE);
            loadUserInfo();
        } else {
            btnLoad.setVisibility(View.VISIBLE);
        }
    }

    //已经登录的话重新获取用户信息
    private void loadUserInfo() {
        String userPhoto = PreferencesManager.getInstance(getActivity()).get(Common.USER_PHOTO);
        String userName = PreferencesManager.getInstance(getActivity()).get(Common.USER_NAME);
        tvName.setText(userName);
        ImageLoader.getInstance().displayImageTarget(ivTx, userPhoto);
    }
}