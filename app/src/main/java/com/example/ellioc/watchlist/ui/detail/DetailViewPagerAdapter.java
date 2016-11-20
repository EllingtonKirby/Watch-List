package com.example.ellioc.watchlist.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.example.ellioc.watchlist.ui.CustomViewPager;

import java.util.ArrayList;

/**
 * Created by Ellioc on 9/17/2016.
 */
public class DetailViewPagerAdapter extends FragmentStatePagerAdapter{

    private Fragment currentFragment;
    private ArrayList<OmdbObject> mDataset;
    private CustomViewPager mPager;

    public DetailViewPagerAdapter(FragmentManager fm, ArrayList<OmdbObject> entries, CustomViewPager mPager) {
        super(fm);
        mDataset = new ArrayList<>(entries);
        this.mPager = mPager;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new DetailViewFragment();
        Bundle args = new Bundle();
        if(i >= getCount()){
            mPager.setPagingEnabled(false);
            i = getCount() - 1;
        }
        args.putSerializable(DetailViewFragment.ARG_OBJECT, mDataset.get(i));
        args.putInt(DetailViewFragment.ARG_POS, i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataset.get(position).getTitle();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
