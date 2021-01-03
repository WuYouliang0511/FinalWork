package com.llj.work.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.llj.work.R;
import com.llj.work.adapter.FragmentPagerAdapter;
import com.llj.work.fragment.DictionaryFragment;
import com.llj.work.fragment.WorkbookFragment;

import java.util.ArrayList;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private BottomNavigationView navigationView;
    private ViewPager2 viewPager;

    private ArrayList<Fragment> fragments;
    private DictionaryFragment dictionaryFragment;
    private WorkbookFragment workbookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        dictionaryFragment = new DictionaryFragment();
        workbookFragment = new WorkbookFragment();
        fragments.add(dictionaryFragment);
        fragments.add(workbookFragment);

        navigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.pager);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(callback);
        //viewPager.setSaveEnabled(false);

        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navigationView.setItemIconTintList(null);
    }

    private final OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_dictionary:
                viewPager.setCurrentItem(0, true);
                //Toast.makeText(MainActivity.this, "dictionary", Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_workbook:
                viewPager.setCurrentItem(1, true);
                //Toast.makeText(MainActivity.this, "workbook", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    };

    private final ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            Log.d(TAG, "position: " + position);
            navigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Window window = getWindow();
        window.setStatusBarColor(Color.BLACK);
        //window.setNavigationBarColor(Color.WHITE);
        //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }
}