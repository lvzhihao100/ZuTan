package com.gamerole.rxcode.demo.box.LayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author吕志豪 .
 * @date 17-12-2  上午8:43.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager {

    private static final float MINSCALE = 0.6f;
    private static final float TOPGAP = 30;
    private int MAX_SHOW_COUNT = 6;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        int initCount;
        if (itemCount >= MAX_SHOW_COUNT) {
            initCount = MAX_SHOW_COUNT;
        } else {
            initCount = itemCount;
        }
        float scaleGap = (1 - MINSCALE) / (initCount-1);
        //从可见的最底层View开始layout，依次层叠上去
        for (int position = itemCount - initCount; position < itemCount; position++) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            //我们在布局时，将childView居中处理，这里也可以改为只水平居中
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
            int originH = getDecoratedMeasuredHeight(view);
            int level = itemCount - position - 1;
            //除了顶层不需要缩小和位移
            if (level >= 0 /*&& level < mShowCount - 1*/) {
                //每一层都需要X方向的缩小
                view.setScaleX(1 - level * scaleGap);
                view.setScaleY(1 - level * scaleGap);
                float realTop = (originH * (level * scaleGap) / 2);
                float desTop = (initCount - level) * TOPGAP;
                float moveUp = realTop - desTop;
                view.setTranslationY(-moveUp);
            }
        }
    }

}
