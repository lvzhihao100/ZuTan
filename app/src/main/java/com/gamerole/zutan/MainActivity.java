package com.gamerole.zutan;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.view.View;
import android.widget.Toast;

import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.utils.CalendarEventUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.security.Permission;
import java.security.Permissions;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends CommonFullTitleActivity {

    private MainActivityCustom dataBinding;


    //Android2.2版本以后的URL，之前的就不写了
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";


    public void onClick(View v) {
        if (v.getId() == R.id.readUserButton) {  //读取系统日历账户，如果为0的话先添加
            Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);

            System.out.println("Count: " + userCursor.getCount());
            Toast.makeText(this, "Count: " + userCursor.getCount(), Toast.LENGTH_LONG).show();

            for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
                System.out.println("name: " + userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME")));


                String userName1 = userCursor.getString(userCursor.getColumnIndex("name"));
                String userName0 = userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME"));
                Toast.makeText(this, "NAME: " + userName1 + " -- ACCOUNT_NAME: " + userName0, Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.inputaccount) {        //添加日历账户

        } else if (v.getId() == R.id.delEventButton) {  //删除事件

            int rownum = getContentResolver().delete(Uri.parse(calanderURL), "_id!=-1", null);  //注意：会全部删除所有账户，新添加的账户一般从id=1开始，
            //可以令_id=你添加账户的id，以此删除你添加的账户
            Toast.makeText(MainActivity.this, "删除了: " + rownum, Toast.LENGTH_LONG).show();

        } else if (v.getId() == R.id.readEventButton) {  //读取事件
            Cursor eventCursor = getContentResolver().query(Uri.parse(calanderEventURL), null, null, null, null);
            if (eventCursor.getCount() > 0) {
                eventCursor.moveToLast();             //注意：这里与添加事件时的账户相对应，都是向最后一个账户添加
                String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                Toast.makeText(MainActivity.this, eventTitle, Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.writeEventButton) {
//
            CalendarEventUtil.addCalendarEvent(this, "张三", "测试", "你好呀", "tianshang", new Date().getTime(), new Date().getTime() + 1000*60*60*24*10+1000*60*60, 10);
            Toast.makeText(MainActivity.this, "插入事件成功!!!", Toast.LENGTH_LONG).show();
        }
    }




    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (MainActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_main;
    }

    @Override
    public void initData() {

        RxPermissions.getInstance(this).request(Manifest.permission.READ_CALENDAR)
                .subscribe(granted -> {
                    if (granted) {
                        System.out.println("授权成功");
                    }
                });
    }

    @Override
    public void setView() {

    }
}
