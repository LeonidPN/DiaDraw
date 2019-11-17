package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.diadraw.Presenters.ChooseFilePresenter;
import com.example.diadraw.R;

public class ChooseFileActivity extends AppCompatActivity {

    private ChooseFilePresenter presenter = ChooseFilePresenter.getPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);

        presenter.setRecyclerView((RecyclerView)findViewById(R.id.recyclerVewChooseFile));
        presenter.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        presenter.getRecyclerView().setAdapter(new ChooseFileListAdapter(presenter.getFileList()));

        presenter.setDialog(new Dialog(this));
        presenter.getDialog().setContentView(R.layout.file_creation_dialog);
        presenter.getDialog().findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createFile();
                presenter.updateRecyclerView();
            }
        });
        presenter.getDialog().findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cancel();
            }
        });

        presenter.setImageButton((ImageButton)findViewById(R.id.buttonCreateFile));
        presenter.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showDialog();
            }
        });
    }
}
