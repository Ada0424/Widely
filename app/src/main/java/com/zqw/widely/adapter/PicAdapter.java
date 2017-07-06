package com.zqw.widely.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zqw.widely.R;
import com.zqw.widely.bean.News;
import com.zqw.widely.bean.Picture;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class PicAdapter extends BaseAdapter {
    private List<Picture> data;

    private ViewHolder holder;


    public PicAdapter(List<Picture> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int positon) {
        return data.get(positon);
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int positon, View converView, ViewGroup parent) {
        if (converView == null) {
            converView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, null);
            holder = new ViewHolder(converView);
            converView.setTag(holder);

        } else {
            holder = (ViewHolder) converView.getTag();
        }
        //加载网络图片
        Glide.with(parent.getContext())
                .load(data.get(positon).getPicUrl())
                .placeholder(R.mipmap.picture)
                .error(R.mipmap.picture)
                .into(holder.img_news);

        holder.tv_title.setText(data.get(positon).getTitle());
        return converView;
    }

    class ViewHolder {
        ImageView img_news;
        TextView tv_title;

        public ViewHolder(View converView) {
            img_news = (ImageView) converView.findViewById(R.id.img_news);
            tv_title = (TextView) converView.findViewById(R.id.tv_title);
        }
    }

    //下拉刷新第一页，先清空数据再从新添加数据，然后更新UI
    public void setNewData(List<Picture> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();

    }

    //加载更多，加载下一页数据那么请求page++,只需要把新的数据加到原数据的后面，然后更新UI
    public void setMoreData(List<Picture> newData) {
        data.addAll(newData);
        notifyDataSetChanged();

    }
}
