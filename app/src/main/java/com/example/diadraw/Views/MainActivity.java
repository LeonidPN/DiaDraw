package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.diadraw.Presenters.MainPresenter;
import com.example.diadraw.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private Dialog dialog;

    private MainPresenter presenter = new MainPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerVewChooseFile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ChooseFileListAdapter(presenter.getFileList()));

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.file_creation_dialog);
        dialog.findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createFile(dialog);
                presenter.updateRecyclerView(recyclerView);
            }
        });
        dialog.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cancel(dialog);
            }
        });

        imageButton = findViewById(R.id.buttonCreateFile);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showDialog(dialog);
            }
        });
    }
}
