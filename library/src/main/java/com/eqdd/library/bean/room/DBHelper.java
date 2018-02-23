package com.eqdd.library.bean.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.eqdd.library.bean.room.database.AppDatabase;


/**
 * @author吕志豪 .
 * @date 17-11-28  下午6:14.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class DBHelper {
    private static final DBHelper instance = new DBHelper();
    private static final String DATABASE_NAME = "user";
    private DBHelper() {

    }
    static final Migration MIGRATION_1_2 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN last_update INTEGER");
        }
    };
    public static DBHelper getInstance() {
        return instance;
    }

    private AppDatabase db;

    public void init(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME)
//                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public AppDatabase getDb() {
        return db;
    }
}
