package com.eqdd.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by XY on 2016/9/14.
 */
public class BGABannerAdapter implements BGABanner.Adapter {
    private Context context;

    public BGABannerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        Picasso with = Picasso.with(context);
        if (model instanceof String) {
            with.load((String) model)
                    .into((ImageView) view);
        } else if (model instanceof Integer) {
            with.load((int) model)
                    .into((ImageView) view);
        }
    }

}
