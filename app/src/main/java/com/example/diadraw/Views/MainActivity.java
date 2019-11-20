package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.diadraw.Presenters.MainPresenter;
import com.example.diadraw.R;

public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getPresenter(this);
        findViewById(R.id.scrollView).setVisibility(View.INVISIBLE);
    }
}
