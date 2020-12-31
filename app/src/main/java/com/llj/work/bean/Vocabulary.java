package com.llj.work.bean;


import org.jetbrains.annotations.NotNull;

public class Vocabulary {

    private Integer id;//单词的id
    private String lemma;//单词的引用
    private String lemma_mark;//单词的音节
    private String senses_senior;//单词的释义
    private String phonetic;//发音
    private Integer degree;//难度
    private Integer collect;//是否收藏  1：收藏  0：未收藏
    private Integer have_audio;//是否有语音 1：有  0：没有

    public Vocabulary() {
    }

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

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getHave_audio() {
        return have_audio;
    }

    public void setHave_audio(Integer have_audio) {
        this.have_audio = have_audio;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
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
                ", degree=" + degree +
                ",collect=" + collect +
                ", have_audio=" + have_audio +
                '}';
    }
}