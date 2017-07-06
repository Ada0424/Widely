package com.zqw.widely.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zqw.widely.R;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ImageLoader {
    private static ImageLoader instance;

    public static ImageLoader getInstance(){
        if(instance == null){
            synchronized (ImageLoader.class){
                if(instance == null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }
    /**
     * 加载图片 Target
     *
     * @param imageView
     * @param url
     */
    public void displayImageTarget(final ImageView imageView, final String
            url) {
        Glide.get(imageView.getContext()).with(imageView.getContext())
                .load(url)
                .asBitmap()//强制转换Bitmap
                .placeholder(R.mipmap.picture)
                .error(R.mipmap.picture)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(getTarget(imageView,url));
    }


    /**
     * 获取BitmapImageViewTarget
     */
    private BitmapImageViewTarget getTarget(ImageView imageView, final String url) {
        return new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                //缓存Bitmap，以便于在没有用到时，自动回收

            }
        };
    }
}
