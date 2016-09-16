package com.example.ellioc.watchlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ViewWatchListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ViewWatchListAdapter mViewWatchListAdapter;
    private WatchListDbHelper watchListDbHelper;
    private ArrayList<OmdbObject> mDataset;
    private RecyclerView.LayoutManager mLayoutManager;
    private View fragmentView;


    public ViewWatchListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ViewWatchListFragment newInstance() {
        return new ViewWatchListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchListDbHelper = new WatchListDbHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =  inflater.inflate(R.layout.fragment_view_watch_list, container, false);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
        mViewWatchListAdapter = new ViewWatchListAdapter(mDataset, getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mViewWatchListAdapter);
        mDataset = new ArrayList<>(watchListDbHelper.getAllOmdbObjects());
        mViewWatchListAdapter.updateEntries(mDataset);
        return fragmentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
