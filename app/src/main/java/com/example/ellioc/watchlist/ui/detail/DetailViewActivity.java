package com.example.ellioc.watchlist.ui.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ellioc.watchlist.R;
import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.example.ellioc.watchlist.ui.CustomViewPager;
import com.example.ellioc.watchlist.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailViewActivity extends BaseActivity<DetailViewPresenter, DetailViewView>{
    public static String OMDB_LIST_KEY = "key_object";
    public static String START_KEY = "start";
    private CustomViewPager mPager;
    private DetailViewPagerAdapter mPagerAdapter;
    private ArrayList<OmdbObject> mDataSet;
    private int start = 0;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        start = b.getInt(START_KEY);
        mDataSet = new ArrayList<>((List<OmdbObject>)b.getSerializable(OMDB_LIST_KEY));
        mPager = (CustomViewPager) findViewById(R.id.pager);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPagerAdapter = new DetailViewPagerAdapter(getSupportFragmentManager(), mDataSet, mPager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(start);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_view;
    }

    @Override
    protected void initializeDependencyInjector() {

    }


}
