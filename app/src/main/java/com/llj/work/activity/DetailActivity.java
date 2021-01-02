package com.llj.work.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.llj.work.R;
import com.llj.work.bean.Vocabulary;

public class DetailActivity extends AppCompatActivity {

    private Vocabulary vocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        vocabulary = getIntent().getParcelableExtra("vocabulary");

        Toast.makeText(this, vocabulary.getLemma(), Toast.LENGTH_LONG).show();
    }
}