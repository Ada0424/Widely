package com.zqw.widely.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/5/17.
 */
public class GlideLoader implements com.yancy.imageselector.ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                .centerCrop()
                .into(imageView);
    }

}
