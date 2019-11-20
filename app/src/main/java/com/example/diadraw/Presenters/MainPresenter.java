package com.example.diadraw.Presenters;

import android.content.Context;
import android.view.View;

import com.example.diadraw.Models.FileService;

import java.util.ArrayList;

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

    private ArrayList<View> figurePanel;

    private MainPresenter(Context context) {
        this.context = context;
    }
}
