package com.gamerole.zutan.box;

import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eqdd.databind.percent.WindowUtil;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author吕志豪 .
 * @date 17-12-25  下午5:09.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class PagerMapSnapHelper extends PagerSnapHelper {
    private OnPosChange onPosChange;
    private int curPos;
    private int gap=500;

    public void setOnPosChange(OnPosChange onPosChange) {
        this.onPosChange = onPosChange;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int childCount = recyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = recyclerView.getChildAt(i);

                    int left = (childAt.getLeft() + childAt.getRight()) >> 1;
                    if (left <= WindowUtil.csw / 2) {
                        childAt.setTranslationY((WindowUtil.csw / 2 - left) * gap / WindowUtil.csw / 2);
                    } else if (left >= WindowUtil.csw / 2/* && left <= WindowUtil.csw / 2 * 2*/) {
                        childAt.setTranslationY((left - WindowUtil.csw / 2) * gap / WindowUtil.csw / 2);
                    }/* else {
                        childAt.setTranslationY(gap);
                    }*/
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    if (onPosChange != null) {
                        int childCount = recyclerView.getChildCount();
                        int minLenth = WindowUtil.csw;
                        int centerPos;
                        for (int i = 0; i < childCount; i++) {
                            View childAt = recyclerView.getChildAt(i);

                            int left = childAt.getLeft();
                            int right = childAt.getRight();
                            if (left <= WindowUtil.csw / 2 && right >= WindowUtil.csw / 2) {
                                centerPos = recyclerView.getChildAdapterPosition(childAt);
                                changePos(centerPos);
                                break;
                            } else {
                                if (right < WindowUtil.csw / 2) {
                                    minLenth = WindowUtil.csw / 2 - right;
                                } else if (left > WindowUtil.csw / 2) {
                                    if (left - WindowUtil.csw / 2 < minLenth) {
                                        centerPos = recyclerView.getChildAdapterPosition(childAt);
                                        changePos(centerPos);
                                        break;
                                    } else {
                                        centerPos = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i - 1));
                                        changePos(centerPos);
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    private void changePos(int centerPos) {
        if (curPos != centerPos) {
            curPos = centerPos;
            onPosChange.change(centerPos);
        }
    }

    public interface OnPosChange {
        void change(int curPos);
    }
}
