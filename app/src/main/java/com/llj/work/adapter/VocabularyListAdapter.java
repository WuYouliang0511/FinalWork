package com.llj.work.adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.llj.work.R;
import com.llj.work.bean.Vocabulary;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VocabularyListAdapter extends RecyclerView.Adapter<VocabularyListAdapter.VocabularyListViewHolder> {

    private static final String TAG = "VocabularyListAdapter";
    private final Context context;
    private ArrayList<Vocabulary> vocabularies;
    private OnVocabularyOperationListener listener;
    private String fuzzyStr;

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

            holder.phonetic.setText(vocabulary.getPhonetic());
            holder.senses_senior.setText(vocabulary.getSenses_senior());
            holder.collect.setChecked(vocabulary.getCollect() == 1);
            holder.collect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onCollect(vocabulary.getId(), isChecked);
                }
            });

            if (TextUtils.isEmpty(fuzzyStr)) {
//                holder.collect.setVisibility(View.VISIBLE);
                holder.lemma.setText(vocabulary.getLemma());
            } else {
                String lemmaInLowerCase = vocabulary.getLemma().toLowerCase();
//                holder.collect.setVisibility(View.GONE);
                int[] index = findIndex(lemmaInLowerCase, fuzzyStr);
                SpannableString ss = new SpannableString(vocabulary.getLemma());
                ss.setSpan(new BackgroundColorSpan(Color.YELLOW), index[0], index[1], 33);
                holder.lemma.setText(ss);
                holder.lemma.setMovementMethod(LinkMovementMethod.getInstance());
            }
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

    /**
     * 根据字符串进行模糊查找,详见DictionaryFragment{afterTextChanged}
     *
     * @param fuzzyStr 模糊字串
     */
    public void fuzzySearch(ArrayList<Vocabulary> vocabularies, String fuzzyStr) {
        this.vocabularies = vocabularies;
        this.fuzzyStr = fuzzyStr;
        Log.d(TAG, "fuzzyStr: " + this.fuzzyStr);
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

    @NotNull
    private int[] findIndex(@NotNull String str1, @NotNull String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        int[] index = new int[]{0, 0};

        for (int i = 0; i < length1 - length2 + 1; i++) {
            if (str1.substring(i, i + length2).equals(str2)) {
                Log.d(TAG, "startIndex: " + i + "   endIndex: " + (i + length2));
                index[0] = i;
                index[1] = i + length2;
                break;
            }
        }
        return index;
    }
}