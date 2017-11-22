package com.eqdd.library.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;

import com.eqdd.common.base.App;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by lv on 17-10-7.
 */

public class CalendarEventUtil {
    //Android2.2版本以后的URL，之前的就不写了
    private static String CALANDER_URL = "content://com.android.calendar/calendars";
    private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDARS_NAME = "易企点";
    private static String CALENDARS_ACCOUNT_NAME = "易企点备忘录";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";

    /**
     * 检查是否有现有存在的账户。存在则返回账户id，否则返回-1
     *
     * @param
     * @return
     */
    private static int checkCalendarAccount(String displayName) {
        Cursor userCursor = App.INSTANCE.getContentResolver().query(Uri.parse(CALANDER_URL), null, null, null, null);
        try {
            if (userCursor == null)//查询返回空值
                return -1;
            int count = userCursor.getCount();
            if (count > 0) {//存在现有账户，取第一个账户的id返回
                for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {


                    String displayNameDB = userCursor.getString(userCursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
                    String accountName = userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME"));
                    if (accountName.equals(CALENDARS_ACCOUNT_NAME) && displayName.equals(displayNameDB)) {
                        return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
                    }
                }
                return -1;
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    /**
     * 添加账户。账户创建成功则返回账户id，否则返回-1
     *
     * @param
     * @param CALENDARS_DISPLAY_NAME
     * @return
     */
    private static long addCalendarAccount(String CALENDARS_DISPLAY_NAME) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALANDER_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = App.INSTANCE.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    //检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
    private static int checkAndAddCalendarAccount(String CALENDARS_DISPLAY_NAME) {
        int oldId = checkCalendarAccount(CALENDARS_DISPLAY_NAME);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(CALENDARS_DISPLAY_NAME);
            if (addId >= 0) {
                return checkCalendarAccount(CALENDARS_DISPLAY_NAME);
            } else {
                return -1;
            }
        }
    }

    public static void addCalendarEvent(Context context, String displayName, String title, String description,
                                        String eventLocation, long beginTime, long endTime, int preMinute) {
        // 获取日历账户的id
        int calId = checkAndAddCalendarAccount(displayName);
        if (calId < 0) {
            // 获取账户id失败直接返回，添加日历事件失败
            return;
        }
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.TITLE, title);
        event.put(CalendarContract.Events.DESCRIPTION, description);
        // 插入账户的id
        event.put(CalendarContract.Events.CALENDAR_ID, calId);
        event.put(CalendarContract.Events.EVENT_LOCATION, eventLocation);


        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(beginTime);//设置开始时间
        long start = mCalendar.getTime().getTime();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        mCalendar.setTimeInMillis(endTime);//设置终止时间
        mCalendar.set(year, month, day);
        long end = mCalendar.getTime().getTime();
        mCalendar.setTimeInMillis(endTime + 1000 * 60 * 60 * 24);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String endDay = sf.format(mCalendar.getTime());

        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL=" + endDay + "T000000Z");
        event.put(CalendarContract.Events.DTEND, end);
        event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
        //添加事件
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALANDER_EVENT_URL), event);
        if (newEvent == null) {
            // 添加日历事件失败直接返回
            return;
        }
        //事件提醒的设定
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
        // 提前10分钟有提醒
        values.put(CalendarContract.Reminders.MINUTES, preMinute);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = context.getContentResolver().insert(Uri.parse(CALANDER_REMIDER_URL), values);
        if (uri == null) {
            // 添加闹钟提醒失败直接返回
            return;
        }
    }

    /**
     * 根据设置的title来查找并删除
     *
     * @param context
     * @param title
     */
    public static void deleteCalendarEvent(Context context, String title) {
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALANDER_EVENT_URL), null, null, null, null);
        try {
            if (eventCursor == null)//查询返回空值
                return;
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALANDER_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) {
                            //事件删除失败
                            return;
                        }
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }


}
