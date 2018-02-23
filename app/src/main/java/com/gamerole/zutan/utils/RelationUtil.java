package com.gamerole.zutan.utils;

import android.util.SparseArray;

/**
 * @author吕志豪 .
 * @date 18-1-23  下午4:54.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class RelationUtil {
    private static SparseArray<String> shipArray;
    static {
        shipArray=new SparseArray(5);
        shipArray.put(0,"爸爸");
        shipArray.put(1,"妈妈");
        shipArray.put(2,"另一半");
        shipArray.put(3,"孩子");
        shipArray.put(4,"姐/兄");
        shipArray.put(5,"妹/弟");
    }
    public static String getShip(int relation){
        return shipArray.get(relation);
    }
}
