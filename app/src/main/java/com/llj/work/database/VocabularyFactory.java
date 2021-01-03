package com.llj.work.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.llj.work.bean.Vocabulary;

import java.util.ArrayList;

@SuppressLint("StaticFieldLeak")
public class VocabularyFactory {

    private static final String TAG = "VocabularyFactory";
    private static SQLiteDatabase database;
    private static volatile VocabularyFactory instance;
    private final Context context;
    private static final int PAGE_LIMIT = 20;

    public static VocabularyFactory getInstance(Context context) {
        if (instance == null) {
            synchronized (VocabularyFactory.class) {
                if (instance == null) {
                    Log.d(TAG, "创建数据库工厂");
                    instance = new VocabularyFactory(context);
                }
            }
        }
        return instance;
    }

    private VocabularyFactory(Context context) {
        this.context = context;
        if (database == null || !database.isOpen()) {
            try {
                Log.d(TAG, "准备加载数据库");
                database = new DatabaseOpenHelper(context).getReadableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getVersion() {
        return database.getVersion();
    }

    /**
     * @return 获取所有的单词
     */
    public ArrayList<Vocabulary> getAllVocabulary() {
        ArrayList<Vocabulary> vocabularies = new ArrayList<>();
        String sql = "SELECT * FROM vocabulary";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setId(cursor.getInt(0));
            vocabulary.setLemma(cursor.getString(1));
            vocabulary.setLemma_mark(cursor.getString(2));
            vocabulary.setSenses_senior(cursor.getString(3));
            vocabulary.setPhonetic(cursor.getString(4));

            vocabulary.setDegree(cursor.getInt(6));
            vocabulary.setCollect(cursor.getInt(7));
            vocabulary.setHave_audio(cursor.getInt(8));
            Log.d(TAG, vocabulary.toString());
            vocabularies.add(vocabulary);
        }
        cursor.close();
        return vocabularies;
    }

    /**
     * @return 获取一页单词 个数为PAGE_LIMIT
     */
    public ArrayList<Vocabulary> getOnePage(int page, boolean order) {
        int index = PAGE_LIMIT * page;
        ArrayList<Vocabulary> vocabularies = new ArrayList<>();
        String sql = "select * from vocabulary order by lemma asc limit " + PAGE_LIMIT + " offset " + index;

        String sql1 = "select * from (" + sql + ") order by lemma " + (order ? "asc" : "desc");
        Cursor cursor = database.rawQuery(sql1, null);
        while (cursor.moveToNext()) {
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setId(cursor.getInt(0));
            vocabulary.setLemma(cursor.getString(1));
            vocabulary.setLemma_mark(cursor.getString(2));
            vocabulary.setSenses_senior(cursor.getString(3));
            vocabulary.setPhonetic(cursor.getString(4));

            vocabulary.setDegree(cursor.getInt(6));
            vocabulary.setCollect(cursor.getInt(7));
            vocabulary.setHave_audio(cursor.getInt(8));
            Log.d(TAG, vocabulary.toString());
            vocabularies.add(vocabulary);
        }
        cursor.close();
        return vocabularies;
    }

    /**
     * @return 排序
     */
    public ArrayList<Vocabulary> sort(int page, boolean order) {
        int index = PAGE_LIMIT * page;
        ArrayList<Vocabulary> vocabularies = new ArrayList<>();

        String sql = "select * from vocabulary order by lemma asc limit " + index;
        String sql1 = "select * from (" + sql + ") order by lemma " + (order ? "asc" : "desc");
        Cursor cursor = database.rawQuery(sql1, null);
        while (cursor.moveToNext()) {
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setId(cursor.getInt(0));
            vocabulary.setLemma(cursor.getString(1));
            vocabulary.setLemma_mark(cursor.getString(2));
            vocabulary.setSenses_senior(cursor.getString(3));
            vocabulary.setPhonetic(cursor.getString(4));
            vocabulary.setDegree(cursor.getInt(6));
            vocabulary.setCollect(cursor.getInt(7));
            vocabulary.setHave_audio(cursor.getInt(8));
            Log.d(TAG, vocabulary.toString());
            vocabularies.add(vocabulary);
        }
        cursor.close();
        return vocabularies;
    }

    /**
     * 进行一次收藏 或者 取消收藏 的操作
     *
     * @param id        单词的id
     * @param collected 操作 true:收藏  false:取消收藏
     */
    public void doCollectOperation(int id, boolean collected) {
        try {
            String sql = "UPDATE vocabulary SET collect=" + (collected ? 1 : 0) + " WHERE id=" + id;
            database.execSQL(sql);
            Toast.makeText(context, collected ? "收藏成功" : "取消收藏", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "操作失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @return 获取收藏的单词
     */
    public ArrayList<Vocabulary> getCollection() {
        ArrayList<Vocabulary> vocabularies = new ArrayList<>();
        String sql = "SELECT * FROM vocabulary WHERE collect = 1 ORDER BY lemma";
        try {
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Vocabulary vocabulary = new Vocabulary();
                vocabulary.setId(cursor.getInt(0));
                vocabulary.setLemma(cursor.getString(1));
                vocabulary.setLemma_mark(cursor.getString(2));
                vocabulary.setSenses_senior(cursor.getString(3));
                vocabulary.setPhonetic(cursor.getString(4));
                vocabulary.setLastModify(cursor.getString(5));
                vocabulary.setDegree(cursor.getInt(6));
                vocabulary.setCollect(cursor.getInt(7));
                vocabulary.setHave_audio(cursor.getInt(8));
                Log.d(TAG, vocabulary.toString());
                vocabularies.add(vocabulary);
            }
            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vocabularies;
    }

    public boolean modifyDegree(Integer id, Integer degree, String time) {
        String sql = "UPDATE vocabulary SET degree='" + degree + "' ,last_modify='" + time + "' WHERE id=" + id;
        Log.d(TAG, "modifyDegree: " + sql);
        try {
            database.execSQL(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}