package com.example.ellioc.watchlist;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ellioc on 9/3/2016.
 */
public class WatchListViewListAdapter extends BaseAdapter{
    private Context mContext;

    private LayoutInflater mLayoutInflater;
    private ArrayList<OmdbObject> mEntries = new ArrayList<>();
    HashMap<OmdbObject, Integer> mIdMap = new HashMap<>();
    View.OnTouchListener mTouchListener;

    private static class ViewHolder {
        private TextView itemView;
    }

    public WatchListViewListAdapter(Context context) {
//        super(context, textViewResourceId, mEntries);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mTouchListener = listener;
//        for (int i = 0; i < mEntries.size(); ++i) {
//            mIdMap.put(mEntries.get(i), i);
//        }
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }
    @Override
    public OmdbObject getItem(int position) {
        return mEntries.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        RelativeLayout itemView;
        if(convertView == null){
            itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.watch_list_item_layout, parent, false);
        }
        else{
            itemView = (RelativeLayout) convertView;
        }
//        if (convertView == null) {
//            convertView = LayoutInflater.from(this.getContext())
//                .inflate(R.layout.omdb_list_item_layout, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.itemView = (TextView) convertView.findViewById(R.id.ItemView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

//        TextView titleView = (TextView) itemView.findViewById(R.id.listTitle);
//        titleView.setText(mEntries.get(position).getTitle());
//        TextView yearView  = (TextView) itemView.findViewById(R.id.listYear);
//        yearView.setText(mEntries.get(position).getYear());
//        TextView typeView  = (TextView) itemView.findViewById(R.id.listItemType);
//        typeView.setText(mEntries.get(position).getType());
//
//        itemView.setOnTouchListener(mTouchListener);
//        return itemView;
        TextView titleView = (TextView) itemView.findViewById(R.id.listTitle);
        titleView.setText(mEntries.get(position).getTitle());
        TextView yearView = (TextView) itemView.findViewById(R.id.listYear);
        yearView.setText(mEntries.get(position).getYear());
        TextView typeView = (TextView) itemView.findViewById(R.id.listItemType);
        typeView.setText(mEntries.get(position).getType());

        ImageButton imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchListDbHelper watchListDbHelper = new WatchListDbHelper(mContext);
                watchListDbHelper.deleteObject(mEntries.get(position));
                mEntries.remove(position);
                upDateEntries(mEntries);
            }
        });

//            itemView.setOnTouchListener(mTouchListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OmdbSpecificMovieActivity.class);
                intent.putExtra("omdb_object_key", mEntries.get(position).getMovieId());
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
