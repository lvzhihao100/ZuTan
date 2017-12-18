package com.gamerole.rxcode.demo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eqdd.common.adapter.MyFragmentPagerAdapter;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.databind.percent.WindowUtil;
import com.eqdd.library.ListItem8Custom;
import com.gamerole.rxcode.demo.box.ItemAnimator.MyItemAnimator;
import com.gamerole.rxcode.demo.box.ItemTouchHelper.CallBack.RenRenCallback;
import com.gamerole.rxcode.demo.box.LayoutManager.OverLayCardLayoutManager;
import com.gamerole.rxcode.demo.box.PageTransformer.CustomTransformer;

import java.util.ArrayList;
import java.util.HashMap;

import ch.ielse.view.imagewatcher.ImageWatcher;

public class MeiZuThemeActivity extends AppCompatActivity {

    DemoMeizuActivityCustom dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<Integer, String> imgUrls = new HashMap<>();
        imgUrls.put(0, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=16f6ff0030292df583cea456d4583615/e1fe9925bc315c60b6b051c087b1cb13495477f3.jpg");
        imgUrls.put(1, "http://pic21.photophoto.cn/20111011/0006019003288114_b.jpg");
        imgUrls.put(2, "http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg");
        imgUrls.put(3, "http://img1.3lian.com/2015/a1/91/d/240.jpg");
        imgUrls.put(4, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=407b2e95de3f8794c7f2406dba726481/a5c27d1ed21b0ef4c9eb5573d7c451da81cb3e98.jpg");
        imgUrls.put(5, "http://pic35.photophoto.cn/20150528/0005018358146030_b.jpg");
        imgUrls.put(6, "http://f2.topitme.com/2/de/e3/113167875369ae3de2o.jpg");
        imgUrls.put(7, "http://f8.topitme.com/8/25/80/1125177570eea80258o.jpg");
        dataBinding = DataBindingUtil.setContentView(this, R.layout.demo_activity_meizu_theme);


        dataBinding.recyclerView.setItemAnimator(new MyItemAnimator() {
        });
        dataBinding.recyclerView.getItemAnimator().setRemoveDuration(2000);
        dataBinding.recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        SlimAdapterEx slimAdapterEx = SlimAdapterEx.create().registerDefault(R.layout.list_item_tantan, (data, injector) -> {

        }).attachTo(dataBinding.recyclerView);
        ItemTouchHelper.Callback callback = new RenRenCallback(dataBinding.recyclerView, slimAdapterEx);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(dataBinding.recyclerView);

        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            objects.add(i);
        }
        slimAdapterEx.updateData(objects);
//
//        dataBinding.bt.setOnClickListener(v -> {
//            dataBinding.recyclerView.getAdapter().notifyItemRangeRemoved(0, 5);
//        });
    }


}
