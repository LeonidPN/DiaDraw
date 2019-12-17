package com.example.diadraw.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.diadraw.Presenters.MainPresenter;
import com.example.diadraw.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getPresenter(this);
        String fileName = getIntent().getStringExtra("filename");
        presenter.loadData(fileName);

        presenter.setDrawView(new DrawView(this));

        ConstraintLayout layout = findViewById(R.id.constarintlayout);
        layout.addView(presenter.getDrawView(), 0);

        presenter.setScrollView((ScrollView) findViewById(R.id.scrollView));

        presenter.setImageViewActivity((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureActivity));
        presenter.setImageViewCondition((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureCondition));
        presenter.setImageViewEnd((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureEnd));
        presenter.setImageViewInput((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureInput));
        presenter.setImageViewOutput((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureOutput));
        presenter.setImageViewStart((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureStart));
        presenter.setImageViewCycleStart((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureCycleStart));
        presenter.setImageViewCycleEnd((ImageView) findViewById(R.id.scrollView)
                .findViewById(R.id.linerLayout).findViewById(R.id.imageViewFigureCycleEnd));

        presenter.setImageViewsOnClick();

        presenter.setButtonFigures((FloatingActionButton) findViewById(R.id.floatingActionButtonFigures));
        presenter.getButtonFigures().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showFiguresPanel();
            }
        });
        presenter.setButtonHome((FloatingActionButton) findViewById(R.id.floatingActionButtonHome));
        presenter.getButtonHome().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter.setButtonMenu((FloatingActionButton) findViewById(R.id.floatingActionButtonMenu));
        presenter.getButtonMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showPopupMenu(v);
            }
        });
        presenter.setButtonSave((FloatingActionButton) findViewById(R.id.floatingActionButtonSave));
        presenter.getButtonSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveFile();
            }
        });

        presenter.setRootView(findViewById(android.R.id.content).getRootView());
        presenter.setRootViewTouchListener();

        presenter.draw();

        presenter.setDialog(new Dialog(this));
        presenter.getDialog().setContentView(R.layout.text_change_dialog);
        presenter.getDialog().findViewById(R.id.buttonChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeText();
                presenter.draw();
            }
        });
        presenter.getDialog().findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cancel();
            }
        });
    }
}
