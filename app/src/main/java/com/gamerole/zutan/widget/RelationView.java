package com.gamerole.zutan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eqdd.common.base.App;
import com.eqdd.common.base.GlideApp;
import com.eqdd.common.utils.DensityUtil;

/**
 * @author吕志豪 .
 * @date 18-1-18  下午5:37.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class RelationView extends View {
    private static RequestOptions optionsCircle;
    private Paint mPaint;
    private Bitmap mBmp;

    static {
        optionsCircle = new RequestOptions()
                .placeholder(com.eqdd.common.R.mipmap.common_holder_img)
                .error(com.eqdd.common.R.mipmap.common_error_img)
                .override(200, 200)
                .transform(new CircleCrop());
    }

    private boolean isFirst = true;
    private int fullWidth;
    private int fullHeight;


    public RelationView(Context context) {
        super(context);
        init();
    }

    public RelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RelationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        GlideApp.with(App.INSTANCE).asBitmap().load("https://c3.xinstatic.com/f2/20180118/2048/5a6097bb89847237377_18.jpg").apply(optionsCircle).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                mBmp = resource;
                if (!isFirst) {
                    invalidate();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        fullWidth = getMySize(DensityUtil.percentW(630), widthMeasureSpec);
        fullHeight = getMySize(DensityUtil.percentW(630), heightMeasureSpec);
        int innerPadding = 50;

        float radius =50;
//        float fatherCenterX=getPaddingLeft()+innerPadding+

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
        super.onDraw(canvas);

        isFirst = false;
        if (mBmp != null) {
            int width = 200;
            int height = width * mBmp.getHeight() / mBmp.getWidth();
            mPaint.setColor(Color.RED);

//        int layerID = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);

            canvas.drawBitmap(mBmp, null, new Rect(0, 0, width, height), mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        canvas.drawRect(0, 0, width, height, mPaint);
//
//        canvas.restoreToCount(layerID);

        }
    }
}
