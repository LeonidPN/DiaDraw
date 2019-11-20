package com.example.diadraw.Presenters;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainPresenter {

    private static MainPresenter presenter;

    public static MainPresenter getPresenter(Context context) {
        if (presenter == null) {
            presenter = new MainPresenter(context);
        }
        return presenter;
    }

    private FileService fileService = new FileService();
    private Context context;
    private FileModel model;

    private View selected_item = null;
    private int offset_x = 0;
    private int offset_y = 0;
    private boolean touchFlag = false;
    private boolean dropFlag = false;
    private ViewGroup.LayoutParams imageParams;

    private View rootView;

    private ScrollView scrollView;

    private ImageView imageViewStart;
    private ImageView imageViewEnd;
    private ImageView imageViewActivity;
    private ImageView imageViewInput;
    private ImageView imageViewOutput;
    private ImageView imageViewCondition;
    private ImageView imageViewCycleStart;
    private ImageView imageViewCycleEnd;

    private FloatingActionButton buttonMenu;
    private FloatingActionButton buttonFigures;
    private FloatingActionButton buttonSave;
    private FloatingActionButton buttonHome;

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public void setImageViewStart(ImageView imageViewStart) {
        this.imageViewStart = imageViewStart;
    }

    public void setImageViewEnd(ImageView imageViewEnd) {
        this.imageViewEnd = imageViewEnd;
    }

    public void setImageViewActivity(ImageView imageViewActivity) {
        this.imageViewActivity = imageViewActivity;
    }

    public void setImageViewInput(ImageView imageViewInput) {
        this.imageViewInput = imageViewInput;
    }

    public void setImageViewOutput(ImageView imageViewOutput) {
        this.imageViewOutput = imageViewOutput;
    }

    public void setImageViewCondition(ImageView imageViewCondition) {
        this.imageViewCondition = imageViewCondition;
    }

    public void setImageViewCycleStart(ImageView imageViewCycleStart) {
        this.imageViewCycleStart = imageViewCycleStart;
    }

    public void setImageViewCycleEnd(ImageView imageViewCycleEnd) {
        this.imageViewCycleEnd = imageViewCycleEnd;
    }

    public FloatingActionButton getButtonMenu() {
        return buttonMenu;
    }

    public void setButtonMenu(FloatingActionButton buttonMenu) {
        this.buttonMenu = buttonMenu;
    }

    public FloatingActionButton getButtonFigures() {
        return buttonFigures;
    }

    public void setButtonFigures(FloatingActionButton buttonFigures) {
        this.buttonFigures = buttonFigures;
    }

    public FloatingActionButton getButtonSave() {
        return buttonSave;
    }

    public void setButtonSave(FloatingActionButton buttonSave) {
        this.buttonSave = buttonSave;
    }

    public FloatingActionButton getButtonHome() {
        return buttonHome;
    }

    public void setButtonHome(FloatingActionButton buttonHome) {
        this.buttonHome = buttonHome;
    }

    private MainPresenter(Context context) {
        this.context = context;
    }

    public void showFiguresPanel() {
        scrollView.setVisibility(View.VISIBLE);
    }

    public void loadData(String fileName) {
        try {
            FileModel fileModel = fileService.getFile(context, fileName);
            model = fileModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            fileService.changeFile(context, model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImageViewsOnTouch() {
        imageViewActivity.setOnTouchListener(touchImage);
        imageViewCondition.setOnTouchListener(touchImage);
        imageViewCycleEnd.setOnTouchListener(touchImage);
        imageViewCycleStart.setOnTouchListener(touchImage);
        imageViewEnd.setOnTouchListener(touchImage);
        imageViewInput.setOnTouchListener(touchImage);
        imageViewOutput.setOnTouchListener(touchImage);
        imageViewStart.setOnTouchListener(touchImage);
    }

    private View.OnTouchListener touchImage = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    touchFlag = true;
                    offset_x = (int) event.getX();
                    offset_y = (int) event.getY();
                    selected_item = v;
                    imageParams = v.getLayoutParams();
                    break;
                case MotionEvent.ACTION_UP:
                    selected_item = null;
                    touchFlag = false;
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    public void setRootViewTouchListener(){
        rootView.setOnTouchListener(touchView);
    }

    private View.OnTouchListener touchView = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

}
