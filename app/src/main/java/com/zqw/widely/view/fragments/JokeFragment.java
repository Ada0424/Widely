package com.zqw.widely.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zqw.widely.R;
import com.zqw.widely.adapter.JokeAdapter;
import com.zqw.widely.bean.ResultJoke;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.ServiceConfig;
import com.zqw.widely.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


public class JokeFragment extends Fragment {
    public static final int TYPE_REFRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;
    @Bind(R.id.list_refresh)
    PullToRefreshListView listRefresh;
    private JokeAdapter jokeAdapter;
    private int page = 1;
    private List<ResultJoke.ResultBean.Joke> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        //设置列表的刷新加载
        listRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化适配器
        jokeAdapter = new JokeAdapter(data);
        //绑定适配器
        listRefresh.setAdapter(jokeAdapter);
        //添加监听事件
        listRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position - 1).getContent(), Toast.LENGTH_SHORT).show();
            }
        });

        listRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getAsynData(page, TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getAsynData(page++, TYPE_LOADMORE);
            }
        });
    }

    private void initData() {
        getAsynData(1, TYPE_REFRESH);
    }

    private void getAsynData(int page, final int type) {
        //请求数据
        OkHttpUtils
                .get()
                .url(ServiceConfig.JOKE＿URL)
                .addParams("sort", "desc")
                .addParams("page", String.valueOf(page))
                .addParams("pagesize", ServiceConfig.PAGE_SIZE)
                .addParams("time", TimeUtils.getTime())
                .addParams("key", Common.JOHE_KEY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        listRefresh.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        listRefresh.onRefreshComplete();
                        //解析数据
                        ResultJoke resultJoke = JSON.parseObject(response, ResultJoke.class);
                        switch (type) {
                            case TYPE_REFRESH:
                                jokeAdapter.setNewData(resultJoke.getResult().getData());
                                break;
                            case TYPE_LOADMORE:
                                jokeAdapter.setMoreData(resultJoke.getResult().getData());
                                break;
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}