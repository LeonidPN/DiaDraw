package com.example.diadraw.Presenters;

import android.app.Dialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.R;
import com.example.diadraw.Views.ChooseFileListAdapter;
import com.example.diadraw.Views.MainActivity;

import java.io.IOException;

public class ChooseFileListPresenter {
    private FileService fileService = new FileService();
    private ChooseFileListAdapter.ChooseFileListViewHolder holder;
    private int position;

    private ChooseFilePresenter presenter;

    public ChooseFileListPresenter(ChooseFileListAdapter.ChooseFileListViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;
        presenter = ChooseFilePresenter.getPresenter(null);
    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.popupmenu_choose_file);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    View v = holder.itemView.getRootView();
                    final FileModel fileModel = fileService.getFilesList(v.getContext()).get(position);
                    if (item.getItemId() == R.id.rename_popup_menu_choose_file) {
                        final Dialog dialog = new Dialog(presenter.getContext());
                        dialog.setContentView(R.layout.file_rename_dialog);
                        ((EditText) dialog.findViewById(R.id.editTextFileName)).setText(fileModel.getName());
                        dialog.findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    fileService.renameFile(v.getContext(), fileModel.getName(),
                                            ((EditText) dialog.findViewById(R.id.editTextFileName))
                                                    .getText().toString());
                                    dialog.dismiss();
                                    presenter.updateRecyclerView();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialog.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((EditText) dialog.findViewById(R.id.editTextFileName)).setText("");
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return true;
                    } else if (item.getItemId() == R.id.delete_popup_menu_choose_file) {
                        fileService.deleteFile(v.getContext(), fileModel.getName());
                        presenter.updateRecyclerView();
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

    public void openFile(){
        View v = holder.itemView.getRootView();
        try {
            final FileModel fileModel = fileService.getFilesList(v.getContext()).get(position);
            Intent intent =new Intent(v.getContext(), MainActivity.class);
            intent.putExtra("filename", fileModel.getName());
            presenter.getContext().startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
