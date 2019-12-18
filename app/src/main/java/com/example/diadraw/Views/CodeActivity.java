package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.diadraw.Presenters.CodePresenter;
import com.example.diadraw.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CodeActivity extends AppCompatActivity {

    private CodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        presenter = CodePresenter.getPresenter(this);
        String fileName = getIntent().getStringExtra("filename");

        presenter.setButtonHome((FloatingActionButton) findViewById(R.id.floatingActionButtonHome));
        presenter.getButtonHome().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        presenter.setCodeTextView((TextView) findViewById(R.id.textViewCode));

        presenter.loadData(fileName);
    }
}
