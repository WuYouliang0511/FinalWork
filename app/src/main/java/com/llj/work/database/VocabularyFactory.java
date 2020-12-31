package com.llj.work.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
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

    public ArrayList<Vocabulary> getAllVocabulary() {
        ArrayList<Vocabulary> vocabularies = new ArrayList<>();
        String sql = "SELECT * FROM vocabulary";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String lemma = cursor.getString(1);
            String lemma_mark = cursor.getString(2);
            String senses_senior = cursor.getString(3);
            String phonetic = cursor.getString(4);
            int degree = cursor.getInt(5);
            int collect = cursor.getInt(6);
            int have_audio = cursor.getInt(7);
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setId(id);
            vocabulary.setLemma(lemma);
            vocabulary.setLemma_mark(lemma_mark);
            vocabulary.setSenses_senior(senses_senior);
            vocabulary.setPhonetic(phonetic);
            vocabulary.setDegree(degree);
            vocabulary.setCollect(collect);
            vocabulary.setHave_audio(have_audio);
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
}
