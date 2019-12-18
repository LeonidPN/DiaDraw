package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.diadraw.Presenters.SettingsPresenter;
import com.example.diadraw.R;

public class SettingsActivity extends AppCompatActivity {

    private SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        presenter = SettingsPresenter.getPresenter(this);
        presenter.setView(this);

        presenter.setButtonCancel((Button) findViewById(R.id.buttonCancel));
        presenter.getButtonCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter.setButtonSave((Button) findViewById(R.id.buttonSave));
        presenter.getButtonSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.save();
                finish();
            }
        });

        presenter.setEditTextAutoSaveTime((EditText) findViewById(R.id.editTextAutoSaveTime));

        presenter.setImageViewBorderColor((ImageView) findViewById(R.id.imageViewBorderColor));
        presenter.setImageViewFontColor((ImageView) findViewById(R.id.imageViewFontColor));
        presenter.setImageViewFiguresColor((ImageView) findViewById(R.id.imageViewFiguresColor));
        presenter.setImageViewWorkAreaColor((ImageView) findViewById(R.id.imageViewWorkAreaColor));

        presenter.loadData();

        presenter.setImageClick();
    }
}
