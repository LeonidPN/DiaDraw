package com.example.diadraw.Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.example.diadraw.R;
import com.example.diadraw.Views.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingsPresenter {

    private static SettingsPresenter presenter;

    public static SettingsPresenter getPresenter(Context context) {
        if (presenter == null) {
            presenter = new SettingsPresenter(context);
        }
        if (context != null) {
            return presenter = new SettingsPresenter(context);
        } else {
            return presenter;
        }
    }

    private SettingsPresenter(Context context) {
        this.context = context;
        settings = context.getSharedPreferences("Settings", MODE_PRIVATE);
        dialog = ColorPickerDialog.createColorPickerDialog(context, ColorPickerDialog.LIGHT_THEME);
    }

    private Context context;

    private EditText editTextAutoSaveTime;

    private ImageView imageViewWorkAreaColor;
    private ImageView imageViewFiguresColor;
    private ImageView imageViewFontColor;
    private ImageView imageViewBorderColor;

    private Button buttonSave;
    private Button buttonCancel;

    private SharedPreferences settings;

    private ColorPickerDialog dialog;

    private SettingsActivity view;

    public void setView(SettingsActivity view) {
        this.view = view;
    }

    public void setEditTextAutoSaveTime(EditText editTextAutoSaveTime) {
        this.editTextAutoSaveTime = editTextAutoSaveTime;
    }

    public void setImageViewWorkAreaColor(ImageView imageViewWorkAreaColor) {
        this.imageViewWorkAreaColor = imageViewWorkAreaColor;
    }

    public void setImageViewFiguresColor(ImageView imageViewFiguresColor) {
        this.imageViewFiguresColor = imageViewFiguresColor;
    }

    public void setImageViewFontColor(ImageView imageViewFontColor) {
        this.imageViewFontColor = imageViewFontColor;
    }

    public void setImageViewBorderColor(ImageView imageViewBorderColor) {
        this.imageViewBorderColor = imageViewBorderColor;
    }

    public Button getButtonSave() {
        return buttonSave;
    }

    public void setButtonSave(Button buttonSave) {
        this.buttonSave = buttonSave;
    }

    public Button getButtonCancel() {
        return buttonCancel;
    }

    public void setButtonCancel(Button buttonCancel) {
        this.buttonCancel = buttonCancel;
    }

    public void loadData() {
        if (settings.contains("autoSaveTime")) {
            editTextAutoSaveTime.setText(settings.getInt("autoSaveTime", 0) + "");
        } else {
            editTextAutoSaveTime.setText("0");
        }
        if (settings.contains("workAreaColor")) {
            imageViewWorkAreaColor.setBackgroundColor(settings.getInt("workAreaColor", Color.WHITE));
        } else {
            imageViewWorkAreaColor.setBackgroundColor(Color.WHITE);
        }
        if (settings.contains("figuresColor")) {
            imageViewFiguresColor.setBackgroundColor(settings.getInt("figuresColor", Color.GRAY));
        } else {
            imageViewFiguresColor.setBackgroundColor(Color.GRAY);
        }
        if (settings.contains("fontColor")) {
            imageViewFontColor.setBackgroundColor(settings.getInt("fontColor", Color.BLACK));
        } else {
            imageViewFontColor.setBackgroundColor(Color.BLACK);
        }
        if (settings.contains("borderColor")) {
            imageViewBorderColor.setBackgroundColor(settings.getInt("borderColor", Color.BLACK));
        } else {
            imageViewBorderColor.setBackgroundColor(Color.BLACK);
        }
    }

    public void setImageClick() {
        imageViewBorderColor.setOnClickListener(clickImage);
        imageViewFontColor.setOnClickListener(clickImage);
        imageViewFiguresColor.setOnClickListener(clickImage);
        imageViewWorkAreaColor.setOnClickListener(clickImage);
    }

    private View.OnClickListener clickImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int imageId = v.getId();
            int color = 0;
            ColorDrawable drawable;
            switch (imageId) {
                case R.id.imageViewBorderColor:
                    drawable = (ColorDrawable) imageViewBorderColor.getBackground();
                    color = drawable.getColor();
                    break;
                case R.id.imageViewFontColor:
                    drawable = (ColorDrawable) imageViewFontColor.getBackground();
                    color = drawable.getColor();
                    break;
                case R.id.imageViewFiguresColor:
                    drawable = (ColorDrawable) imageViewFiguresColor.getBackground();
                    color = drawable.getColor();
                    break;
                case R.id.imageViewWorkAreaColor:
                    drawable = (ColorDrawable) imageViewWorkAreaColor.getBackground();
                    color = drawable.getColor();
                    break;
            }
            dialog.setLastColor(color);
            dialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                @Override
                public void onColorPicked(int color, String hexVal) {
                    switch (imageId) {
                        case R.id.imageViewBorderColor:
                            imageViewBorderColor.setBackgroundColor(color);
                            break;
                        case R.id.imageViewFontColor:
                            imageViewFontColor.setBackgroundColor(color);
                            break;
                        case R.id.imageViewFiguresColor:
                            imageViewFiguresColor.setBackgroundColor(color);
                            break;
                        case R.id.imageViewWorkAreaColor:
                            imageViewWorkAreaColor.setBackgroundColor(color);
                            break;
                    }
                }
            });
            dialog.show();
        }
    };

    public void save() {
        try {
            SharedPreferences.Editor editor = settings.edit();
            ColorDrawable drawable;
            int color;
            int time;
            String str = editTextAutoSaveTime.getText().toString();
            if (str == null || str == "") {
                time = 0;
            } else {
                time = Integer.parseInt(str);
                if (time < 0) {
                    time = 0;
                }
            }
            editor.putInt("autoSaveTime", time);
            drawable = (ColorDrawable) imageViewBorderColor.getBackground();
            color = drawable.getColor();
            editor.putInt("borderColor", color);
            drawable = (ColorDrawable) imageViewFontColor.getBackground();
            color = drawable.getColor();
            editor.putInt("fontColor", color);
            drawable = (ColorDrawable) imageViewFiguresColor.getBackground();
            color = drawable.getColor();
            editor.putInt("figuresColor", color);
            drawable = (ColorDrawable) imageViewWorkAreaColor.getBackground();
            color = drawable.getColor();
            editor.putInt("workAreaColor", color);
            editor.commit();
        } catch (Exception ex) {
            Toast(ex.getMessage(), true);
        }
    }

    public void Toast(String message, boolean isLong) {
        try {
            Toast.makeText(view, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
        } catch (Resources.NotFoundException e) {
            String report = e.getMessage() + "\n";
            report += e.getStackTrace();
            Log.e(TAG, report);
        }
    }
}
