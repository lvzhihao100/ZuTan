package com.eqdd.library.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.R;
import com.eqdd.library.LibraryRecyclerViewCustom;
import com.eqdd.library.base.RoutConfig;

import java.util.ArrayList;

/**
 * Created by lv on 17-9-28.
 */
@Route(path = RoutConfig.LIBRARY_LIST_ITEM)
public class ListItemActivity extends CommonFullTitleActivity {

    private LibraryRecyclerViewCustom dataBinding;
    private SlimAdapterEx slimAdapterEx;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (LibraryRecyclerViewCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview;
    }

    @Override
    public void initData() {

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapterEx = SlimAdapterEx.create()
                .register(R.layout.library_list_item_01, new SlimInjector<ListItem1>() {
                    @Override
                    public void onInject(ListItem1 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_02, new SlimInjector<ListItem2>() {
                    @Override
                    public void onInject(ListItem2 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_03, new SlimInjector<ListItem3>() {
                    @Override
                    public void onInject(ListItem3 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_04, new SlimInjector<ListItem4>() {
                    @Override
                    public void onInject(ListItem4 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_05, new SlimInjector<ListItem5>() {
                    @Override
                    public void onInject(ListItem5 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_06, new SlimInjector<ListItem6>() {
                    @Override
                    public void onInject(ListItem6 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_07, new SlimInjector<ListItem7>() {
                    @Override
                    public void onInject(ListItem7 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_08, new SlimInjector<ListItem8>() {
                    @Override
                    public void onInject(ListItem8 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_09, new SlimInjector<ListItem9>() {
                    @Override
                    public void onInject(ListItem9 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_10, new SlimInjector<ListItem10>() {
                    @Override
                    public void onInject(ListItem10 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_11, new SlimInjector<ListItem11>() {
                    @Override
                    public void onInject(ListItem11 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_12, new SlimInjector<ListItem12>() {
                    @Override
                    public void onInject(ListItem12 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_13, new SlimInjector<ListItem13>() {
                    @Override
                    public void onInject(ListItem13 data, IViewInjector injector) {

                    }
                }).register(R.layout.library_list_item_15, new SlimInjector<ListItem15>() {
                    @Override
                    public void onInject(ListItem15 data, IViewInjector injector) {

                    }
                }).registerDefault(R.layout.library_list_item_15, new SlimInjector() {
                    @Override
                    public void onInject(Object data, IViewInjector injector) {

                    }
                }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
    }

    @Override
    public void setView() {
        ArrayList<Object> data = new ArrayList<>();
        data.add(new ListItem1());
        data.add(new ListItem2());
        data.add(new ListItem3());
        data.add(new ListItem4());
        data.add(new ListItem5());
        data.add(new ListItem6());
        data.add(new ListItem7());
        data.add(new ListItem8());
        data.add(new ListItem9());
        data.add(new ListItem10());
        data.add(new ListItem11());
        data.add(new ListItem12());
        data.add(new ListItem13());
        data.add(new ListItem14());
        data.add(new ListItem15());
        slimAdapterEx.updateData(data);

    }

    class ListItem1 {
    }

    class ListItem2 {
    }

    class ListItem3 {
    }

    class ListItem4 {
    }

    class ListItem5 {
    }

    class ListItem6 {
    }

    class ListItem7 {
    }

    class ListItem8 {
    }

    class ListItem9 {
    }

    class ListItem10 {
    }

    class ListItem11 {
    }

    class ListItem12 {
    }

    class ListItem13 {
    }

    class ListItem14 {
    }

    class ListItem15 {
    }

}
