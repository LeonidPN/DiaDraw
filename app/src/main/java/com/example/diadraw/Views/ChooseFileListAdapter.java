package com.example.diadraw.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.Presenters.ChooseFileListPresenter;
import com.example.diadraw.R;

import java.util.List;

public class ChooseFileListAdapter extends RecyclerView.Adapter<ChooseFileListAdapter.ChooseFileListViewHolder> {

    public List<FileModel> filesList;

    public static class ChooseFileListViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public ChooseFileListViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public ChooseFileListAdapter(List<FileModel> filesList) {
        this.filesList = filesList;
    }

    @Override
    public ChooseFileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_file_list, parent, false);
        ChooseFileListViewHolder holder = new ChooseFileListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChooseFileListViewHolder holder, int position) {
        final FileModel file = filesList.get(position);
        final ChooseFileListPresenter presenter = new ChooseFileListPresenter(holder, position);
        ((TextView) holder.view.findViewById(R.id.textViewFileName)).setText(file.getName());
        ((TextView) holder.view.findViewById(R.id.textViewDate)).setText(file.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }
}
