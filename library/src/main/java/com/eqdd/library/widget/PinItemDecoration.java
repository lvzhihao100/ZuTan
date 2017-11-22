package com.eqdd.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.eqdd.databind.percent.WindowUtil;


/**
 * Created by lvzhihao on 17-5-16.
 */

public class PinItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "PinItemDecoration";
    private final int textLeft;


    private DecorationCallback mDecorationCallback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private int from;
    private Paint.FontMetrics fontMetrics;
    private final float baseLineHeight;


    public PinItemDecoration(Context context, DecorationCallback mDecorationCallback, int from) {
        this.from = from;
        this.mDecorationCallback = mDecorationCallback;
        //设置悬浮栏的画笔---paint
        paint = new Paint();
        paint.setColor(Color.parseColor("#cccccc"));

        //设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.RED);
        textPaint.setTextAlign(Paint.Align.LEFT);
        Rect rect = new Rect();
        textPaint.getTextBounds("文字", 0, 1, rect);
        fontMetrics = textPaint.getFontMetrics();
        baseLineHeight = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        //决定悬浮栏的高度等
        topGap = WindowUtil.width / 10;
        textLeft = WindowUtil.width / 10;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        //只有是同一组的第一个才显示悬浮栏
        if (isFirstInGroup(pos)) {
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            if (isFirstInGroup(position)) {
                float top = view.getTop() - topGap;
                float bottom = view.getTop();
                float wordY = top + topGap / 2 + baseLineHeight;
//                c.drawRect(left, top, right, bottom, paint);
//                c.drawText(mDecorationCallback.getGroupLabel(position - from), textLeft, wordY, textPaint);
                View groupView = mDecorationCallback.getGroupView(position - from);
                Bitmap drawingCache = getBitmap(left, right, groupView);
                c.drawBitmap(drawingCache, left, top, paint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getChildCount() > 0) {//有内容
            //获取屏幕上第一个item在recyclerView中所占的位置
            int firstPosition = parent.getChildAdapterPosition(parent.getChildAt(0));
            if (firstPosition - from >= 0) {//在可绘制区域
                int childCount = parent.getChildCount();//屏幕上占的数量
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                String text = mDecorationCallback.getGroupLabel(firstPosition);
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                int top = topGap;
                for (int i = 0; i < childCount - 1; i++) {//获取可绘制区域
                    View view = parent.getChildAt(i);
                    int position = parent.getChildAdapterPosition(view);
                    String groupLabelNow = mDecorationCallback.getGroupLabel(position - from);
                    String groupLabelNext = mDecorationCallback.getGroupLabel(position + 1 - from);
                    if (TextUtils.isEmpty(groupLabelNow) || TextUtils.isEmpty(groupLabelNext)) {
                        return;
                    }
                    if (!mDecorationCallback.getGroupLabel(position - from).equals(mDecorationCallback.getGroupLabel(position + 1 - from))) {
                        View nextView = parent.getChildAt(i + 1);
                        if (nextView.getTop() < (topGap * 2)) {
                            top = nextView.getTop() - topGap;
                            break;
                        }
                    }
                }
                View groupView = mDecorationCallback.getGroupView(firstPosition);
                Bitmap drawingCache = getBitmap(left, right, groupView);
                c.drawBitmap(drawingCache, left, top - topGap, paint);
                return;
            }
        }
        int itemCount = state.getItemCount();//所有数量
        int childCount = parent.getChildCount();//屏幕上占的数量
        String text = "";
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        try {
            text = mDecorationCallback.getGroupLabel(parent.getChildAdapterPosition(parent.getChildAt(0)) - from);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (TextUtils.isEmpty(text)) {
            return;
        }
        int top = topGap;
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            String groupLabelNow = mDecorationCallback.getGroupLabel(position - from);
            String groupLabelNext = mDecorationCallback.getGroupLabel(position + 1 - from);
            if (TextUtils.isEmpty(groupLabelNow) || TextUtils.isEmpty(groupLabelNext)) {
                return;
            }
            if (!mDecorationCallback.getGroupLabel(position - from).equals(mDecorationCallback.getGroupLabel(position + 1 - from))) {
                View nextView = parent.getChildAt(i + 1);
                if (nextView.getTop() < (topGap * 2)) {
                    top = nextView.getTop() - topGap;
                    break;
                }
            }
        }
//        mDecorationCallback.getGroupView(firstPosition)
//        textView.setDrawingCacheEnabled(true);
//        textView.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
//        textView.layout(0,top-topGap,right,top);
//        textView.buildDrawingCache();
//        Bitmap drawingCache = textView.getDrawingCache();
//        drawingCache = drawingCache.createBitmap(drawingCache);
//        textView.setDrawingCacheEnabled(false);
//        c.drawBitmap(drawingCache, left, top-topGap, paint);
//        c.drawRect(left, 0, right, top, paint);
//        c.drawText(text, textLeft, top - topGap / 2 + baseLineHeight, textPaint);
    }

    private Bitmap getBitmap(int left, int right, View groupView) {
        groupView.setDrawingCacheEnabled(true);
        groupView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        groupView.layout(0, 0, right - left, topGap);
        groupView.buildDrawingCache();
        Bitmap drawingCache = groupView.getDrawingCache();
        drawingCache = drawingCache.createBitmap(drawingCache);
        groupView.setDrawingCacheEnabled(false);
        return drawingCache;
    }

    private boolean isFirstInGroup(int pos) {
        if (pos < from) {
            return false;
        }
        if (pos == from && !TextUtils.isEmpty(mDecorationCallback.getGroupLabel(pos - from))) {
            return true;
        }

        String prevLabel = mDecorationCallback.getGroupLabel(pos - 1 - from);
        String label = mDecorationCallback.getGroupLabel(pos - from);
        if (TextUtils.isEmpty(prevLabel) || TextUtils.isEmpty(label)) {
            return false;
        }
        if (prevLabel.equals(label)) {
            return false;
        } else {
            return true;
        }

    }

    private boolean isLastInGroup(int pos) {

        String label = mDecorationCallback.getGroupLabel(pos);
        String nextLabel;
        try {
            nextLabel = mDecorationCallback.getGroupLabel(pos + 1);
        } catch (ArrayIndexOutOfBoundsException exception) {
            return true;
        }

        if (!label.equals(nextLabel)) return true;

        return false;
    }


    public interface DecorationCallback {
        String getGroupLabel(int position);

        View getGroupView(int position);
    }
}  