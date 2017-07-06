package com.zqw.widely.view.fragments;


import android.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zqw.widely.BaseApplication;
import com.zqw.widely.R;
import com.zqw.widely.adapter.NewsAdapter;
import com.zqw.widely.bean.Collection;
import com.zqw.widely.bean.News;
import com.zqw.widely.bean.Users;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.ServiceConfig;
import com.zqw.widely.util.LoginUtils;
import com.zqw.widely.util.ToastUtils;
import com.zqw.widely.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;

import static android.R.attr.type;


public class NewsFragment extends Fragment {
    @Bind(R.id.list_refresh)
    PullToRefreshListView listRefresh;
    private List<News> data = new ArrayList<>();
    private NewsAdapter newAdapter;
    private int page = 1;
    private View view;
    public static final int TYPE_REFRESH = 0X01;
    public static final int TYPE_LOADMORE = 0X02;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        getSyncData(1, TYPE_REFRESH);
    }

    private void initViews() {
        newAdapter = new NewsAdapter(data);
        listRefresh.setAdapter(newAdapter);
        listRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news", data.get(i));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        listRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        listRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getSyncData(1, TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getSyncData(page++, TYPE_LOADMORE);
            }
        });
    }

    private void getSyncData(int i, int typeRefresh) {
        OkHttpUtils
                .get()
                .url(ServiceConfig.NEWS＿URL)
                .addParams("key", Common.API_NEWS_KEY)
                .addParams("type", "top")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败！", Toast.LENGTH_SHORT).show();
                        listRefresh.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        listRefresh.onRefreshComplete();
                        //把返回的结果json数据字符串转换成json对象
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        //获取data对应的数组数据
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                        //解析json数组为list对象
                        data.addAll(JSONArray.parseArray(jsonArray.toString(), News.class));
                        switch (type) {
                            case TYPE_REFRESH:
                                newAdapter.setNewData(data);
                                break;
                            case TYPE_LOADMORE:
                                newAdapter.setMoreData(data);
                                break;
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEvent();
    }

    private void setEvent() {
        newAdapter = new NewsAdapter(data);
        listRefresh.setAdapter(newAdapter);
        listRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news", data.get(i - 1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        listRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("收藏")
                        .setMessage("是否收藏")
                        .setPositiveButton("收藏", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoginUtils.checkLogin(true);
                                Users users = BmobUser.getCurrentUser(BaseApplication.getInstance(),Users.class);
                                if(users != null){
                                    Collection collection = new Collection();
                                    collection.setuId(users.getObjectId());
                                    collection.setType(Common.COLLECTION_TYPE_NEWS);
                                    collection.setTitle(data.get(i).getTitle());
                                    if(data.size() > 0){
                                        collection.setPicUrl(data.get(0).getUrl());
                                    }
                                    collection.setUrl(data.get(i).getUrl());
                                    saveCollectionData(collection);
                                }

                            }
                        })
                        .setNegativeButton("取消",null)
                        .create()
                        .show();
            }
        });
    }

    private void saveCollectionData(Collection collection) {
        collection.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.shortToast(getActivity(),"收藏成功!");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(getActivity(),"收藏失败!");
            }
        });
    }
    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
