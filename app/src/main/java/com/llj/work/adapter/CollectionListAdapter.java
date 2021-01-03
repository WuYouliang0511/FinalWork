package com.llj.work.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.llj.work.R;
import com.llj.work.activity.DetailActivity;
import com.llj.work.bean.Vocabulary;
import com.llj.work.database.VocabularyFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.CollectionListViewHolder> {

    private static final String TAG = "CollectionListAdapter";
    private ArrayList<Vocabulary> vocabularies;
    private final Context context;

    public CollectionListAdapter(Context context, ArrayList<Vocabulary> vocabularies) {
        this.context = context;
        this.vocabularies = vocabularies;
    }

    @NonNull
    @Override
    public CollectionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
        return new CollectionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionListViewHolder holder, int position) {
        Vocabulary vocabulary = vocabularies.get(position);
        if (vocabulary != null) {

            holder.lemma.setText(vocabulary.getLemma());
            holder.phonetic.setText(vocabulary.getPhonetic());
            holder.senses_senior.setText(vocabulary.getSenses_senior());
            holder.lastModify.setText(vocabulary.getLastModify());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(context, DetailActivity.class);
                intent.putExtra("vocabulary", vocabulary);
                context.startActivity(intent);
            });

            holder.degree.setSelection(vocabulary.getDegree() - 1);
            holder.degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position + 1 == vocabulary.getDegree()) return;
                    Log.d(TAG, "position: " + position);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String lastModify = format.format(new Date());
                    boolean result = VocabularyFactory.getInstance(context).modifyDegree(vocabulary.getId(), position + 1, lastModify);
                    Toast.makeText(context, result ? "修改成功" : "修改失败", Toast.LENGTH_LONG).show();
                    vocabulary.setLastModify(lastModify);
                    vocabulary.setDegree(position + 1);
                    notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //发音
            if (vocabulary.getHave_audio() == 1) {
                holder.audio.setVisibility(View.VISIBLE);
                holder.layoutAudio.setOnClickListener(v -> {
                    Toast.makeText(context, vocabulary.getLemma() + "的发音", Toast.LENGTH_LONG).show();
                    AssetManager manager = context.getAssets();
                    try {
                        String[] fileNames = manager.list("");
                        Log.d(TAG, "size: " + fileNames.length);
                        for (String fileName : fileNames) {
                            Log.d(TAG, "fileName: " + fileName);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                holder.audio.setVisibility(View.GONE);
            }
        }
    }

    public void update() {
        this.vocabularies = VocabularyFactory.getInstance(context).getCollection();
        notifyDataSetChanged();
    }

    @Override

    public int getItemCount() {
        return vocabularies == null ? 0 : vocabularies.size();
    }

    public static class CollectionListViewHolder extends RecyclerView.ViewHolder {

        public TextView lemma;
        public TextView phonetic;
        public TextView senses_senior;
        public TextView lastModify;
        public Spinner degree;
        public LinearLayout layoutAudio;
        public ImageView audio;

        public CollectionListViewHolder(@NonNull View view) {
            super(view);
            lemma = view.findViewById(R.id.lemma);
            phonetic = view.findViewById(R.id.phonetic);
            senses_senior = view.findViewById(R.id.senses_senior);
            lastModify = view.findViewById(R.id.last_modify);
            degree = view.findViewById(R.id.degree);
            audio = view.findViewById(R.id.audio);
            layoutAudio = view.findViewById(R.id.layout_audio);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
