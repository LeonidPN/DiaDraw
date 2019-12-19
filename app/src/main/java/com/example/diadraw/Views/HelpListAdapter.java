package com.example.diadraw.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diadraw.R;

import java.util.Map;

public class HelpListAdapter extends RecyclerView.Adapter<HelpListAdapter.HelpListViewHolder> {

    public Map<Integer,String> helpList;

    public static class HelpListViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public HelpListViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public HelpListAdapter(Map<Integer,String> helpList) {
        this.helpList = helpList;
    }

    @Override
    public HelpListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help_dialog, parent, false);
        HelpListViewHolder holder = new HelpListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(HelpListViewHolder holder, int position) {
        int key = (int)helpList.keySet().toArray()[position];
        ((TextView) holder.view.findViewById(R.id.textView)).setText(helpList.get(key));
        ((ImageView) holder.view.findViewById(R.id.imageView)).setImageResource(key);
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }
}
