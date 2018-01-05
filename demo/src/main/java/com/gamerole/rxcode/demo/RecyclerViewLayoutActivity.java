package com.gamerole.rxcode.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimAdapterExReverse;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.gamerole.rxcode.demo.box.LayoutManager.LearnLayoutManager;
import com.gamerole.rxcode.demo.box.LayoutManager.MapLayoutManager;
import com.gamerole.rxcode.demo.box.LayoutManager.VegaLayoutManager;
import com.gamerole.rxcode.demo.box.SnapHelper.PagerMapSnapHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewLayoutActivity extends AppCompatActivity {

    RecyclerViewMapActivityCustom dataBinding;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.demo_activity_recyclerview);
        HashMap<Integer, String> imgUrls = new HashMap<>();
        imgUrls.put(0, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=16f6ff0030292df583cea456d4583615/e1fe9925bc315c60b6b051c087b1cb13495477f3.jpg");
        imgUrls.put(1, "http://pic21.photophoto.cn/20111011/0006019003288114_b.jpg");
        imgUrls.put(2, "http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg");
        imgUrls.put(3, "http://img1.3lian.com/2015/a1/91/d/240.jpg");
        imgUrls.put(4, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=407b2e95de3f8794c7f2406dba726481/a5c27d1ed21b0ef4c9eb5573d7c451da81cb3e98.jpg");
        imgUrls.put(5, "http://pic35.photophoto.cn/20150528/0005018358146030_b.jpg");
        imgUrls.put(6, "http://f2.topitme.com/2/de/e3/113167875369ae3de2o.jpg");
        imgUrls.put(7, "http://f8.topitme.com/8/25/80/1125177570eea80258o.jpg");
        imgUrls.put(8, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=16f6ff0030292df583cea456d4583615/e1fe9925bc315c60b6b051c087b1cb13495477f3.jpg");
        imgUrls.put(9, "http://pic21.photophoto.cn/20111011/0006019003288114_b.jpg");
        imgUrls.put(10, "http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg");
        imgUrls.put(11, "http://img1.3lian.com/2015/a1/91/d/240.jpg");
        imgUrls.put(12, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=407b2e95de3f8794c7f2406dba726481/a5c27d1ed21b0ef4c9eb5573d7c451da81cb3e98.jpg");
        imgUrls.put(13, "http://pic35.photophoto.cn/20150528/0005018358146030_b.jpg");
        imgUrls.put(14, "http://f2.topitme.com/2/de/e3/113167875369ae3de2o.jpg");
        imgUrls.put(15, "http://f8.topitme.com/8/25/80/1125177570eea80258o.jpg");
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < imgUrls.size(); i++) {
            strings.add(imgUrls.get(i));
        }
        dataBinding.recyclerView.setLayoutManager(new VegaLayoutManager());
        SlimAdapterEx slimAdapterEx = SlimAdapterEx.create().register(R.layout.list_item_tantan, new SlimInjector<String>() {
            @Override
            public void onInject(String data, IViewInjector injector) {
                System.out.println(injector.pos());
                injector.image(R.id.imageView, data);
            }
        }).attachTo(dataBinding.recyclerView).updateData(strings);

    }


}
