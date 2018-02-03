package com.eqdd.common.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eqdd.common.R;
import com.eqdd.common.base.App;
import com.eqdd.common.base.GlideApp;
import com.eqdd.common.box.GlideTransform.GlideRoundTransformCenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by vzhihao on 2016/7/29.
 */
public class ImageUtil {
    private static RequestOptions optionsRound;
    private static RequestOptions optionsNormal;
    private static RequestOptions optionsCircle;

    static {
        optionsRound = new RequestOptions()
                .placeholder(R.mipmap.common_holder_img)
                .error(R.mipmap.common_error_img)
                .transform(new GlideRoundTransformCenterCrop(20));
    }

    static {
        optionsCircle = new RequestOptions()
                .placeholder(R.mipmap.common_holder_img)
                .error(R.mipmap.common_error_img)
                .transform(new CircleCrop());
    }


    static {
        optionsNormal = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.common_holder_img)
                .error(R.mipmap.common_error_img);
    }


    public static void setCircleImage(Object url, ImageView imageView) {
        GlideApp.with(App.INSTANCE).load(url).apply(optionsCircle).into(imageView);
    }

    public static void setCircleImageReady(Object url,DrawableBack drawableBack) {
        GlideApp.with(App.INSTANCE).load(url).apply(optionsCircle).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                drawableBack.back(resource);
            }
        });
    }

    public static void setImage(Object url, ImageView imageView) {
        GlideApp.with(App.INSTANCE).load(url).apply(optionsNormal).into(imageView);

    }

    public static void setRoundImage(Object url, ImageView imageView) {
        GlideApp.with(App.INSTANCE).load(url).apply(optionsRound).into(imageView);
    }

    public static void flurImage(Object url, ImageView imageView, int radius) {
        GlideApp.with(App.INSTANCE).load(url)
                .apply(bitmapTransform(new BlurTransformation(radius)))
                .into(imageView);
    }

    public static void bitmap(Object url, BitmapBack bitmapBack) {
        GlideApp.with(App.INSTANCE)
                .asBitmap()
                .load(url)
                .apply(optionsCircle)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        bitmapBack.back(resource);
                    }
                });
    }

    public interface BitmapBack {
        void back(Bitmap bitmap);
    }
    public interface DrawableBack {
        void back(Drawable drawable);
    }
}
