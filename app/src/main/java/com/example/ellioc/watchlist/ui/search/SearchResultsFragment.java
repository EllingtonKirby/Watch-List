package com.example.ellioc.watchlist.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ellioc.watchlist.R;
import com.example.ellioc.watchlist.dagger.components.LandingComponent;
import com.example.ellioc.watchlist.dagger.scope.Injector;
import com.example.ellioc.watchlist.data.api.OmdbIntentService;
import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.example.ellioc.watchlist.ui.OmdbResultReceiver;
import com.example.ellioc.watchlist.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends BaseFragment<SearchFragmentPresenter, SearchResultsView>
        implements OmdbResultReceiver.Receiver {
    private static final String ARG_PARAM_QUERY = "query";
    private String mParamQuery;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
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
        mSearchResultsAdapter = new SearchResultsAdapter(mDataset, getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mSearchResultsAdapter);
    }

    @Override
    protected void initializeDependencyInjector() {
        Injector.obtain(getContext(), LandingComponent.class).inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_results;
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
        OmdbIntentService.startActionGetMoviesByTitle(getActivity(), mParamQuery, resultReceiver);
    }

    public void onReceiveResult(int val, Bundle b){
        if(val == 0){
            try {
                mDataset = new ArrayList<>((List<OmdbObject>)b.getSerializable("results"));
                mSearchResultsAdapter.updateEntries(mDataset);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            TextView textView = (TextView) fragmentView.findViewById(R.id.empty);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
