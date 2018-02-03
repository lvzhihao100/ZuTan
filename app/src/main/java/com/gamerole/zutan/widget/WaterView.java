package com.gamerole.zutan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.eqdd.common.utils.DensityUtil;

/**
 * @author吕志豪 .
 * @date 18-1-29  下午3:50.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class WaterView extends View {

    private int fullWidth;
    private int fullHeight;
    private Paint mPaint;
    private int makeFirstWidth;
    private int makeFirstHeight;
    private int makeSecondWidth;
    private int makeSecondHeight;
    private int realWidth;
    private int realHeight;

    public WaterView(Context context) {
        super(context);
        init();
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        fullWidth = getMySize(DensityUtil.percentW(630), widthMeasureSpec);
        fullHeight = getMySize(DensityUtil.percentW(630), heightMeasureSpec);
        realWidth = fullWidth - getPaddingLeft() - getPaddingRight();
        realHeight = fullHeight - getPaddingTop() - getPaddingBottom();
        makeFirstWidth = 130 * realWidth / 100;
        makeFirstHeight = 5 * realHeight / 100;
        makeSecondWidth = 25 * realWidth / 100;
        makeSecondHeight = 100 * realHeight / 100;
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);//绘制透明色
        Path mPath = new Path();
        int centerX = fullWidth >> 1;
        int topY = getPaddingTop();
        int bottomY = fullHeight - getPaddingTop();
        mPath.reset();
        mPath.moveTo(centerX, topY);
        mPath.cubicTo(centerX + makeFirstWidth, makeFirstHeight - topY, centerX + makeSecondWidth, makeSecondHeight - topY, centerX, bottomY);
        mPath.cubicTo(centerX - makeSecondWidth, makeSecondHeight - topY, centerX - makeFirstWidth, makeFirstHeight - topY, centerX, topY);
        canvas.drawPath(mPath, mPaint);
    }

    public void setMakeFirstWidth(int makeFirstWidth) {
        this.makeFirstWidth = makeFirstWidth * realWidth / 100;
    }

    public void setMakeFirstHeight(int makeFirstHeight) {
        this.makeFirstHeight = makeFirstHeight * realHeight / 100;
    }

    public void setMakeSecondWidth(int makeSecondWidth) {
        this.makeSecondWidth = makeSecondWidth * realWidth / 100;
    }

    public void setMakeSecondHeight(int makeSecondHeight) {
        this.makeSecondHeight = makeSecondHeight * realHeight / 100;
    }

    public void refresh() {
        invalidate();
    }
}
