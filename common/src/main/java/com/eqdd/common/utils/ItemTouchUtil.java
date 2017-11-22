package com.eqdd.common.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;


/**
 * Created by lv on 17-9-25.
 */

public class ItemTouchUtil {

    private static int originPos;
    private static int toPos;
    private static int fromPos;

    public static void openMove(RecyclerView recyclerView, OnMove onMove) {

        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            private RecyclerView.ViewHolder lastDragViewHolder;

            /**
             * 当用户长按后，会触发拖拽的选中效果，viewHolder就是当前的选中
             * @param viewHolder
             * @param actionState 取下面中的值
             *                    {@link ItemTouchHelper#ACTION_STATE_IDLE},
             *                    {@link ItemTouchHelper#ACTION_STATE_SWIPE},
             *                    {@link ItemTouchHelper#ACTION_STATE_DRAG}.
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                //如果状态为拖拽，说明选中了
                //我在xml里面写的scale都为0.8 我们需要把当前的视图放大一下，所以设置为1就可以了
                if (viewHolder != null && actionState == ACTION_STATE_DRAG) {
                    lastDragViewHolder = viewHolder;
                    viewHolder.itemView.setScaleX(1.2f);
                    viewHolder.itemView.setScaleY(1.2f);
                    originPos = lastDragViewHolder.getAdapterPosition();
                }

                //ACTION_STATE_IDLE就是松开了，把大小改为原状
                if (lastDragViewHolder != null && actionState == ACTION_STATE_IDLE) {
                    lastDragViewHolder.itemView.setScaleX(1);
                    lastDragViewHolder.itemView.setScaleY(1);
                    lastDragViewHolder = null;
                    if (onMove != null) {
                        onMove.release(originPos,fromPos, toPos);
                    }
                }
            }

            /**
             * 我们不需要滑动删除，所以返回false
             * @return
             */
            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            /**
             * 这个是用来设置用户可以对 viewHolder进行什么操作，推荐用makeMovementFlags(int dragFlags, int swipeFlags)来处理
             * 例如 makeMovementFlags(UP | DOWN, LEFT);就是可以上下拖拽，向左滑动
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                return makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, -1);
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return true;
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                ItemTouchUtil.toPos = toPos;
                ItemTouchUtil.fromPos = fromPos;
                if (onMove != null) {
                    onMove.move(fromPos, toPos);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(recyclerView);
    }

    public interface OnMove {
        void move(int fromPos, int toPos);

        void release(int originFromPos, int fromPos, int toPos);
    }
}
