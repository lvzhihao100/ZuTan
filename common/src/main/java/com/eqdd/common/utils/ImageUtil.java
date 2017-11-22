package com.eqdd.common.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eqdd.common.R;
import com.eqdd.common.base.App;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by vzhihao on 2016/7/29.
 */
public class ImageUtil {
    static {
        optionsCircle = new RequestOptions()
                .placeholder(R.mipmap.common_holder_img)
                .error(R.mipmap.common_error_img)
                .transform(new CircleCrop());
    }

    private static RequestOptions optionsNormal;

    static {
        optionsNormal = new RequestOptions()
                .placeholder(R.mipmap.common_holder_img)
                .error(R.mipmap.common_error_img);
    }

    private static RequestOptions optionsCircle;

    public static void setCircleImage(Object url, ImageView imageView) {
        Glide.with(App.INSTANCE).load(url).apply(optionsCircle).into(imageView);
    }

    public static void setImage(Object url, ImageView imageView) {
        Glide.with(App.INSTANCE).load(url).apply(optionsNormal).into(imageView);

    }
    public static void flurImage(Object url, ImageView imageView, int radius) {
        Glide.with(App.INSTANCE).load(url)
                .apply(bitmapTransform(new BlurTransformation(radius)))
                .into(imageView);
    }
}
