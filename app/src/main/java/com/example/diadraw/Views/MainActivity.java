package com.example.diadraw.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.diadraw.Presenters.MainPresenter;
import com.example.diadraw.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

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
        presenter.getDialog().findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
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

        AlertDialog.Builder helpDialogBuilder;
        helpDialogBuilder = new AlertDialog.Builder(this);
        helpDialogBuilder.setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);
        helpDialogBuilder.setView(dialogView);
        //helpDialog.setView(linearlayout);
        //View linearlayout = getLayoutInflater().inflate(R.layout.help_dialog, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Map<Integer, String> map = new HashMap<>();
        map.put(R.drawable.image_1, "На экране представлено 4 кнопки: возврат на страницу выбора файла (без сохранения схемы), " +
                "быстрое сохранение, кнопка открытия панели блоков  и кнопка открытия меню");
        //map.put(R.drawable.image_2, "В меню");
        map.put(R.drawable.image_3, "При нажатии на кнопку открытия панели блоков открывается панель со всеми доступными для" +
                " использования блоки");
        map.put(R.drawable.image_4, "При нажатии на любой элемент из панели блоков соответствующий блок появляется по центру экрана");
        map.put(R.drawable.image_5, "При нажатии на блок в рабочей области он выделяется. Рядом с ним появляются кнопки перетаскивания " +
                "и удаления блока");
        map.put(R.drawable.image_6, "При нажатии на кнопку перетаскивания блока он будет двигаться за пальцем");
        map.put(R.drawable.image_7, "При нажатии на кнопку удаления блока он удаляется");
        map.put(R.drawable.image_8, "Если на выделенную фигуру долго нажимать она перейдет в состояние соединения блоков");
        map.put(R.drawable.image_9, "При нажатии на другую фигуру автоматически появляется соединительная линия между блоками");
        map.put(R.drawable.image_10, "Если на выделенныю фигуру нажать второй раз, откроется окно изменения текста блока");
        map.put(R.drawable.image_11, "Введенный текст быдет отображаться на соответствующем блоке");
        map.put(R.drawable.image_12, "При нажатии на соеденительную линию она выделяется");
        map.put(R.drawable.image_13, "Если на выделенную линию долго нажимать создасться дополнительная точка на линии");
        map.put(R.drawable.image_14, "Эту точку можно перемещать по рабочей области");
        map.put(R.drawable.image_15, "При нажатии на кнопку удаления линии она удаляется");
        map.put(R.drawable.image_16, "При движении пальцем по свободной части рабочей области ее можно сдвинуть");
        map.put(R.drawable.image_17, "Это позволяетсоздать дополнительное место на рабочей области");
        map.put(R.drawable.image_18, "При этом новые созданные блоки появляются по прежнему в центре экрана");
        //map.put(R.drawable.image_19, "");
        //map.put(R.drawable.image_20, "");
        recyclerView.setAdapter(new HelpListAdapter(map));
        AlertDialog helpDialog = helpDialogBuilder.create();
        presenter.setHelpDialog(helpDialog);
    }

    @Override
    protected void onResume() {
        String fileName = getIntent().getStringExtra("filename");
        presenter.loadData(fileName);
        presenter.resume();
        super.onResume();
    }
}
