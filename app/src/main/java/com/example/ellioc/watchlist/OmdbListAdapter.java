package com.example.ellioc.watchlist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ellioc on 8/31/2016.
 */
public class OmdbListAdapter extends BaseAdapter {

    private final String OMDB_OBJECT_KEY = "omdb_object_key";
    private Context mContext;

    private LayoutInflater mLayoutInflater;
    private ArrayList<OmdbObject> mEntries = new ArrayList<>();

    public OmdbListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }
    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        RelativeLayout itemView;
        if(convertView == null){
            itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.omdb_list_item_layout, parent, false);
        }
        else{
            itemView = (RelativeLayout) convertView;
        }
        TextView titleView = (TextView) itemView.findViewById(R.id.listTitle);
        titleView.setText(mEntries.get(position).getTitle());
        TextView yearView  = (TextView) itemView.findViewById(R.id.listYear);
        yearView.setText(mEntries.get(position).getYear());
        TextView typeView  = (TextView) itemView.findViewById(R.id.listItemType);
        typeView.setText(mEntries.get(position).getType());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OmdbSpecificMovieActivity.class);
                intent.putExtra(OMDB_OBJECT_KEY, mEntries.get(position).getMovieId());
                mContext.startActivity(intent);
            }
        });

        return itemView;
    }

    public void upDateEntries(ArrayList<OmdbObject> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}
