package com.llj.work.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.llj.work.R;
import com.llj.work.adapter.VocabularyListAdapter;
import com.llj.work.bean.Vocabulary;
import com.llj.work.database.VocabularyFactory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressLint("NonConstantResourceId")
public class DictionaryFragment extends Fragment implements VocabularyListAdapter.OnVocabularyOperationListener {

    private static final String TAG = "DictionaryFragment";
    private RecyclerView vocabularyList;
    private SmartRefreshLayout refreshLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary, container, true);
        vocabularyList = view.findViewById(R.id.vocabulary_list);
        //设置适配器
        VocabularyListAdapter adapter = new VocabularyListAdapter(getContext(), getAllVocabulary());
        adapter.setOnVocabularyOperationListener(this);
        vocabularyList.setAdapter(adapter);
        //添加item的分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        vocabularyList.addItemDecoration(decoration);

        refreshLayout = view.findViewById(R.id.refresher);
        refreshLayout.setEnableRefresh(true);//是否可下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {//下拉刷新监听
            adapter.resetData(getAllVocabulary());
            refreshLayout.finishRefresh(500);
        });

        refreshLayout.setEnableLoadMore(true);//是否可以下拉加载更多
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {//下拉加载更多监听
            adapter.addData(getAllVocabulary());
            refreshLayout.finishLoadMore(500);
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 创建右上角ActionVar的选项（排序，关于）
     * 并通过onOptionsItemSelected对选项进行监听
     *
     * @param menu     ActionBar的按钮
     * @param inflater 布局管理
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_action, menu);
    }

    /**
     * 对选项（排序，关于）进行监听
     *
     * @param item 已设定的按钮
     * @return 事件的结果，是否被消费
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort://点击排序
                Toast.makeText(getContext(), "排序", Toast.LENGTH_LONG).show();
                return true;
            case R.id.about://点击关于
                Toast.makeText(getContext(), "关于", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NotNull
    private ArrayList<Vocabulary> getAllVocabulary() {
        return VocabularyFactory.getInstance(getContext()).getAllVocabulary();
    }

    @Override
    public void onCollect(Integer id, boolean collected) {
//        if (collected) {
//            Toast.makeText(getContext(), id + "收藏", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getContext(), id + "取消收藏", Toast.LENGTH_LONG).show();
//        }
        VocabularyFactory.getInstance(getContext()).doCollectOperation(id, collected);
    }
}