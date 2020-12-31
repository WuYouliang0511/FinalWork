package com.llj.work.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.llj.work.R;
import com.llj.work.bean.Vocabulary;

import java.util.ArrayList;

public class VocabularyListAdapter extends RecyclerView.Adapter<VocabularyListAdapter.VocabularyListViewHolder> {

    private final Context context;
    private ArrayList<Vocabulary> vocabularies;
    private OnVocabularyOperationListener listener;

    public VocabularyListAdapter(Context context, ArrayList<Vocabulary> vocabularies) {
        this.context = context;
        this.vocabularies = vocabularies;
    }

    @NonNull
    @Override
    public VocabularyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vocabulary, parent, false);
        return new VocabularyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyListViewHolder holder, int position) {
        Vocabulary vocabulary = vocabularies.get(position);

        if (vocabulary != null) {
            holder.lemma.setText(vocabulary.getLemma());
            holder.phonetic.setText(vocabulary.getPhonetic());
            holder.senses_senior.setText(vocabulary.getSenses_senior());
            holder.collect.setChecked(vocabulary.getCollect() == 1);
            holder.collect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onCollect(vocabulary.getId(), isChecked);
                }
            });
        }
    }

    public void resetData(ArrayList<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
        notifyDataSetChanged();//通知列表更新
    }

    public void addData(ArrayList<Vocabulary> vocabularies) {
        this.vocabularies.addAll(vocabularies);
        notifyDataSetChanged();//通知列表更新
    }

    @Override
    public int getItemCount() {
        return vocabularies == null ? 0 : vocabularies.size();
    }

    public static class VocabularyListViewHolder extends RecyclerView.ViewHolder {

        public TextView lemma;
        public TextView phonetic;
        public TextView senses_senior;
        public CheckBox collect;

        public VocabularyListViewHolder(@NonNull View view) {
            super(view);
            lemma = view.findViewById(R.id.lemma);
            phonetic = view.findViewById(R.id.phonetic);
            senses_senior = view.findViewById(R.id.senses_senior);
            collect = view.findViewById(R.id.collect);
        }
    }

    public interface OnVocabularyOperationListener {
        void onCollect(Integer id, boolean collect);
    }

    public void setOnVocabularyOperationListener(OnVocabularyOperationListener listener) {
        this.listener = listener;
    }
}
