package com.example.diadraw.Presenters;

import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.R;
import com.example.diadraw.Views.ChooseFileListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseFilePresenter {

    private static ChooseFilePresenter presenter;

    public static ChooseFilePresenter getPresenter(Context context) {
        if (presenter == null) {
            presenter = new ChooseFilePresenter(context);
        }
        return presenter;
    }

    private ChooseFilePresenter(Context context) {
        this.context = context;
    }

    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private Dialog dialog;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    private FileService fileService = new FileService();
    private Context context;

    public Context getContext() {
        return context;
    }

    public List<FileModel> getFileList() {
        return getFiles();
    }

    public void createFile() {
        String fileName = ((EditText) dialog.findViewById(R.id.editTextFileName)).getText().toString();
        ((EditText) dialog.findViewById(R.id.editTextFileName)).setText("");
        if (checkFileName(fileName)) {
            try {
                fileService.createFile(context, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }

    public void updateRecyclerView() {
        recyclerView.setAdapter(new ChooseFileListAdapter(getFiles()));
    }

    public void showDialog() {
        dialog.show();
    }

    public void cancel() {
        ((EditText) dialog.findViewById(R.id.editTextFileName)).setText("");
        dialog.dismiss();
    }

    private List<FileModel> getFiles() {
        try {
            return fileService.getFilesList(context);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean checkFileName(String fileName) {
        List<FileModel> list;
        list = getFiles();
        for (FileModel fileModel : list) {
            if (fileModel.getName().equals(fileName)) {
                return false;
            }
        }
        if (fileName.length() < 1 || fileName.length() > 20) {
            return false;
        }
        if (!fileName.matches("\\w{1}[\\w!@\\$%&\\*\\+\\-_]*")) {
            return false;
        }
        return true;
    }
}
