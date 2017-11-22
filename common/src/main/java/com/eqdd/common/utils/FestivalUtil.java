package com.eqdd.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvzhihao on 17-5-18.
 */

public class FestivalUtil {
    private static Map<String,String> yangliFestivals=new HashMap<>();
    private static Map<String,String> yinliFestivals=new HashMap<>();
    static {
        yangliFestivals.put("01-01","元旦节");
        yangliFestivals.put("02-14","情人节");
        yangliFestivals.put("03-08","妇女节");
        yangliFestivals.put("03-12","植树节");
        yangliFestivals.put("04-01","愚人节");
        yangliFestivals.put("04-05","清明节");
        yangliFestivals.put("05-01","劳动节");
        yangliFestivals.put("05-04","青年节");
        yangliFestivals.put("05-31","无烟日");
        yangliFestivals.put("06-01","儿童节");
        yangliFestivals.put("07-01","建党节");
        yangliFestivals.put("08-01","建军节");
        yangliFestivals.put("09-10","教师节");
        yangliFestivals.put("10-01","国庆节");
        yangliFestivals.put("10-31","鬼节");
        yangliFestivals.put("11-01","万圣节");
        yangliFestivals.put("11-11","单身节");
        yangliFestivals.put("12-24","平安夜");
        yangliFestivals.put("12-25","圣诞节");

        yinliFestivals.put("正月初一","春节");
        yinliFestivals.put("正月十五","元宵节");
        yinliFestivals.put("二月初二","龙抬头");
        yinliFestivals.put("五月初五","端午节");
        yinliFestivals.put("七月初七","乞巧节");
        yinliFestivals.put("八月十五","中秋节");
        yinliFestivals.put("九月初九","重阳节");
        yinliFestivals.put("腊月初八","腊八节");
        yinliFestivals.put("腊月廿三","小年");
        
    }

    public static String getFestival(Calendar calendar){
        //阳历节日
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        System.out.println(sf.format(calendar.getTime()));
        if (yangliFestivals.get(sf.format(calendar.getTime()))!=null){
            return yangliFestivals.get(sf.format(calendar.getTime()));
        }
        Lunar lunar = new Lunar(calendar);

        if (yinliFestivals.get(lunar.toString())!=null){
            return yinliFestivals.get(lunar.toString());
        }
        calendar.add(Calendar.DATE,1);
        Lunar temp = new Lunar(calendar);
        if (temp.toString().equals("正月初一")){
            return "除夕节";
        }
        return null;
    }
}
