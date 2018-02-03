package com.gamerole.zutan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.eqdd.common.utils.DensityUtil;

/**
 * @author吕志豪 .
 * @date 18-1-29  下午5:14.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class ThreeCircleView extends View {

    private int fullWidth;
    private int fullHeight;
    private Paint mPaint;
    private float ringWidth;

    public ThreeCircleView(Context context) {
        super(context);
        init();
    }

    public ThreeCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThreeCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        fullWidth = getMySize(DensityUtil.percentW(630), widthMeasureSpec);
        fullHeight = getMySize(DensityUtil.percentW(630), heightMeasureSpec);
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
        //绘制圆环
        canvas.drawOval(0, 0, fullWidth, fullHeight, mPaint);
        canvas.drawOval(fullWidth / 5, fullHeight / 5, fullWidth * 4 / 5, fullHeight * 4 / 5, mPaint);
        canvas.drawOval(fullWidth / 3, fullHeight / 3, fullWidth * 2 / 3, fullHeight * 2 / 3, mPaint);
    }
}
