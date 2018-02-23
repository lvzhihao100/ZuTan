package com.gamerole.zutan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gamerole.zutan.R;

/**
 * @author吕志豪 .
 * @date 18-2-5  上午10:58.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class BlackHoleView extends View {
    //将水平和竖直方向上都划分为20格
    private final int WIDTH = 100;
    private final int HEIGHT = 100;
    private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);  //记录该图片包含21*21个点
    private final float[] verts = new float[COUNT * 2];    //扭曲前21*21个点的坐标
    private final float[] orig = new float[COUNT * 2];    //扭曲后21*21个点的坐标
    private Bitmap mBitmap;
    private float bH, bW;
    private int sx = 0;
    private double degree = 0;
    private float centerX;
    private float centerY;

    public void setSx(int sx) {
        this.sx = sx;
        mash(sx, 20);
        invalidate();
    }

    public void setDegree(double degree) {
        this.degree = degree;
        if (degree <= 1) {
            mash(sx, degree);
            invalidate();
        }

    }

    private void mash(int sx, double degree) {
        int index = 0;
        //初始化orig和verts数组。
        for (int y = 0; y <= HEIGHT; y++) {
            for (int x = 0; x <= WIDTH; x++) {
                PointF vertPoint = dealXY(orig[index * 2 + 0], orig[index * 2 + 1], degree, sx);
                verts[index * 2 + 0] = vertPoint.x;
                verts[index * 2 + 1] = vertPoint.y;
                index++;
            }
        }
    }

    //    private PointF dealXY(float x, float y, double degress, int sx) {
//        double returnX;
//        double returnY;
//        System.out.println(degress);
//        double originDegree = degress;
//        degress = Math.sqrt((x * x + y * y)) * degress;
//        returnX = (x * Math.cos(Math.toRadians(degress)) - y * Math.sin(Math.toRadians(degress))) * ((1 - originDegree));
//        returnY = (x * Math.sin(Math.toRadians(degress)) + y * Math.cos(Math.toRadians(degress))) * ((1 - originDegree));
//        return new PointF((float) (returnX), (float) (returnY));
//    }
    private PointF dealXY(float x0, float y0, double degress, int sx) {
        double pi = 3.1415926;
        double Dis = x0 * x0 + y0 * y0;
        double theta = Math.atan((y0 / (x0 + 0.00001)));
        if (x0 < 0) {
            theta = theta + pi;
        }

       double radius = Math.sqrt(Dis);
        theta = theta + radius / sx;
        double new_x = radius * Math.cos(theta);
        double new_y = radius * Math.sin(theta);
//        if(new_x<0)         new_x=0;
//        if(new_x>=width-1)  new_x=width-2;
//        if(new_y<0)         new_y=0;
//        if(new_y>=height-1) new_y=height-2;
//        double returnX;
//        double returnY;
//        System.out.println(degress);
//        double originDegree = degress;
//        degress = Math.sqrt((x * x + y * y)) * degress;
//        returnX = (x * Math.cos(Math.toRadians(degress)) - y * Math.sin(Math.toRadians(degress))) * ((1 - originDegree));
//        returnY = (x * Math.sin(Math.toRadians(degress)) + y * Math.cos(Math.toRadians(degress))) * ((1 - originDegree));
        return new PointF((float) (new_x), (float) (new_y));
    }


    public BlackHoleView(Context context) {
        super(context);

    }

    public BlackHoleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public BlackHoleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void collapse(Bitmap mBitmap, float centerX, float centerY) {
        this.mBitmap = mBitmap;
        this.centerX = centerX;
        this.centerY = centerY;
        bW = mBitmap.getWidth();
        bH = mBitmap.getHeight();
        int index = 0;
        //初始化orig和verts数组。
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = bH * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bW * x / WIDTH;
                orig[index * 2] = verts[index * 2] = fx - centerX;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy - centerY;
                index += 1;
            }
        }
        //设置背景色
        setBackgroundColor(Color.WHITE);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.translate(centerX, centerY);
            canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts
                    , 0, null, 0, null);
        }
    }

}
