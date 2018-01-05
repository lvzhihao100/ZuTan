package com.eqdd.common.box.ItemDecorate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;


import com.eqdd.common.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionDividerLineItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint mSectionPaint;
    //分割线画笔
    private Paint mDividerPaint;

    Map<Integer, String> maps = new HashMap<Integer, String>();
    //每一块级所占的条目数
    private List<Integer> sections = new ArrayList<>();
    //头部高度
    private int headHeight = 0;
    //块级间隔高度
    private int sectionHeight = DensityUtil.percentW(50);
    //分割线高度
    private int dividerHeight;
    //分割线距离左边距离
    private int leftDividerPadding = 100;
    //分割线距离右边距离
    private int rightDividerPadding = 100;
    //最后一个条目下边距
    private int lastBottomHeight = 0;
    //分割块距离左边距离
    private int leftSectionPadding = 0;
    //分割块距离右边距离
    private int rightSectionPadding = 0;
    private int dividerColor = 0xffcccccc;
    private int sectionColor = 0xffecedf2;


    public SectionDividerLineItemDecoration setHeadHeight(int headHeight) {
        this.headHeight = headHeight;
        return this;
    }

    public SectionDividerLineItemDecoration setLeftDividerPadding(int leftDividerPadding) {
        this.leftDividerPadding = leftDividerPadding;
        return this;
    }

    public SectionDividerLineItemDecoration setRightDividerPadding(int rightDividerPadding) {
        this.rightDividerPadding = rightDividerPadding;
        return this;
    }

    public SectionDividerLineItemDecoration setLastBottomHeight(int lastBottomHeight) {
        this.lastBottomHeight = lastBottomHeight;
        return this;
    }

    public SectionDividerLineItemDecoration setSectionHeight(int sectionHeight) {
        this.sectionHeight = sectionHeight;
        return this;
    }

    public SectionDividerLineItemDecoration setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        return this;
    }

    public SectionDividerLineItemDecoration setLeftSectionPadding(int leftSectionPadding) {
        this.leftSectionPadding = leftSectionPadding;
        return this;
    }

    public SectionDividerLineItemDecoration setRightSectionPadding(int rightSectionPadding) {
        this.rightSectionPadding = rightSectionPadding;
        return this;
    }

    public SectionDividerLineItemDecoration setSectionColor(int sectionColor) {
        this.sectionColor = sectionColor;
        return this;
    }

    public SectionDividerLineItemDecoration(Context context, int... sections) {
        int num = 0;
        for (int section : sections) {
            this.sections.add(num += section);
        }
        mDividerPaint = new Paint();
        mDividerPaint.setColor(dividerColor);
        mSectionPaint = new Paint();
        mSectionPaint.setStyle(Paint.Style.FILL);
        mSectionPaint.setColor(sectionColor);
    }


    public SectionDividerLineItemDecoration setDividerColor(int dividerColor) {
        mDividerPaint.setColor(dividerColor);
        return this;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            int top = 0;
            int bottom = 0;
            if (position == 0) {
                top -= headHeight;
                c.drawRect(left + leftSectionPadding,view.getTop()+top,right - rightSectionPadding,view.getTop(),mSectionPaint);
            }
            if (sections.contains(position)) {
                top -= sectionHeight;
                c.drawRect(left + leftSectionPadding,view.getTop()+top,right - rightSectionPadding,view.getTop(),mSectionPaint);

            }
            if (!sections.contains(position + 1) && parent.getAdapter().getItemCount() - 1 != position) {
                bottom += dividerHeight;
                c.drawLine(left + leftDividerPadding, view.getBottom() , right - rightDividerPadding, view.getBottom(), mDividerPaint);
            }
            if (parent.getAdapter().getItemCount() - 1 == position) {
                bottom += lastBottomHeight;
                c.drawRect(left + leftSectionPadding,view.getBottom(),right - rightSectionPadding,view.getBottom()+bottom,mSectionPaint);

            }
        }
    }

    /**
     * 设置item分割线的size
     *
     * @param outRect outRect
     * @param view    view
     * @param parent  parent
     * @param state   state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        int top = 0;
        int bottom = 0;
        if (position == 0) {
            top -= headHeight;
        }
        if (sections.contains(position)) {
            top -= sectionHeight;
        }
        if (!sections.contains(position + 1) && parent.getAdapter().getItemCount() - 1 != position) {
            bottom += dividerHeight;
        }
        if (parent.getAdapter().getItemCount() - 1 == position) {
            bottom += lastBottomHeight;
        }
        outRect.set(0, Math.abs(top), 0, bottom);
    }
}