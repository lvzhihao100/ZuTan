package com.gamerole.rxcode.demo.box.ItemTouchHelper.CallBack;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;

/**
 * @author吕志豪 .
 * @date 17-12-2  上午9:56.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class RenRenCallback extends ItemTouchHelper.SimpleCallback {
    private SlimAdapterEx slimAdapterEx;
    private RecyclerView recyclerView;
    private static final float MINSCALE = 0.6f;
    private static final float TOPGAP = 30;
    private int MAX_SHOW_COUNT = 6;


    public RenRenCallback(RecyclerView recyclerView, SlimAdapterEx slimAdapterEx) {
        super(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.slimAdapterEx = slimAdapterEx;
        this.recyclerView = recyclerView;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//★实现循环的要点
        System.out.println("swiped");
        int layoutPosition = viewHolder.getLayoutPosition();
        Object remove = slimAdapterEx.getData().remove(layoutPosition);
        slimAdapterEx.getData().add(0, remove);
        slimAdapterEx.notifyItemRemoved(layoutPosition);
        slimAdapterEx.notifyItemInserted(0);

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        System.out.println("RenRenCallback->onChildDraw");
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int initCount = recyclerView.getChildCount();
        float scaleGap = (1 - MINSCALE) / (initCount - 1);
        for (int i = 0; i < initCount; i++) {
            View child = recyclerView.getChildAt(i);
            child.setScaleX(1 - (initCount - i - 1) * scaleGap);
            child.setScaleY(1 - (initCount - i - 1) * scaleGap);
            child.setTranslationX((dX / (initCount - 1)) * i);
            int level = initCount - i - 1;
            if (level >= 0) {
                int originH = child.getHeight();
                float realTop = (originH * (level * scaleGap) / 2);
                float desTop = (initCount - level) * TOPGAP;
                float moveUp = realTop - desTop;
                child.setTranslationY(-moveUp + (dY / (initCount - 1)) * i);
            }
        }
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
       /* System.out.println("RenRenCallback->clearView");
        //对每个ChildView进行缩放 位移
        int initCount = recyclerView.getChildCount();
        float scaleGap = (1 - MINSCALE) / (initCount - 1);

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);

        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            int curValue = (int) animation.getAnimatedValue();
            for (int i = 0; i < initCount; i++) {
                View child = recyclerView.getChildAt(i);
                float translationX = child.getTranslationX();
                float translationY = child.getTranslationY();
                child.setTranslationX(translationX * (100 - curValue));
                int level = initCount - i - 1;
                if (level >= 0) {
                    int originH = child.getHeight();
                    float realTop = (originH * (level * scaleGap) / 2);
                    float desTop = (initCount - level) * TOPGAP;
                    float moveUp = realTop - desTop;
                    child.setTranslationY(-moveUp + (translationY + moveUp) * (100 - curValue));
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                RenRenCallback.super.clearView(recyclerView, viewHolder);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(200);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
*/
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        System.out.println("RenRenCallback->onSelectedChanged");
        super.onSelectedChanged(viewHolder, actionState);
    }
}
