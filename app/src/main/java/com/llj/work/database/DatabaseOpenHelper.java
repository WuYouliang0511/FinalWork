package com.llj.work.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseOpenHelper";
    public static final String DATABASE_NAME = "finalWork.db";
    public static final int DATABASE_VERSION = 3;

    public DatabaseOpenHelper(Context context) {
        //1、context上下文  2、name数据库名称  3、factory  4、version数据库版本
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
        //1、context上下文  2、name数据库名称  3、factory  4、version数据库版本
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase database) {
        database.beginTransaction();
        try {
            init(database);
            updateFrom1To2(database);
            updateFrom2To3(database);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            updateFrom1To2(database);
            updateFrom2To3(database);
        } else if (oldVersion == 2) {
            updateFrom2To3(database);
        }
    }

    private void init(@NotNull SQLiteDatabase database) {
        String sql = "create table vocabulary(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "lemma varchar(40) DEFAULT '' NOT NULL," +
                "lemma_mark varchar(60) DEFAULT '' NOT NULL," +
                "senses_senior varchar(40) DEFAULT '' NOT NULL," +
                "phonetic varchar(40) DEFAULT '' NOT NULL," +
                "last_modify varchar(16) DEFAULT '' NOT NULL," +
                "degree INTEGER DEFAULT 0," +
                "collect INTEGER DEFAULT 0," +
                "have_audio INTEGER DEFAULT 0)";
        database.execSQL(sql);
        Log.d(TAG, "数据库创建成功");

        for (int i = 0; i < 1000; i++) {
            String sql1 = "INSERT INTO vocabulary VALUES(NULL,'" + (getRandomStr()) + "','音节" + i + "','解释" + i + "','发音','1970-01-01 00:00'," + (new Random().nextInt(9) + 1) + ",0,"+new Random().nextInt(2)+")";
            database.execSQL(sql1);
            Log.d(TAG, "插入数据成功:" + sql1);
        }
    }

    private void updateFrom1To2(@NotNull SQLiteDatabase database) {

    }

    private void updateFrom2To3(@NotNull SQLiteDatabase database) {

    }

    @NotNull
    private static String getRandomStr() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(2);
            long result;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString().toLowerCase();
    }
}
