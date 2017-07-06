package com.zqw.widely.view.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zqw.widely.R;
import com.zqw.widely.adapter.PicAdapter;
import com.zqw.widely.bean.Picture;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.ServiceConfig;
import com.zqw.widely.view.activity.PicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class PicFragment extends Fragment {
    public static final int TYPE_REHRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;
    @Bind(R.id.list_refresh)
    PullToRefreshListView listRefresh;
    @Bind(R.id.ll_list)
    LinearLayout llList;
    private int page = 1;


    private List<Picture> data = new ArrayList<>();
    private PicAdapter picAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setEvent();
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    private void setEvent() {
        //设置列表的刷新加载
        listRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        picAdapter = new PicAdapter(data);


        listRefresh.setAdapter(picAdapter);
        listRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), PicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("picture", data.get(i - 1));
                intent.putExtras(bundle);
                PicFragment.this.startActivity(intent);
            }
        });
       listRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新第一页，先清空数据再从新添加数据，然后更新UI
                getAsyncData(1, TYPE_REHRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多，加载下一页数据那么请求page++,只需要把新的数据加到原数据的后面，然后更新UI
                getAsyncData(page++, TYPE_LOADMORE);

            }
        });

    }

    private void initData() {
        getAsyncData(1, TYPE_REHRESH);
    }
    //获取异步请求的数据

    private void getAsyncData(int i, final int type) {
        OkHttpUtils.get()
                .url(ServiceConfig.PIC_URL)
                .addParams("key", Common.PIC_KEY)
                .addParams("num", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        //停止刷新
                        listRefresh.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //停止刷新
                        listRefresh.onRefreshComplete();
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("newslist");
                        switch (type) {
                            case TYPE_REHRESH:
                                picAdapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(), Picture.class));
                                break;
                            case TYPE_LOADMORE:
                                picAdapter.setMoreData(JSONArray.parseArray(jsonArray.toJSONString(), Picture.class));
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
