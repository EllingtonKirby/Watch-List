package com.example.ellioc.watchlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ellioc on 9/3/2016.
 */
public class WatchListViewListAdapter extends ArrayAdapter<OmdbObject>{
    private Context mContext;

    private LayoutInflater mLayoutInflater;
    private ArrayList<OmdbObject> mEntries = new ArrayList<>();
    HashMap<OmdbObject, Integer> mIdMap = new HashMap<>();
    View.OnTouchListener mTouchListener;

    private static class ViewHolder {
        private TextView itemView;
    }

    public WatchListViewListAdapter(Context context, int textViewResourceId, ArrayList<OmdbObject> mEntries,
                                    View.OnTouchListener listener) {
        super(context, textViewResourceId, mEntries);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTouchListener = listener;
        for (int i = 0; i < mEntries.size(); ++i) {
            mIdMap.put(mEntries.get(i), i);
        }
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
        OmdbObject item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RelativeLayout view = (RelativeLayout) mLayoutInflater.inflate(R.layout.omdb_list_item_layout, parent, false);
//        if(convertView == null){
//            itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.omdb_list_item_layout, parent, false);
//        }
//        else{
//            itemView = (RelativeLayout) convertView;
//        }
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
        if(view != convertView) {
            TextView titleView = (TextView) view.findViewById(R.id.listTitle);
            titleView.setText(mEntries.get(position).getTitle());
            TextView yearView = (TextView) view.findViewById(R.id.listYear);
            yearView.setText(mEntries.get(position).getYear());
            TextView typeView = (TextView) view.findViewById(R.id.listItemType);
            typeView.setText(mEntries.get(position).getType());

            view.setOnTouchListener(mTouchListener);
        }
        return view;
    }

//    public void upDateEntries(ArrayList<OmdbObject> entries) {
//        mEntries = entries;
//
//        notifyDataSetChanged();
//    }
}
