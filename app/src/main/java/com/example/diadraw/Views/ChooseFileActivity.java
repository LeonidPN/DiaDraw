package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.diadraw.Presenters.ChooseFilePresenter;
import com.example.diadraw.R;

public class ChooseFileActivity extends AppCompatActivity {

    private ChooseFilePresenter presenter = ChooseFilePresenter.getPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
        } else {
            int PERMISSION_REQUEST_CODE = 1;
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_CODE);
        }

        presenter.setRecyclerView((RecyclerView) findViewById(R.id.recyclerVewChooseFile));
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

        presenter.setImageButton((ImageButton) findViewById(R.id.buttonCreateFile));
        presenter.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showDialog();
            }
        });
    }
}
