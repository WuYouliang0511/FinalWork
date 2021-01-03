package com.llj.work.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

public class Vocabulary implements Parcelable {

    private Integer id;//单词的id
    private String lemma;//单词的引用
    private String lemma_mark;//单词的音节
    private String senses_senior;//单词的释义
    private String phonetic;//发音
    private String lastModify;
    private Integer degree;//难度
    private Integer collect;//是否收藏  1：收藏  0：未收藏
    private Integer have_audio;//是否有语音 1：有  0：没有

    public Vocabulary() {
    }

    protected Vocabulary(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        lemma = in.readString();
        lemma_mark = in.readString();
        senses_senior = in.readString();
        phonetic = in.readString();
        lastModify = in.readString();
        if (in.readByte() == 0) {
            degree = null;
        } else {
            degree = in.readInt();
        }
        if (in.readByte() == 0) {
            collect = null;
        } else {
            collect = in.readInt();
        }
        if (in.readByte() == 0) {
            have_audio = null;
        } else {
            have_audio = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(lemma);
        dest.writeString(lemma_mark);
        dest.writeString(senses_senior);
        dest.writeString(phonetic);
        dest.writeString(lastModify);
        if (degree == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(degree);
        }
        if (collect == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(collect);
        }
        if (have_audio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(have_audio);
        }
    }

    @Override
    @NotNull
    public String toString() {
        return "Vocabulary{" +
                "id=" + id +
                ", lemma='" + lemma + '\'' +
                ", lemma_mark='" + lemma_mark + '\'' +
                ", senses_senior='" + senses_senior + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", lastModify='" + lastModify + '\'' +
                ", degree=" + degree +
                ", collect=" + collect +
                ", have_audio=" + have_audio +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Vocabulary> CREATOR = new Creator<Vocabulary>() {
        @Override
        public Vocabulary createFromParcel(Parcel in) {
            return new Vocabulary(in);
        }

        @Override
        public Vocabulary[] newArray(int size) {
            return new Vocabulary[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getLemma_mark() {
        return lemma_mark;
    }

    public void setLemma_mark(String lemma_mark) {
        this.lemma_mark = lemma_mark;
    }

    public String getSenses_senior() {
        return senses_senior;
    }

    public void setSenses_senior(String senses_senior) {
        this.senses_senior = senses_senior;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getHave_audio() {
        return have_audio;
    }

    public void setHave_audio(Integer have_audio) {
        this.have_audio = have_audio;
    }

    public static Creator<Vocabulary> getCREATOR() {
        return CREATOR;
    }
}