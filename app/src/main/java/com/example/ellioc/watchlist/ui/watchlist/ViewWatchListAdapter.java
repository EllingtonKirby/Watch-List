package com.example.ellioc.watchlist.ui.watchlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ellioc.watchlist.R;
import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.example.ellioc.watchlist.data.model.WatchListDbHelper;
import com.example.ellioc.watchlist.ui.detail.DetailViewActivity;

import java.util.ArrayList;

/**
 * Created by Ellioc on 9/15/2016.
 */
public class ViewWatchListAdapter extends RecyclerView.Adapter<ViewWatchListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OmdbObject> mDataset = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageButton closeButton;
        public TextView title;
        public TextView year;
        public TextView type;
        public CardView card;

        public ViewHolder(View v){
            super(v);
            closeButton = (ImageButton) v.findViewById(R.id.remove_watchlist);
            title = (TextView) v.findViewById(R.id.listTitle);
            year = (TextView) v.findViewById(R.id.listYear);
            type = (TextView) v.findViewById(R.id.listItemType);
            card = (CardView) v.findViewById(R.id.card_view);
        }
    }

    public ViewWatchListAdapter(ArrayList<OmdbObject> myDataset, Context context) {
        if(myDataset != null)
            mDataset = myDataset;
        else
            mDataset = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public ViewWatchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_list_item_layout, parent, false);
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
                //Start a DetailView activity
                Intent intent = new Intent(mContext, DetailViewActivity.class);
                intent.putExtra(DetailViewActivity.OMDB_LIST_KEY, mDataset);
                intent.putExtra(DetailViewActivity.START_KEY, position);
                mContext.startActivity(intent);
            }
        });

        holder.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Alert!!!!");
                builder.setMessage("Are you sure you want to remove "+ mDataset.get(position).getTitle());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WatchListDbHelper watchListDbHelper = new WatchListDbHelper(mContext);
                        watchListDbHelper.deleteObject(mDataset.get(position));
                        mDataset.remove(position);
                        updateEntries(mDataset);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.create().show();
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
