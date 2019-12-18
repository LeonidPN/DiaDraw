package com.example.diadraw.Presenters;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.Figure;
import com.example.diadraw.Models.WorkModels.FigureType;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.Models.WorkModels.Line;
import com.example.diadraw.Models.WorkModels.Point;
import com.example.diadraw.R;
import com.example.diadraw.Views.CodeActivity;
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
        if (context != null) {
            return presenter = new MainPresenter(context);
        } else {
            return presenter;
        }
    }

    private boolean showFigurePanelFlag = false;

    private FileService fileService = new FileService();
    private Context context;
    private FileModel model;

    private float x;
    private float y;
    private Figure selectedFigure = null;
    private Line selectedLine = null;
    private boolean dragFlag = false;
    private boolean dragLineFlag = false;
    private boolean addingLineFlag = false;
    private float dragX = 0;
    private float dragY = 0;
    private float dragPointInputX = 0;
    private float dragPointInputY = 0;
    private float dragPointOutputX = 0;
    private float dragPointOutputY = 0;

    private float translateX;
    private float translateY;

    private View rootView;

    private DrawView drawView;

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

    private Dialog dialog;

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
        this.drawView.invalidate();
        translateX = this.drawView.getBitmapWidth() / 2;
        translateY = this.drawView.getBitmapHight() / 2;
        this.drawView.setTranslateX(translateX);
        this.drawView.setTranslateY(translateY);
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

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    private MainPresenter(Context context) {
        this.context = context;
    }

    public void showFiguresPanel() {
        if (!showFigurePanelFlag) {
            scrollView.setVisibility(View.VISIBLE);
            showFigurePanelFlag = !showFigurePanelFlag;
            draw();
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
        imageViewCycleStart.setOnClickListener(clickImage);
        imageViewCycleEnd.setOnClickListener(clickImage);
    }

    public void changeText() {
        String text = ((EditText) dialog.findViewById(R.id.editTextInputElementText)).getText().toString();
        ((EditText) dialog.findViewById(R.id.editTextInputElementText)).setText("");
        for (int i = 0; i < model.getFigures().size(); i++) {
            if (model.getFigures().get(i).getId() == selectedFigure.getId()) {
                model.getFigures().get(i).setText(text);
                break;
            }
        }
        dialog.dismiss();
    }

    private View.OnClickListener clickImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int figureId = v.getId();
            float x = (drawView.getWidth() - translateX) / 2;
            float y = (drawView.getHeight() - translateY) / 2;
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
                case R.id.imageViewFigureCycleStart:
                    model.getFigures().add(new Figure(id, FigureType.CYCLE_START, x, y));
                    break;
                case R.id.imageViewFigureCycleEnd:
                    int count = 0;
                    for (Figure figure : model.getFigures()) {
                        if (figure.getType().equals(FigureType.CYCLE_START)) {
                            count++;
                            break;
                        }
                        if (figure.getType().equals(FigureType.CYCLE_END)) {
                            count--;
                            break;
                        }
                    }
                    if (count > 0) {
                        model.getFigures().add(new Figure(id, FigureType.CYCLE_END, x, y));
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

    public void draw() {
        selectedFigure = null;
        addingLineFlag = false;
        drawView.setAddingLine(false);
        drawView.setLines(model.getLines());
        drawView.setFigures(model.getFigures());
        drawView.setSelectedFigure(selectedFigure);
        drawView.invalidate();
    }

    public void cancel() {
        ((TextView) dialog.findViewById(R.id.textViewTextChange)).setText("");
        dialog.dismiss();
    }

    public void showDialog() {
        ((EditText) dialog.findViewById(R.id.editTextInputElementText)).setText(selectedFigure.getText());
        dialog.show();
    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.popupmenu_main_form);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    if (item.getItemId() == R.id.item_export) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.file_creation_dialog);
                        ((EditText) dialog.findViewById(R.id.editTextFileName)).setText("");
                        dialog.findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    fileService.saveImage(context,
                                            ((EditText) dialog.findViewById(R.id.editTextFileName)).getText().toString(),
                                            drawView.getBitmap());
                                    dialog.dismiss();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialog.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((EditText) dialog.findViewById(R.id.editTextFileName)).setText("");
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return true;
                    } else if (item.getItemId() == R.id.item_settings) {
                        return true;
                    } else if (item.getItemId() == R.id.item_generate_code) {
                        saveFile();
                        Intent intent = new Intent(context, CodeActivity.class);
                        intent.putExtra("filename", model.getName());
                        context.startActivity(intent);
                        return true;
                    } else if (item.getItemId() == R.id.item_help) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private View.OnTouchListener touchView = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (showFigurePanelFlag) {
                scrollView.setVisibility(View.INVISIBLE);
                showFigurePanelFlag = !showFigurePanelFlag;
            }
            float evX = event.getX() - translateX;
            float evY = event.getY() - translateY;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX() - translateX;
                    y = event.getY() - translateY;
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
                            case FigureType.CYCLE_START:
                                figureX = selectedFigure.getX() - 250 / 2;
                                figureY = selectedFigure.getY() - 160 / 2;
                                figureWidth = 500;
                                figureHeight = 400;
                                break;
                            case FigureType.CYCLE_END:
                                figureX = selectedFigure.getX() - 250 / 2;
                                figureY = selectedFigure.getY() - 100 / 2;
                                figureWidth = 500;
                                figureHeight = 400;
                                break;
                        }
                        figureX -= translateX / 2;
                        figureY -= translateY / 2;
                        figureWidth /= 2;
                        figureHeight /= 2;
                        float buttonX = figureX - 10;
                        float buttonY = figureY - 10 - 40 * 3;
                        if (x > buttonX && y > buttonY && x < buttonX + 40 * 3 && y < buttonY + 40 * 3) {
                            dragFlag = true;
                            dragX = x - selectedFigure.getX();
                            dragY = y - selectedFigure.getY();
                            for (int i = 0; i < model.getLines().size(); i++) {
                                if (model.getLines().get(i).getFigureEndId() == selectedFigure.getId()) {
                                    float pointX = model.getLines().get(i).getPoints()
                                            .get(model.getLines().get(i).getPoints().size() - 1).getX();
                                    float pointY = model.getLines().get(i).getPoints()
                                            .get(model.getLines().get(i).getPoints().size() - 1).getY();
                                    dragPointInputX = x - pointX;
                                    dragPointInputY = y - pointY;
                                }
                                if (model.getLines().get(i).getFigureStartId() == selectedFigure.getId()) {
                                    float pointX = model.getLines().get(i).getPoints()
                                            .get(0).getX();
                                    float pointY = model.getLines().get(i).getPoints()
                                            .get(0).getY();
                                    dragPointOutputX = x - pointX;
                                    dragPointOutputY = y - pointY;
                                }
                            }
                            return true;
                        }
                        buttonX = figureX + figureWidth - 40 * 3;
                        if (x > buttonX && y > buttonY && x < buttonX + 40 * 3 && y < buttonY + 40 * 3) {
                            model.getFigures().remove(selectedFigure);
                            for (int i = 0; i < model.getLines().size(); i++) {
                                if (model.getLines().get(i).getFigureStartId() == selectedFigure.getId() ||
                                        model.getLines().get(i).getFigureEndId() == selectedFigure.getId()) {
                                    for (int j = 0; j < model.getFigures().size(); j++) {
                                        if (model.getFigures().get(j).getId() ==
                                                model.getLines().get(i).getFigureStartId()) {
                                            if (model.getFigures().get(j).getType()
                                                    .equals(FigureType.CONDITION)) {
                                                if (model.getFigures().get(j).getOutputLeft().getId() == selectedFigure.getId()) {
                                                    model.getFigures().get(j).setOutputLeft(null);
                                                } else {
                                                    model.getFigures().get(j).setOutputRight(null);
                                                }
                                            } else {
                                                model.getFigures().get(j).setOutput(null);
                                            }
                                        }
                                    }
                                    model.getLines().remove(i);
                                    i--;
                                }
                            }
                            selectedFigure = null;
                            drawView.setLines(model.getLines());
                            drawView.setFigures(model.getFigures());
                            drawView.setSelectedFigure(selectedFigure);
                            drawView.invalidate();
                            return true;
                        }
                    }
                    if (selectedLine != null) {
                        for (int j = 0; j < selectedLine.getPoints().size(); j++) {
                            float x1 = selectedLine.getPoints().get(j).getX() - translateX / 2;
                            float y1 = selectedLine.getPoints().get(j).getY() - translateY / 2;
                            if (j > 0 && j < selectedLine.getPoints().size() - 1) {
                                if (Math.sqrt((evX - x1) * (evX - x1) + (evY - y1) * (evY - y1)) <= 30) {
                                    dragLineFlag = true;
                                    dragX = x - selectedLine.getPoints().get(j).getX();
                                    dragY = y - selectedLine.getPoints().get(j).getY();
                                    return true;
                                }
                            }
                            x1 = selectedLine.getPoints().get(0).getX() - translateX / 2;
                            y1 = selectedLine.getPoints().get(0).getY() - translateY / 2;
                            if (x > x1 - 100 && y > y1 + 25 && x < x1 - 100 + 40 * 3 && y < y1 + 25 + 40 * 3) {
                                model.getLines().remove(selectedLine);
                                for (int i = 0; i < model.getFigures().size(); i++) {
                                    if (model.getFigures().get(i).getId() == selectedLine.getFigureStartId()) {
                                        if (model.getFigures().get(i).getType().equals(FigureType.CONDITION)) {
                                            if (model.getFigures().get(i).getOutputLeft().getId() ==
                                                    selectedLine.getFigureStartId()) {
                                                model.getFigures().get(i).setOutputLeft(null);
                                            } else {
                                                model.getFigures().get(i).setOutputRight(null);
                                            }
                                        } else {
                                            model.getFigures().get(i).setOutput(null);
                                        }
                                    }
                                }
                                selectedLine = null;
                                drawView.setLines(model.getLines());
                                drawView.setFigures(model.getFigures());
                                drawView.setSelectedLine(selectedLine);
                                drawView.invalidate();
                                return true;
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (dragFlag) {
                        selectedFigure.setX(evX - dragX);
                        selectedFigure.setY(evY - dragY);
                        for (int i = 0; i < model.getLines().size(); i++) {
                            if (model.getLines().get(i).getFigureEndId() == selectedFigure.getId()) {
                                model.getLines().get(i).getPoints()
                                        .get(model.getLines().get(i).getPoints().size() - 1).setX(evX - dragPointInputX);
                                model.getLines().get(i).getPoints()
                                        .get(model.getLines().get(i).getPoints().size() - 1).setY(evY - dragPointInputY);
                            }
                            if (model.getLines().get(i).getFigureStartId() == selectedFigure.getId()) {
                                model.getLines().get(i).getPoints()
                                        .get(0).setX(evX - dragPointOutputX);
                                model.getLines().get(i).getPoints()
                                        .get(0).setY(evY - dragPointOutputY);
                            }
                        }
                        drawView.setLines(model.getLines());
                        drawView.setSelectedFigure(selectedFigure);
                        drawView.invalidate();
                        return true;
                    } else if (dragLineFlag) {
                        for (int i = 0; i < model.getLines().size(); i++) {
                            if (model.getLines().get(i).equals(selectedLine)) {
                                for (int j = 1; j < selectedLine.getPoints().size() - 1; j++) {
                                    float x1 = selectedLine.getPoints().get(j).getX() - translateX / 2;
                                    float y1 = selectedLine.getPoints().get(j).getY() - translateY / 2;
                                    if (Math.sqrt((evX - x1) * (evX - x1) + (evY - y1) * (evY - y1)) <= 30) {
                                        model.getLines().get(i).getPoints().get(j).setX(evX - dragX);
                                        model.getLines().get(i).getPoints().get(j).setY(evY - dragY);
                                        drawView.setLines(model.getLines());
                                        drawView.invalidate();
                                        return true;
                                    }
                                }
                            }
                        }
                        return true;
                    } else {
                        drawView.setTranslateX(translateX + evX - x);
                        drawView.setTranslateY(translateY + evY - y);
                        drawView.invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (dragFlag) {
                        dragFlag = false;
                        return true;
                    } else if (dragLineFlag) {
                        dragLineFlag = false;
                        return true;
                    } else {
                        translateX += evX - x;
                        translateY += evY - y;
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
            Figure newFigure = null;
            Line newLine = null;
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
                    case FigureType.CYCLE_START:
                        figureX = figure.getX() - 250 / 2;
                        figureY = figure.getY() - 160 / 2;
                        figureWidth = 500;
                        figureHeight = 270;
                        break;
                    case FigureType.CYCLE_END:
                        figureX = figure.getX() - 250 / 2;
                        figureY = figure.getY() - 100 / 2;
                        figureWidth = 500;
                        figureHeight = 270;
                        break;
                }
                figureX -= translateX / 2;
                figureY -= translateY / 2;
                figureWidth /= 2;
                figureHeight /= 2;
                if (x > figureX && y > figureY && x < figureX + figureWidth && y < figureY + figureHeight) {
                    if (selectedFigure != null && figure.getId() != selectedFigure.getId()) {
                        if (addingLineFlag) {
                            if (selectedFigure.getType().equals(FigureType.CONDITION)) {
                                if (selectedFigure.getOutputLeft() == null) {
                                    for (int i = 0; i < model.getFigures().size(); i++) {
                                        if (model.getFigures().get(i).getId() == selectedFigure.getId()) {
                                            model.getFigures().get(i).setOutputLeft(figure);
                                        }
                                    }
                                    model.getLines().add(new Line(selectedFigure, figure));
                                } else {
                                    for (int i = 0; i < model.getFigures().size(); i++) {
                                        if (model.getFigures().get(i).getId() == selectedFigure.getId()) {
                                            model.getFigures().get(i).setOutputRight(figure);
                                        }
                                    }
                                    model.getLines().add(new Line(selectedFigure, figure));
                                }
                            } else {
                                for (int i = 0; i < model.getFigures().size(); i++) {
                                    if (model.getFigures().get(i).getId() == selectedFigure.getId()) {
                                        model.getFigures().get(i).setOutput(figure);
                                    }
                                }
                                model.getLines().add(new Line(selectedFigure, figure));
                            }

                            addingLineFlag = false;
                        }
                    } else if (selectedFigure != null && figure.getId() == selectedFigure.getId()) {
                        if (!selectedFigure.getType().equals(FigureType.START) &&
                                !selectedFigure.getType().equals(FigureType.END) &&
                                !selectedFigure.getType().equals(FigureType.CYCLE_END)) {
                            showDialog();
                        }
                        newFigure = figure;
                    } else {
                        newFigure = figure;
                    }
                    break;
                }
            }
            selectedFigure = newFigure;
            if (selectedFigure == null) {
                for (int i = 0; i < model.getLines().size(); i++) {
                    for (int j = 0; j < model.getLines().get(i).getPoints().size() - 1; j++) {
                        float x1 = model.getLines().get(i).getPoints().get(j).getX() - translateX / 2;
                        float y1 = model.getLines().get(i).getPoints().get(j).getY() - translateY / 2;
                        float x2 = model.getLines().get(i).getPoints().get(j + 1).getX() - translateX / 2;
                        float y2 = model.getLines().get(i).getPoints().get(j + 1).getY() - translateY / 2;
                        float k = (y1 - y2) / (x1 - x2);
                        float b = y2 - k * x2;

                        if ((k * x - y + b) / Math.sqrt(k * k + 1) <= 30 &&
                                (k * x - y + b) / Math.sqrt(k * k + 1) >= 0) {
                            if (x >= x1 && x <= x2 || x <= x1 && x >= x2) {
                                if (y >= y1 && y <= y2 || y <= y1 && y >= y2) {
                                    newLine = model.getLines().get(i);
                                    break;
                                }
                            }
                        }
                    }
                    if (newLine != null) {
                        break;
                    }
                }
            }
            selectedLine = newLine;
            addingLineFlag = false;
            drawView.setAddingLine(false);
            drawView.setSelectedFigure(selectedFigure);
            drawView.setSelectedLine(selectedLine);
            drawView.setLines(model.getLines());
            drawView.invalidate();
        }
    };

    private View.OnLongClickListener longClickView = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            addingLineFlag = false;
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
                    case FigureType.CYCLE_START:
                        figureX = selectedFigure.getX() - 250 / 2;
                        figureY = selectedFigure.getY() - 160 / 2;
                        figureWidth = 500;
                        figureHeight = 270;
                        break;
                    case FigureType.CYCLE_END:
                        figureX = selectedFigure.getX() - 250 / 2;
                        figureY = selectedFigure.getY() - 100 / 2;
                        figureWidth = 500;
                        figureHeight = 270;
                        break;
                }
                figureX -= translateX / 2;
                figureY -= translateY / 2;
                figureWidth /= 2;
                figureHeight /= 2;
                if (x > figureX && y > figureY && x < figureX + figureWidth && y < figureY + figureHeight) {
                    if (!selectedFigure.getType().equals(FigureType.END)) {
                        if (selectedFigure.getType().equals(FigureType.CONDITION)) {
                            if (selectedFigure.getOutputLeft() == null ||
                                    selectedFigure.getOutputRight() == null) {
                                addingLineFlag = true;
                                drawView.setAddingLine(true);
                                drawView.invalidate();
                            }
                        } else {
                            if (selectedFigure.getOutput() == null) {
                                addingLineFlag = true;
                                drawView.setAddingLine(true);
                                drawView.invalidate();
                            }
                        }
                    }
                    return true;
                }
            } else if (selectedLine != null) {
                int i;
                for (i = 0; i < model.getLines().size(); i++) {
                    if (model.getLines().get(i).equals(selectedLine)) {
                        break;
                    }
                }
                float lineX = 0;
                float lineY = 0;
                int p = 0;
                /*for (int j = 1; j < selectedLine.getPoints().size() - 1; j++) {
                    float x1 = selectedLine.getPoints().get(j).getX() - translateX / 2;
                    float y1 = selectedLine.getPoints().get(j).getY() - translateY / 2;

                    if (Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)) <= 30) {
                        model.getLines().get(i).getPoints().remove(selectedLine.getPoints().get(j));
                        selectedLine = null;
                        drawView.setSelectedLine(selectedLine);
                        drawView.setLines(model.getLines());
                        drawView.invalidate();
                        return true;
                    }
                }*/
                for (int j = 0; j < selectedLine.getPoints().size() - 1; j++) {
                    float x1 = selectedLine.getPoints().get(j).getX() - translateX / 2;
                    float y1 = selectedLine.getPoints().get(j).getY() - translateY / 2;
                    float x2 = selectedLine.getPoints().get(j + 1).getX() - translateX / 2;
                    float y2 = selectedLine.getPoints().get(j + 1).getY() - translateY / 2;
                    float k = (y1 - y2) / (x1 - x2);
                    float b = y2 - k * x2;

                    if ((k * x - y + b) / Math.sqrt(k * k + 1) <= 30 &&
                            (k * x - y + b) / Math.sqrt(k * k + 1) >= 0) {
                        p = j;
                        float r1 = Math.abs(x - (y - b) / k);
                        float r2 = Math.abs(y - (k * x + b));
                        if (r1 < r2) {
                            lineX = (y - b) / k;
                            lineY = y;
                        } else {
                            lineX = x;
                            lineY = k * x + b;
                        }
                        model.getLines().get(i).getPoints().add(p + 1, new Point(lineX, lineY));
                        drawView.setLines(model.getLines());
                        drawView.invalidate();
                        return true;
                    }
                }
            }
            return false;
        }
    };

}
