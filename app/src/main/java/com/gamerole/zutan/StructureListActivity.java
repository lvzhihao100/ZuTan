package com.gamerole.zutan;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.User;
import com.eqdd.library.http.DialogCallBack;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.library.store.DisMoveLinearLayoutManager;
import com.gamerole.zutan.bean.NetInfoBean;
import com.jakewharton.rxbinding.view.RxView;
import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author吕志豪 .
 * @date 17-10-19  下午3:24.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_STRUCTURE_LIST)
public class StructureListActivity extends CommonFullTitleActivity {

    private StructureListActivityCustom dataBinding;
    private SlimAdapterEx<NetInfoBean.Siblings> slimAdapterEx;
    private User nowUser;
    private int nowPos;
    private boolean down;
    private boolean up;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (StructureListActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_structure_list;
    }

    @Override
    public void initData() {
        dataBinding.loopViewpager.setLayoutManager(new DisMoveLinearLayoutManager(this).setScrollEnabled(false));
        slimAdapterEx = SlimAdapterEx.create().registerDefault(R.layout.library_list_item_62, new SlimInjector<NetInfoBean.Siblings>() {
            @Override
            public void onInject(NetInfoBean.Siblings data, IViewInjector injector) {
                RecyclerViewPager recyclerViewPager = (RecyclerViewPager) injector.findViewById(R.id.loopViewpager);
                recyclerViewPager.setLayoutManager(new LinearLayoutManager(StructureListActivity.this, LinearLayoutManager.HORIZONTAL, false));
                SlimAdapterEx adapterEx = SlimAdapterEx.create().registerDefault(R.layout.library_list_item_01, new SlimInjector<User>() {
                    @Override
                    public void onInject(User data, IViewInjector injector) {
                        injector.text(R.id.tv_name, data.getName());
                    }
                }).
                        register(R.layout.library_list_item_16, new SlimInjector<Integer>() {
                            @Override
                            public void onInject(Integer data, IViewInjector injector) {

                            }
                        }).attachTo(recyclerViewPager).updateData(new ArrayList());
                recyclerViewPager.setHasFixedSize(true);
                recyclerViewPager.setLongClickable(true);
                recyclerViewPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + recyclerViewPagerPager.getFirstVisiblePosition());
                        int childCount = recyclerViewPager.getChildCount();
                        int width = recyclerViewPager.getChildAt(0).getWidth();
                        int padding = (recyclerViewPager.getWidth() - width) / 2;
//                mCountText.setText("Count: " + childCount);

                        for (int j = 0; j < childCount; j++) {
                            View v = recyclerView.getChildAt(j);
                            //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                            float rate = 0;
                            ;
                            if (v.getLeft() <= padding) {
                                if (v.getLeft() >= padding - v.getWidth()) {
                                    rate = (padding - v.getLeft()) * 1f / v.getWidth();
                                } else {
                                    rate = 1;
                                }
                                v.setScaleY(1 - rate * 0.3f);
                                v.setScaleX(1 - rate * 0.3f);

                            } else {
                                //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                                if (v.getLeft() <= recyclerView.getWidth() - padding) {
                                    rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                                }
                                v.setScaleY(0.7f + rate * 0.3f);
                                v.setScaleX(0.7f + rate * 0.3f);
                            }
                        }
                    }
                });
                recyclerViewPager.addOnPageChangedListener((oldPosition, newPosition) -> {
                    if (adapterEx.getData().get(newPosition) instanceof User) {
                        nowUser = (User) adapterEx.getData().get(newPosition);
                    }
                });
                ItemClickSupport.addTo(recyclerViewPager)
                        .setOnItemClickListener((recyclerView, position, v) -> {

                        });
                recyclerViewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        if (recyclerViewPager.getChildCount() < 3) {
                            if (recyclerViewPager.getChildAt(1) != null) {
                                if (recyclerViewPager.getCurrentPosition() == 0) {
                                    View v1 = recyclerViewPager.getChildAt(1);
                                    v1.setScaleY(0.7f);
                                    v1.setScaleX(0.7f);
                                } else {
                                    View v1 = recyclerViewPager.getChildAt(0);
                                    v1.setScaleY(0.7f);
                                    v1.setScaleX(0.7f);
                                }
                            }
                        } else {
                            if (recyclerViewPager.getChildAt(0) != null) {
                                View v0 = recyclerViewPager.getChildAt(0);
                                v0.setScaleY(0.7f);
                                v0.setScaleX(0.7f);
                            }
                            if (recyclerViewPager.getChildAt(2) != null) {
                                View v2 = recyclerViewPager.getChildAt(2);
                                v2.setScaleY(0.7f);
                                v2.setScaleX(0.7f);
                            }
                        }

                    }
                });
                ArrayList<Object> temp = new ArrayList<>();
                temp.addAll(data.getUsers());
                if (temp.size() == 0) {
                    temp.add(1);
                }
                adapterEx.updateData(temp);
                recyclerViewPager.scrollToPosition(data.getPos());

            }
        }).attachTo(dataBinding.loopViewpager).updateData(new ArrayList());
        dataBinding.loopViewpager.setTriggerOffset(0.35f);
        dataBinding.loopViewpager.setFlingFactor(0.25f);
        dataBinding.loopViewpager.setHasFixedSize(true);
        dataBinding.loopViewpager.setLongClickable(true);

        dataBinding.loopViewpager.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + dataBinding.loopViewpagerPager.getFirstVisiblePosition());
                int childCount = dataBinding.loopViewpager.getChildCount();
                int width = dataBinding.loopViewpager.getChildAt(0).getWidth();
                int padding = (dataBinding.loopViewpager.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getTop() <= padding) {
                        if (v.getTop() >= padding - v.getHeight()) {
                            rate = (padding - v.getTop()) * 1f / v.getHeight();
                        } else {
                            rate = 1;
                        }
                        v.setScaleX(1 - rate * 0.3f);
                        v.setScaleY(1 - rate * 0.3f);
                    } else {
                        //往右 从 padding 到 recyclerView.getHeight()-padding 的过程中，由大到小
                        if (v.getTop() <= recyclerView.getHeight() - padding) {
                            rate = (recyclerView.getHeight() - padding - v.getTop()) * 1f / v.getHeight();
                        }
                        v.setScaleX(0.7f + rate * 0.3f);
                        v.setScaleY(0.7f + rate * 0.3f);
                    }
                }
            }
        });

        dataBinding.loopViewpager.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (dataBinding.loopViewpager.getChildCount() < 3) {
                if (dataBinding.loopViewpager.getChildAt(1) != null) {
                    View v1 = dataBinding.loopViewpager.getChildAt(1);
                    v1.setScaleY(0.7f);
                }
            } else {
                if (dataBinding.loopViewpager.getChildAt(0) != null) {
                    View v0 = dataBinding.loopViewpager.getChildAt(0);
                    v0.setScaleY(0.7f);
                }
                if (dataBinding.loopViewpager.getChildAt(2) != null) {
                    View v2 = dataBinding.loopViewpager.getChildAt(2);
                    v2.setScaleY(0.7f);
                }
            }
        });
    }

    @Override
    public void setView() {
        RxView.clicks(dataBinding.btFather)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (up) {
                        OkGo.<HttpResult<NetInfoBean.Siblings>>post(HttpConfig.BASE_URL + HttpConfig.SIBLING_INFO + "fatherSiblingFromFather")
                                .params("id", nowUser.getId())
                                .execute(new DialogCallBack<HttpResult<NetInfoBean.Siblings>>(StructureListActivity.this) {
                                    @Override
                                    public void onSuccess(Response<HttpResult<NetInfoBean.Siblings>> response) {
                                        HttpResult<NetInfoBean.Siblings> httpResult = response.body();
                                        ToastUtil.showShort(httpResult.getMsg());
                                        if (httpResult.getStatus() == 200) {
                                            if (nowPos == 0) {
                                                slimAdapterEx.getData().add(0, httpResult.getItems());
                                                slimAdapterEx.notifyItemInserted(0);
                                                dataBinding.loopViewpager.scrollToPosition(0);
                                            } else {
                                                slimAdapterEx.getData().remove(nowPos - 1);
                                                slimAdapterEx.getData().add(nowPos - 1, httpResult.getItems());
                                                slimAdapterEx.notifyItemChanged(nowPos - 1);
                                                dataBinding.loopViewpager.scrollToPosition(nowPos - 1);
                                                nowPos--;
                                            }
                                            if (httpResult.getItems().getUsers().size() > 0) {
                                                up = true;
                                                down = true;
                                                nowUser = httpResult.getItems().getUsers().get(httpResult.getItems().getPos());
                                            } else {
                                                up = false;
                                            }
                                        }
                                    }
                                });
                    }
                });
        RxView.clicks(dataBinding.btSon)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (down) {
                        OkGo.<HttpResult<NetInfoBean.Siblings>>post(HttpConfig.BASE_URL + HttpConfig.SIBLING_INFO + "son")
                                .params("id", nowUser.getId())
                                .execute(new DialogCallBack<HttpResult<NetInfoBean.Siblings>>(StructureListActivity.this) {
                                    @Override
                                    public void onSuccess(Response<HttpResult<NetInfoBean.Siblings>> response) {
                                        HttpResult<NetInfoBean.Siblings> httpResult = response.body();
                                        ToastUtil.showShort(httpResult.getMsg());
                                        if (httpResult.getStatus() == 200) {
                                            if (nowPos == slimAdapterEx.getData().size() - 1) {
                                                slimAdapterEx.getData().add(httpResult.getItems());
                                                slimAdapterEx.notifyItemInserted(slimAdapterEx.getData().size() - 1);
                                                dataBinding.loopViewpager.scrollToPosition(slimAdapterEx.getData().size() - 1);
                                            } else {
                                                slimAdapterEx.getData().remove(nowPos + 1);
                                                slimAdapterEx.getData().add(nowPos + 1, httpResult.getItems());
                                                slimAdapterEx.notifyItemChanged(nowPos + 1);
                                                dataBinding.loopViewpager.scrollToPosition(nowPos + 1);
                                                nowPos++;
                                            }
                                            if (httpResult.getItems().getUsers().size() > 0) {
                                                down = true;
                                                up = true;
                                                nowUser = httpResult.getItems().getUsers().get(httpResult.getItems().getPos());
                                            } else {
                                                down = false;
                                            }
                                        }
                                    }
                                });
                    }
                });
        update(0, "mySiblingFromFather");
    }

    private void update(int id, String relative) {
        OkGo.<HttpResult<NetInfoBean.Siblings>>post(HttpConfig.BASE_URL + HttpConfig.SIBLING_INFO + relative)
                .params("id", id)
                .execute(new DialogCallBack<HttpResult<NetInfoBean.Siblings>>(StructureListActivity.this) {
                    @Override
                    public void onSuccess(Response<HttpResult<NetInfoBean.Siblings>> response) {
                        HttpResult<NetInfoBean.Siblings> httpResult = response.body();
                        ToastUtil.showShort(httpResult.getMsg());
                        if (httpResult.getStatus() == 200) {
                            nowUser = httpResult.getItems().getUsers().get(httpResult.getItems().getPos());
                            ArrayList<NetInfoBean.Siblings> siblings = new ArrayList<>();
                            siblings.add(httpResult.getItems());
                            slimAdapterEx.updateData(siblings);
                            nowPos = 0;
                            up = true;
                            down = true;
                        }
                    }
                });
    }
}
