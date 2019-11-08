package com.example.diadraw.Presenters;

import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.R;
import com.example.diadraw.Views.ChooseFileListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    private FileService fileService = new FileService();
    private Context context;

    private List<FileModel> list = new ArrayList<>();

    public MainPresenter(Context context){
        this.context=context;
    }

    public List<FileModel> getFileList() {
        return getFiles();
    }

    public void createFile(Dialog dialog) {
        String fileName = ((EditText) dialog.findViewById(R.id.editTextFileName)).getText().toString();
        ((EditText) dialog.findViewById(R.id.editTextFileName)).setText("");
        try {
            fileService.createFile(context, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    public void updateRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new ChooseFileListAdapter(getFiles()));
    }

    public void showDialog(Dialog dialog) {
        dialog.show();
    }

    public void cancel(Dialog dialog) {
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
}
