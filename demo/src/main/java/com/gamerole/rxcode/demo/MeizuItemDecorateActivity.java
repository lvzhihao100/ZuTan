package com.gamerole.rxcode.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.databind.percent.WindowUtil;
import com.gamerole.rxcode.demo.box.ItemDecorate.PinHeadItemDecoration;
import com.gamerole.rxcode.demo.box.ItemDrawer.MeizuDecorationDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class MeizuItemDecorateActivity extends AppCompatActivity {

    private MeizuItemDecorateActivityCustom dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.demo_activity_meizu_item_decorate);
        PinHeadItemDecoration floatingItemDecoration = new PinHeadItemDecoration();
        floatingItemDecoration.setDecorationDrawer(new MeizuDecorationDrawer());
        dataBinding.recyclerView.addItemDecoration(floatingItemDecoration);
        floatingItemDecoration
                .textSize(30 * WindowUtil.csw / WindowUtil.width)
                .titleHeight(40 * WindowUtil.csw / WindowUtil.width);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapterEx slimAdapterEx = SlimAdapterEx.create().register(R.layout.list_item_tantan, new SlimInjector<String>() {
            @Override
            public void onInject(String data, IViewInjector injector) {

            }
        }).attachTo(dataBinding.recyclerView);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add(i + "");
        }
        HashMap<Integer, String> keys = new HashMap<>();
        keys.put(0, "王");
        keys.put(2, "张");
        keys.put(4, "白");
        keys.put(6, "吕");
        keys.put(8, "赵");
        keys.put(10, "爱");
        keys.put(18, "天");
        keys.put(19, "佳");
        keys.put(20, "期");
        keys.put(22, "如");
        keys.put(25, "梦");
        floatingItemDecoration.setKeys(keys);
        floatingItemDecoration.setPush(true);
        slimAdapterEx.updateData(strings);


    }
}
