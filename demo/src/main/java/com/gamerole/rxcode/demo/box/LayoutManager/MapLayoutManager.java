package com.gamerole.rxcode.demo.box.LayoutManager;

/**
 * @author吕志豪 .
 * @date 17-12-27  上午11:06.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

import android.graphics.Rect;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.gamerole.rxcode.demo.box.SnapHelper.StartSnapHelper;

import java.util.ArrayList;

/**
 * Created by xmuSistone on 2017/9/20.
 */
public class MapLayoutManager extends RecyclerView.LayoutManager {

    private int scroll = 0;
    private SparseArray<Rect> locationRects = new SparseArray<>();
    private SparseBooleanArray attachedItems = new SparseBooleanArray();
    private ArrayMap<Integer, Integer> viewTypeHeightMap = new ArrayMap<>();
    private ArrayMap<Integer, Integer> viewTypeWidthMap = new ArrayMap<>();

    private boolean needSnap = false;
    private int lastDy = 0;
    private int maxScroll = 10000;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Recycler recycler;
    private int space = 200;
    private int lastdx;

    public MapLayoutManager() {
        setAutoMeasureEnabled(true);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        super.onAdapterChanged(oldAdapter, newAdapter);
        this.adapter = newAdapter;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.recycler = recycler; // 二话不说，先把recycler保存了
        if (state.isPreLayout()) {
            return;
        }

        buildLocationRects();

        // 先回收放到缓存，后面会再次统一layout
        detachAndScrapAttachedViews(recycler);
        layoutItemsOnCreate(recycler);
    }

    private void buildLocationRects() {
        locationRects.clear();
        attachedItems.clear();

        int tempPosition = getPaddingTop();
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            // 1. 先计算出itemWidth和itemHeight
            int viewType = adapter.getItemViewType(i);
            int itemHeight;
            int itemWidth;
            if (viewTypeHeightMap.containsKey(viewType)) {
                itemHeight = viewTypeHeightMap.get(viewType);
                itemWidth = viewTypeWidthMap.get(viewType);
            } else {
                View itemView = recycler.getViewForPosition(i);
                addView(itemView);
                measureChildWithMargins(itemView, View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                itemHeight = getDecoratedMeasuredHeight(itemView);
                itemWidth = getDecoratedMeasuredWidth(itemView);
                viewTypeHeightMap.put(viewType, itemHeight);
                viewTypeWidthMap.put(viewType, itemWidth);
            }

            Rect rect = new Rect();
            if (i == 0) {
                rect.left = (getWidth() - itemWidth) >> 1;
                rect.top = 0;
                rect.right = (getWidth() + itemWidth) >> 1;
                rect.bottom = itemHeight;
                locationRects.put(i, rect);
                attachedItems.put(i, false);
                tempPosition = rect.right + space;
            } else {
                rect.left = tempPosition;
                rect.top = 0;
                rect.right = tempPosition + itemWidth;
                rect.bottom = itemHeight;
                locationRects.put(i, rect);
                attachedItems.put(i, false);
                tempPosition = rect.right + space;
            }
        }
        maxScroll = locationRects.get(locationRects.size() - 1).right - getWidth();
        System.out.println("maxScroll" + maxScroll);
//        if (itemCount == 0) {
//            maxScroll = 0;
//        } else {
//            computeMaxScroll();
//        }
    }

    /**
     * 对外提供接口，找到第一个可视view的index
     */
//    public int findFirstVisibleItemPosition() {
//        int count = locationRects.size();
//        Rect displayRect = new Rect(0, scroll, getWidth(), getHeight() + scroll);
//        for (int i = 0; i < count; i++) {
//            if (Rect.intersects(displayRect, locationRects.get(i)) &&
//                    attachedItems.get(i)) {
//                return i;
//            }
//        }
//        return 0;
//    }

    /**
     * 计算可滑动的最大值
     */
//    private void computeMaxScroll() {
//        maxScroll = locationRects.get(locationRects.size() - 1).bottom - getHeight();
//        if (maxScroll < 0) {
//            maxScroll = 0;
//            return;
//        }
//
//        int itemCount = getItemCount();
//        int screenFilledHeight = 0;
//        for (int i = itemCount - 1; i >= 0; i--) {
//            Rect rect = locationRects.get(i);
//            screenFilledHeight = screenFilledHeight + (rect.bottom - rect.top);
//            if (screenFilledHeight > getHeight()) {
//                int extraSnapHeight = getHeight() - (screenFilledHeight - (rect.bottom - rect.top));
//                maxScroll = maxScroll + extraSnapHeight;
//                break;
//            }
//        }
//    }

    /**
     * 初始化的时候，layout子View
     */
    private void layoutItemsOnCreate(RecyclerView.Recycler recycler) {
        int itemCount = getItemCount();
        Rect displayRect = new Rect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < itemCount; i++) {
            Rect thisRect = locationRects.get(i);
            if (Rect.intersects(displayRect, thisRect)) {
                View childView = recycler.getViewForPosition(i);
                addView(childView);
                measureChildWithMargins(childView, View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                layoutItem(childView, locationRects.get(i));
                attachedItems.put(i, true);
//                childView.setPivotY(0);
//                childView.setPivotX(childView.getMeasuredWidth() / 2);
                if (thisRect.left > getWidth()) {
                    break;
                }
            }
        }
    }


    /**
     * 初始化的时候，layout子View
     */
    private void layoutItemsOnScroll() {
//        int childCount = getChildCount();
//        // 1. 已经在屏幕上显示的child
//        int itemCount = getItemCount();
//        Rect displayRect = new Rect(scroll, 0, getWidth()+scroll, getHeight());
//        int firstVisiblePosition = -1;
//        int lastVisiblePosition = -1;
//        for (int i = childCount - 1; i >= 0; i--) {
//            View child = getChildAt(i);
//            if (child == null) {
//                continue;
//            }
//            int position = getPosition(child);
//            if (!Rect.intersects(displayRect, locationRects.get(position))) {
//                // 回收滑出屏幕的View
//                removeAndRecycleView(child, recycler);
//                attachedItems.put(position, false);
//            } else {
//                // Item还在显示区域内，更新滑动后Item的位置
//                if (lastVisiblePosition < 0) {
//                    lastVisiblePosition = position;
//                }
//
//                if (firstVisiblePosition < 0) {
//                    firstVisiblePosition = position;
//                } else {
//                    firstVisiblePosition = Math.min(firstVisiblePosition, position);
//                }
//
//                layoutItem(child, locationRects.get(position)); //更新Item位置
//            }
//        }
//
//        // 2. 复用View处理
//        if (firstVisiblePosition > 0) {
//            // 往前搜索复用
//            for (int i = firstVisiblePosition - 1; i >= 0; i--) {
//                if (Rect.intersects(displayRect, locationRects.get(i)) &&
//                        !attachedItems.get(i)) {
//                    reuseItemOnSroll(i, true);
//                } else {
//                    break;
//                }
//            }
//        }
//        // 往后搜索复用
//        for (int i = lastVisiblePosition + 1; i < itemCount; i++) {
//            if (Rect.intersects(displayRect, locationRects.get(i)) &&
//                    !attachedItems.get(i)) {
//                reuseItemOnSroll(i, false);
//            } else {
//                break;
//            }
//        }
        int childCount = getChildCount();
        int itemCount = getItemCount();
        int firstPos = -1;
        int lastPos = -1;
        ArrayList<Integer> integers = new ArrayList<>();
        Rect displayRect = new Rect(scroll, 0, getWidth() + scroll, getHeight());
        for (int i = 0; i < itemCount; i++) {
            if (Rect.intersects(displayRect, locationRects.get(i))) {
                integers.add(i);
                if (childCount > 0) {
                    if (firstPos == -1) {
                        View childAt = getChildAt(0);
                        if (Rect.intersects(locationRects.get(i), new Rect(childAt.getLeft() + scroll, 0, childAt.getRight() + scroll, childAt.getBottom()))) {
                            firstPos = i;
                        }
                    }
                    if (lastPos == -1) {
                        View childAt = getChildAt(childCount - 1);
                        if (Rect.intersects(locationRects.get(i), new Rect(childAt.getLeft() + scroll, 0, childAt.getRight() + scroll, childAt.getBottom()))) {
                            lastPos = i;
                            break;
                        }
                    }
                }
            }
        }
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getLeft() > getWidth() || child.getRight() < 0) {
                removeAndRecycleView(child, recycler);
            }else {
                if (firstPos+i>0) {
                    layoutItem(child, locationRects.get(firstPos + i)); //更新Item位置
                }
            }
        }
        for (int i = integers.get(0); i < integers.get(integers.size()-1); i++) {
            if (i<firstPos){
                reuseItemOnSroll(i,true);
            }else if (i<lastPos){
                reuseItemOnSroll(i,false);
            }
        }



    }

    /**
     * 复用position对应的View
     */
    private void reuseItemOnSroll(int position, boolean addViewFromTop) {
        View scrap = recycler.getViewForPosition(position);
        measureChildWithMargins(scrap, 0, 0);
//        scrap.setPivotY(0);
//        scrap.setPivotX(scrap.getMeasuredWidth() / 2);

        if (addViewFromTop) {
            addView(scrap, 0);
        } else {
            addView(scrap);
        }
        // 将这个Item布局出来
        layoutItem(scrap, locationRects.get(position));
        attachedItems.put(position, true);
    }

    private void layoutItem(View child, Rect rect) {
//        int topDistance = scroll - rect.top;
//        int layoutTop, layoutBottom;
//        int itemHeight = rect.bottom - rect.top;
//        if (topDistance < itemHeight && topDistance > 0) {
//            float rate1 = (float) topDistance / itemHeight;
//            float rate2 = 1 - rate1 * rate1 / 3;
//            float rate3 = 1 - rate1 * rate1;
//            child.setScaleX(rate2);
//            child.setScaleY(rate2);
//            child.setAlpha(rate3);
//            layoutTop = 0;
//            layoutBottom = itemHeight;
//        } else {
//            child.setScaleX(1);
//            child.setScaleY(1);
//            child.setAlpha(1);
//
//            layoutTop = rect.top - scroll;
//            layoutBottom = rect.bottom - scroll;
//        }
        int height = (rect.bottom - rect.top) >> 1;
        int width = (getWidth() - (rect.right - rect.left)) >> 1;
        int offHeight = Math.abs((rect.left - scroll + rect.right - scroll - getWidth()) >> 1) * height / width;
        layoutDecorated(child, rect.left - scroll, offHeight, rect.right - scroll, (height << 1) + offHeight);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0 || dx == 0) {
            return 0;
        }
        int travel = dx;
        if (dx + scroll < 0) {
            travel = -scroll;
        } else if (dx + scroll > maxScroll) {
            travel = maxScroll - scroll;
        }
        scroll += travel; //累计偏移量
        lastdx = dx;
        if (!state.isPreLayout() && getChildCount() > 0) {
            layoutItemsOnScroll();
        }

        return travel;
    }

//
//    @Override
//    public void onAttachedToWindow(RecyclerView view) {
//        super.onAttachedToWindow(view);
//        new StartSnapHelper().attachToRecyclerView(view);
//    }
//
//    @Override
//    public void onScrollStateChanged(int state) {
//        if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
//            needSnap = true;
//        }
//        super.onScrollStateChanged(state);
//    }
//
//    public int getSnapHeight() {
//        if (!needSnap) {
//            return 0;
//        }
//        needSnap = false;
//
//        Rect displayRect = new Rect(0, scroll, getWidth(), getHeight() + scroll);
//        int itemCount = getItemCount();
//        for (int i = 0; i < itemCount; i++) {
//            Rect itemRect = locationRects.get(i);
//            if (displayRect.intersect(itemRect)) {
//
//                if (lastDy > 0) {
//                    // scroll变大，属于列表往下走，往下找下一个为snapView
//                    if (i < itemCount - 1) {
//                        Rect nextRect = locationRects.get(i + 1);
//                        return nextRect.top - displayRect.top;
//                    }
//                }
//                return itemRect.top - displayRect.top;
//            }
//        }
//        return 0;
//    }
//
//    public View findSnapView() {
//        if (getChildCount() > 0) {
//            return getChildAt(0);
//        }
//        return null;
//    }
}