package com.example.diadraw.Presenters;

import android.content.Context;
import android.widget.TextView;

import com.example.diadraw.Models.CodeGeneratorService;
import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class CodePresenter {

    private static CodePresenter presenter;

    public static CodePresenter getPresenter(Context context) {
        if (presenter == null) {
            presenter = new CodePresenter(context);
        }
        if (context != null) {
            return presenter = new CodePresenter(context);
        } else {
            return presenter;
        }
    }

    private CodePresenter(Context context) {
        this.context = context;
    }

    private FileService fileService = new FileService();
    private Context context;

    private FloatingActionButton buttonHome;

    private TextView codeTextView;

    public FloatingActionButton getButtonHome() {
        return buttonHome;
    }

    public void setButtonHome(FloatingActionButton buttonHome) {
        this.buttonHome = buttonHome;
    }

    public void setCodeTextView(TextView codeTextView) {
        this.codeTextView = codeTextView;
    }

    public void loadData(String fileName) {
        try {
            FileModel fileModel = fileService.getFile(context, fileName);
            CodeGeneratorService service = new CodeGeneratorService();
            ArrayList<String> lines = service.getCode(fileModel.getFigures());
            String res = "";
            for (String line : lines) {
                res += line + '\n';
            }
            codeTextView.setText(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
