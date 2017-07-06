package com.zqw.widely.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.zqw.widely.R;
import com.zqw.widely.bean.News;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class NewsAdapter extends BaseAdapter {
    private List<News> data;
    private ViewHolder holder;

    public NewsAdapter(Context context, int item_news, ListView lv_news) {
    }

    public NewsAdapter(List<News> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
       return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(data.get(position).getThumbnail_pic_s())
                .centerCrop()
                .placeholder(R.mipmap.picture)
                .error(R.mipmap.picture)
                .crossFade()
                .into(holder.img_news);
        holder.tv_title.setText(data.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        ImageView img_news;
        TextView tv_title;

        public ViewHolder(View view) {
            img_news = (ImageView) view.findViewById(R.id.img_news);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }

    public void setNewData(List<News> list){
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }
    public void setMoreData(List<News> list){
        data.addAll(list);
        notifyDataSetChanged();
    }
}
