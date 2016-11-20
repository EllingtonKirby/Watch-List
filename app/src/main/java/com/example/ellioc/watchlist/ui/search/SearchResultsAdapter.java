package com.example.ellioc.watchlist.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ellioc.watchlist.R;
import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.example.ellioc.watchlist.ui.detail.DetailViewActivity;

import java.util.ArrayList;

/**
 * Created by Ellioc on 9/15/2016.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<OmdbObject> mDataset = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView year;
        public TextView type;
        public CardView card;
        public ViewHolder(View view){
            super(view);
            this.title = (TextView) view.findViewById(R.id.listTitle);
            this.year = (TextView) view.findViewById(R.id.listYear);
            this.type = (TextView) view.findViewById(R.id.listItemType);
            this.card = (CardView) view.findViewById(R.id.card_view);
        }

    }

    public SearchResultsAdapter(ArrayList<OmdbObject> myDataset, Context context) {
        if(myDataset != null)
            mDataset = myDataset;
        else
            mDataset = new ArrayList<>();
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item_layout, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.year.setText(mDataset.get(position).getYear());
        holder.type.setText(mDataset.get(position).getType());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a detail view activity
                Intent intent = new Intent(mContext, DetailViewActivity.class);
                intent.putExtra(DetailViewActivity.OMDB_LIST_KEY, mDataset);
                intent.putExtra(DetailViewActivity.START_KEY, position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateEntries(ArrayList<OmdbObject> entries){
        mDataset = entries;
        notifyDataSetChanged();
    }
}
