package com.example.diadraw.Presenters;

import android.app.Dialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.R;
import com.example.diadraw.Views.ChooseFileListAdapter;

import java.io.IOException;

public class ChooseFileListPresenter {
    private FileService fileService = new FileService();
    private ChooseFileListAdapter.ChooseFileListViewHolder holder;
    private int position;

    public ChooseFileListPresenter(ChooseFileListAdapter.ChooseFileListViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;
    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.popupmenu_choose_file);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                RecyclerView recyclerView;
                Dialog dialog;
                try {
                    View v = holder.itemView.getRootView();
                    FileModel fileModel = fileService.getFilesList(v.getContext()).get(position);
                    if (item.getItemId() == R.id.rename_popup_menu_choose_file) {
                        return true;
                    } else if (item.getItemId() == R.id.delete_popup_menu_choose_file) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });

        popupMenu.show();
    }

}
