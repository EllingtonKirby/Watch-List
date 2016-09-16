package com.example.ellioc.watchlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment implements OmdbResultReceiver.Receiver{
    private static final String ARG_PARAM_QUERY = "query";
    private String mParamQuery;

    private RecyclerView recyclerView;
    private SearchResultsAdapter mSearchResultsAdapter;
    private ArrayList<OmdbObject> mDataset;
    private OmdbResultReceiver resultReceiver;
    private RecyclerView.LayoutManager mLayoutManager;
    private View fragmentView;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    public static SearchResultsFragment newInstance(String param1) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_QUERY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamQuery = getArguments().getString(ARG_PARAM_QUERY);
        }

        resultReceiver = new OmdbResultReceiver(new Handler());
        resultReceiver.setReceiver(this);

        initDataSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_search_results, container, false);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
        mSearchResultsAdapter = new SearchResultsAdapter(mDataset, getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mSearchResultsAdapter);

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

    private void initDataSet(){
        OmdbService.startActionGetMoviesByTitle(getActivity(), mParamQuery, resultReceiver);
    }

    public void onReceiveResult(int val, Bundle b){
        if(val == 0){
            Log.i("JSON", b.getString("results"));
            try {
                OmdbJSONParser jsonParser = new OmdbJSONParser();
                mDataset = new ArrayList<>(jsonParser.parseMovieObjects(new JSONArray(b.getString("results"))));
                mSearchResultsAdapter.updateEntries(mDataset);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        else{
            TextView textView = (TextView) fragmentView.findViewById(R.id.empty);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
