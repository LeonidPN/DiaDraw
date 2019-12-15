package com.example.diadraw.Presenters;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.Figure;
import com.example.diadraw.Models.WorkModels.FigureType;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.R;
import com.example.diadraw.Views.DrawView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainPresenter {

    private static MainPresenter presenter;

    public static MainPresenter getPresenter(Context context) {
        if (presenter == null) {
            presenter = new MainPresenter(context);
        }
        return presenter;
    }

    private boolean showFigurePanelFlag = false;

    private FileService fileService = new FileService();
    private Context context;
    private FileModel model;

    private float x;
    private float y;
    private Figure selectedFigure = null;
    private boolean dragFlag = false;
    private float dragX = 0;
    private float dragY = 0;

    private View rootView;

    private DrawView drawView;

    private ScrollView scrollView;

    private ImageView imageViewStart;
    private ImageView imageViewEnd;
    private ImageView imageViewActivity;
    private ImageView imageViewInput;
    private ImageView imageViewOutput;
    private ImageView imageViewCondition;

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

    public DrawView getDrawView() {
        return drawView;
    }

    public void setDrawView(DrawView drawView) {
        this.drawView = drawView;
        this.drawView.setFigures(model.getFigures());
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
        if (!showFigurePanelFlag) {
            scrollView.setVisibility(View.VISIBLE);
            showFigurePanelFlag = !showFigurePanelFlag;
        } else {
            scrollView.setVisibility(View.INVISIBLE);
            showFigurePanelFlag = !showFigurePanelFlag;
        }
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

    public void setImageViewsOnClick() {
        imageViewActivity.setOnClickListener(clickImage);
        imageViewCondition.setOnClickListener(clickImage);
        imageViewEnd.setOnClickListener(clickImage);
        imageViewInput.setOnClickListener(clickImage);
        imageViewOutput.setOnClickListener(clickImage);
        imageViewStart.setOnClickListener(clickImage);
    }

    private View.OnClickListener clickImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int figureId = v.getId();
            float x = drawView.getWidth() / 2;
            float y = drawView.getHeight() / 2;
            boolean flag;
            long id = 1;
            if (model.getFigures().size() > 0) {
                id = model.getFigures().get(model.getFigures().size() - 1).getId() + 1;
            }
            switch (figureId) {
                case R.id.imageViewFigureActivity:
                    model.getFigures().add(new Figure(id, FigureType.ACTIVITY, x, y));
                    break;
                case R.id.imageViewFigureCondition:
                    model.getFigures().add(new Figure(id, FigureType.CONDITION, x, y));
                    break;
                case R.id.imageViewFigureEnd:
                    flag = true;
                    for (Figure figure : model.getFigures()) {
                        if (figure.getType().equals(FigureType.END)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        model.getFigures().add(new Figure(id, FigureType.END, x, y));
                    }
                    break;
                case R.id.imageViewFigureInput:
                    model.getFigures().add(new Figure(id, FigureType.INPUT, x, y));
                    break;
                case R.id.imageViewFigureOutput:
                    model.getFigures().add(new Figure(id, FigureType.OUTPUT, x, y));
                    break;
                case R.id.imageViewFigureStart:
                    flag = true;
                    for (Figure figure : model.getFigures()) {
                        if (figure.getType().equals(FigureType.START)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        model.getFigures().add(new Figure(id, FigureType.START, x, y));
                    }
                    break;
            }
            showFiguresPanel();
            drawView.setFigures(model.getFigures());
            drawView.invalidate();
        }
    };

    public void setRootViewTouchListener() {
        drawView.setOnTouchListener(touchView);
        drawView.setOnClickListener(clickView);
        drawView.setOnLongClickListener(longClickView);
    }

    private View.OnTouchListener touchView = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float evX = event.getX();
            float evY = event.getY();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    if (selectedFigure != null) {
                        float figureX = 0;
                        float figureY = 0;
                        float figureWidth = 0;
                        float figureHeight = 0;
                        switch (selectedFigure.getType()) {
                            case FigureType.ACTIVITY:
                                figureX = selectedFigure.getX() - 250 / 2;
                                figureY = selectedFigure.getY() - 100 / 2;
                                figureWidth = 500;
                                figureHeight = 220;
                                break;
                            case FigureType.START:
                            case FigureType.END:
                                figureX = selectedFigure.getX() - 250 / 2;
                                figureY = selectedFigure.getY() - 50 / 2;
                                figureWidth = 500;
                                figureHeight = 100;
                                break;
                            case FigureType.INPUT:
                            case FigureType.OUTPUT:
                                figureX = selectedFigure.getX() - 340 / 2;
                                figureY = selectedFigure.getY() - 200 / 2;
                                figureWidth = 680;
                                figureHeight = 300;
                                break;
                            case FigureType.CONDITION:
                                figureX = selectedFigure.getX() - 480 / 2;
                                figureY = selectedFigure.getY() - 200 / 2;
                                figureWidth = 960;
                                figureHeight = 400;
                                break;
                        }
                        figureWidth /= 2;
                        figureHeight /= 2;
                        float buttonX = figureX - 10;
                        float buttonY = figureY - 10 - 40 * 3;
                        if (x > buttonX && y > buttonY && x < buttonX + 40 * 3 && y < buttonY + 40 * 3) {
                            dragFlag = true;
                            dragX = x - selectedFigure.getX();
                            dragY = y - selectedFigure.getY();
                            return true;
                        }
                        buttonX = figureX + figureWidth - 40 * 3;
                        if (x > buttonX && y > buttonY && x < buttonX + 40 * 3 && y < buttonY + 40 * 3) {
                            model.getFigures().remove(selectedFigure);
                            selectedFigure = null;
                            drawView.setFigures(model.getFigures());
                            drawView.setSelectedFigure(selectedFigure);
                            drawView.invalidate();
                            return true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (dragFlag) {
                        selectedFigure.setX(evX - dragX);
                        selectedFigure.setY(evY - dragY);
                        drawView.setSelectedFigure(selectedFigure);
                        drawView.invalidate();
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (dragFlag) {
                        dragFlag = false;
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private View.OnClickListener clickView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<Figure> list = (ArrayList<Figure>) model.getFigures().clone();
            Collections.reverse(list);
            float figureX = 0;
            float figureY = 0;
            float figureWidth = 0;
            float figureHeight = 0;
            selectedFigure = null;
            for (Figure figure : list) {
                switch (figure.getType()) {
                    case FigureType.ACTIVITY:
                        figureX = figure.getX() - 250 / 2;
                        figureY = figure.getY() - 100 / 2;
                        figureWidth = 500;
                        figureHeight = 220;
                        break;
                    case FigureType.START:
                    case FigureType.END:
                        figureX = figure.getX() - 250 / 2;
                        figureY = figure.getY() - 50 / 2;
                        figureWidth = 500;
                        figureHeight = 100;
                        break;
                    case FigureType.INPUT:
                    case FigureType.OUTPUT:
                        figureX = figure.getX() - 340 / 2;
                        figureY = figure.getY() - 200 / 2;
                        figureWidth = 680;
                        figureHeight = 300;
                        break;
                    case FigureType.CONDITION:
                        figureX = figure.getX() - 480 / 2;
                        figureY = figure.getY() - 200 / 2;
                        figureWidth = 960;
                        figureHeight = 400;
                        break;
                }
                figureWidth /= 2;
                figureHeight /= 2;
                if (x > figureX && y > figureY && x < figureX + figureWidth && y < figureY + figureHeight) {
                    selectedFigure = figure;
                    break;
                }
            }
            drawView.setAddingLine(false);
            drawView.setSelectedFigure(selectedFigure);
            drawView.invalidate();
        }
    };

    private View.OnLongClickListener longClickView = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            drawView.setAddingLine(false);
            if (selectedFigure != null) {
                float figureX = 0;
                float figureY = 0;
                float figureWidth = 0;
                float figureHeight = 0;
                switch (selectedFigure.getType()) {
                    case FigureType.ACTIVITY:
                        figureX = selectedFigure.getX() - 250 / 2;
                        figureY = selectedFigure.getY() - 100 / 2;
                        figureWidth = 500;
                        figureHeight = 220;
                        break;
                    case FigureType.START:
                    case FigureType.END:
                        figureX = selectedFigure.getX() - 250 / 2;
                        figureY = selectedFigure.getY() - 50 / 2;
                        figureWidth = 500;
                        figureHeight = 100;
                        break;
                    case FigureType.INPUT:
                    case FigureType.OUTPUT:
                        figureX = selectedFigure.getX() - 340 / 2;
                        figureY = selectedFigure.getY() - 200 / 2;
                        figureWidth = 680;
                        figureHeight = 300;
                        break;
                    case FigureType.CONDITION:
                        figureX = selectedFigure.getX() - 480 / 2;
                        figureY = selectedFigure.getY() - 200 / 2;
                        figureWidth = 960;
                        figureHeight = 400;
                        break;
                }
                figureWidth /= 2;
                figureHeight /= 2;
                if (x > figureX && y > figureY && x < figureX + figureWidth && y < figureY + figureHeight) {
                    drawView.setAddingLine(true);
                    drawView.invalidate();
                    return true;
                }
            }
            return false;
        }
    };

}
