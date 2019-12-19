package com.example.diadraw.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.View;

import com.example.diadraw.Models.WorkModels.Figure;
import com.example.diadraw.Models.WorkModels.FigureType;
import com.example.diadraw.Models.WorkModels.Line;
import com.example.diadraw.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DrawView extends View {

    private Paint fontPaint;

    private int fontColor;

    private Paint figurePaint;

    private int figureColor;

    private Paint borderPaint;

    private int borderColor;

    private int workAreaColor;

    private ArrayList<Figure> figures;

    private Figure selectedFigure;

    private boolean addingLine;

    private ArrayList<Line> lines;

    private Line selectedLine;

    private float translateX;

    private float translateY;

    private Bitmap bitmap;

    private int bitmapWidth;

    private int bitmapHight;

    private SharedPreferences settings;

    public DrawView(Context context) {
        super(context);

        settings = context.getSharedPreferences("Settings", MODE_PRIVATE);

        figures = new ArrayList<>();

        lines = new ArrayList<>();

        addingLine = false;

        translateX = 0;
        translateY = 0;

        fontPaint = new Paint();
        figurePaint = new Paint();
        borderPaint = new Paint();

        updateColors();

        fontPaint.setTextSize(40);
        fontPaint.setStyle(Paint.Style.FILL);
        fontPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        fontPaint.setColor(fontColor);
        fontPaint.setTextAlign(Paint.Align.CENTER);

        figurePaint.setStyle(Paint.Style.FILL);
        figurePaint.setColor(figureColor);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(5);
    }

    private void updateColors() {
        if (settings.contains("workAreaColor")) {
            workAreaColor = settings.getInt("workAreaColor", Color.WHITE);
        } else {
            workAreaColor = Color.WHITE;
        }
        if (settings.contains("figuresColor")) {
            figureColor = settings.getInt("figuresColor", Color.GRAY);
        } else {
            figureColor = Color.GRAY;
        }
        figurePaint.setColor(figureColor);
        if (settings.contains("fontColor")) {
            fontColor = settings.getInt("fontColor", Color.BLACK);
        } else {
            fontColor = Color.BLACK;
        }
        fontPaint.setColor(fontColor);
        if (settings.contains("borderColor")) {
            borderColor = settings.getInt("borderColor", Color.BLACK);
        } else {
            borderColor = Color.BLACK;
        }
        borderPaint.setColor(borderColor);
    }

    public void setFigures(ArrayList<Figure> figures) {
        this.figures = figures;
    }

    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }

    public void setAddingLine(boolean addingLine) {
        this.addingLine = addingLine;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public void setSelectedLine(Line selectedLine) {
        this.selectedLine = selectedLine;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public int getBitmapHight() {
        return bitmapHight;
    }

    public Bitmap getBitmap() {
        updateColors();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHight,
                Bitmap.Config.ARGB_8888);
        Canvas canvasSaving = new Canvas(bitmap);
        canvasSaving.drawColor(workAreaColor);
        canvasSaving.scale(0.5f, 0.5f);
        canvasSaving.translate(translateX, translateY);
        for (Line line : lines) {
            drawLine(canvasSaving, line);
        }
        for (Figure figure : figures) {
            switch (figure.getType()) {
                case FigureType.ACTIVITY:
                    drawActivityFigure(canvasSaving, figure);
                    break;
                case FigureType.START:
                    drawStartFigure(canvasSaving, figure);
                    break;
                case FigureType.END:
                    drawEndFigure(canvasSaving, figure);
                    break;
                case FigureType.INPUT:
                    drawInputFigure(canvasSaving, figure);
                    break;
                case FigureType.OUTPUT:
                    drawOutputFigure(canvasSaving, figure);
                    break;
                case FigureType.CONDITION:
                    drawConditionFigure(canvasSaving, figure);
                    break;
                case FigureType.CYCLE_START:
                    drawCycleStartFigure(canvasSaving, figure);
                    break;
                case FigureType.CYCLE_END:
                    drawCycleEndFigure(canvasSaving, figure);
                    break;
            }
        }
        float x1 = 0;
        float y1 = 0;
        float x2 = 0;
        float y2 = 0;
        if (figures.size() > 0) {
            x1 = figures.get(0).getX();
            y1 = figures.get(0).getY();
            x2 = figures.get(0).getX();
            y2 = figures.get(0).getY();
        }
        for (int i = 1; i < figures.size(); i++) {
            float x = figures.get(i).getX();
            float y = figures.get(i).getY();
            if (x < x1) {
                x1 = x;
            }
            if (y < y1) {
                y1 = y;
            }
            if (x > x2) {
                x2 = x;
            }
            if (y > y2) {
                y2 = y;
            }
        }
        x1 -= 600;
        y1 -= 600;
        x2 += 600;
        y2 += 600;
        return Bitmap.createBitmap(bitmap, Math.max((int) x1, 0), Math.max((int) y1, 0),
                Math.min((int) (x2 - x1), bitmapWidth), Math.min((int) (y2 - y1), bitmapHight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateColors();
        bitmapWidth = canvas.getMaximumBitmapWidth();
        bitmapHight = canvas.getMaximumBitmapHeight();
        canvas.drawColor(workAreaColor);
        canvas.scale(0.5f, 0.5f);
        canvas.translate(translateX, translateY);
        for (Line line : lines) {
            drawLine(canvas, line);
        }
        for (Figure figure : figures) {
            switch (figure.getType()) {
                case FigureType.ACTIVITY:
                    drawActivityFigure(canvas, figure);
                    break;
                case FigureType.START:
                    drawStartFigure(canvas, figure);
                    break;
                case FigureType.END:
                    drawEndFigure(canvas, figure);
                    break;
                case FigureType.INPUT:
                    drawInputFigure(canvas, figure);
                    break;
                case FigureType.OUTPUT:
                    drawOutputFigure(canvas, figure);
                    break;
                case FigureType.CONDITION:
                    drawConditionFigure(canvas, figure);
                    break;
                case FigureType.CYCLE_START:
                    drawCycleStartFigure(canvas, figure);
                    break;
                case FigureType.CYCLE_END:
                    drawCycleEndFigure(canvas, figure);
                    break;
            }
        }
        drawSelectedFigure(canvas);
        drawSelectedLine(canvas);
    }

    private void drawActivityFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;
        canvas.drawRect(x - 250, y - 110, x + 250, y + 110, figurePaint);
        canvas.drawRect(x - 250, y - 110, x + 250, y + 110, borderPaint);

        ArrayList<String> lines = getLines(figure.getText());
        for (int i = 0; i < lines.size(); i++) {
            canvas.drawText(lines.get(lines.size() - 1 - i), x
                    , y + 90 - 40 * i, fontPaint);
        }
    }

    private void drawConditionFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;

        float[] points = new float[10];
        points[0] = x - 480;
        points[1] = y;
        points[2] = x;
        points[3] = y - 200;
        points[4] = x + 480;
        points[5] = y;
        points[6] = x;
        points[7] = y + 200;
        points[8] = x - 480;
        points[9] = y;

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, points.length, points, 0,
                null, 0, null, 0, null, 0, 0, figurePaint);
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[8], points[9]);
        canvas.drawPath(path, figurePaint);
        canvas.drawPath(path, borderPaint);

        fontPaint.setColor(borderColor);
        fontPaint.setTextSize(80);
        canvas.drawText("+", points[0] + 40, points[1] - 80, fontPaint);
        canvas.drawText("-", points[4] - 40, points[5] - 80, fontPaint);
        fontPaint.setColor(fontColor);
        fontPaint.setTextSize(40);

        ArrayList<String> lines = getLines(figure.getText());
        for (int i = 0; i < lines.size(); i++) {
            canvas.drawText(lines.get(lines.size() - 1 - i), x
                    , y + 90 - 40 * i, fontPaint);
        }
    }

    private void drawCycleStartFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;

        float[] points = new float[12];
        points[0] = x - 250;
        points[1] = y + 110;
        points[2] = x - 250;
        points[3] = y - 110;
        points[4] = x - 200;
        points[5] = y - 160;
        points[6] = x + 200;
        points[7] = y - 160;
        points[8] = x + 250;
        points[9] = y - 110;
        points[10] = x + 250;
        points[11] = y + 110;

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, points.length, points, 0,
                null, 0, null, 0, null, 0, 0, figurePaint);
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[8], points[9]);
        path.lineTo(points[10], points[11]);
        path.lineTo(points[0], points[1]);
        canvas.drawPath(path, figurePaint);
        canvas.drawPath(path, borderPaint);

        ArrayList<String> lines = getLines(figure.getText());
        for (int i = 0; i < lines.size(); i++) {
            canvas.drawText(lines.get(lines.size() - 1 - i), x
                    , y + 90 - 40 * i, fontPaint);
        }
    }

    private void drawCycleEndFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;

        float[] points = new float[12];
        points[0] = x - 250;
        points[1] = y + 110;
        points[2] = x - 250;
        points[3] = y - 110;
        points[4] = x + 250;
        points[5] = y - 110;
        points[6] = x + 250;
        points[7] = y + 110;
        points[8] = x + 200;
        points[9] = y + 160;
        points[10] = x - 200;
        points[11] = y + 160;

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, points.length, points, 0,
                null, 0, null, 0, null, 0, 0, figurePaint);
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[8], points[9]);
        path.lineTo(points[10], points[11]);
        path.lineTo(points[0], points[1]);
        canvas.drawPath(path, figurePaint);
        canvas.drawPath(path, borderPaint);
    }

    private void drawInputFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;

        float[] points = new float[10];
        points[0] = x - 340;
        points[1] = y + 100;
        points[2] = x - 190;
        points[3] = y - 200;
        points[4] = x + 340;
        points[5] = y - 200;
        points[6] = x + 240;
        points[7] = y + 100;
        points[8] = x - 340;
        points[9] = y + 100;

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, points.length, points, 0,
                null, 0, null, 0, null, 0, 0, figurePaint);
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[8], points[9]);
        canvas.drawPath(path, figurePaint);
        canvas.drawPath(path, borderPaint);

        fontPaint.setTextSize(80);
        canvas.drawText("Ввод", x + 25, y - 120, fontPaint);
        fontPaint.setTextSize(40);

        ArrayList<String> lines = getLines(figure.getText());
        for (int i = 0; i < lines.size(); i++) {
            canvas.drawText(lines.get(lines.size() - 1 - i), x
                    , y + 90 - 40 * i, fontPaint);
        }
    }

    private void drawOutputFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;

        float[] points = new float[10];
        points[0] = x - 340;
        points[1] = y + 100;
        points[2] = x - 190;
        points[3] = y - 200;
        points[4] = x + 340;
        points[5] = y - 200;
        points[6] = x + 240;
        points[7] = y + 100;
        points[8] = x - 340;
        points[9] = y + 100;

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, points.length, points, 0,
                null, 0, null, 0, null, 0, 0, figurePaint);
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[8], points[9]);
        canvas.drawPath(path, figurePaint);
        canvas.drawPath(path, borderPaint);

        fontPaint.setTextSize(80);
        canvas.drawText("Вывод", x + 25, y - 120, fontPaint);
        fontPaint.setTextSize(40);

        ArrayList<String> lines = getLines(figure.getText());
        for (int i = 0; i < lines.size(); i++) {
            canvas.drawText(lines.get(lines.size() - 1 - i), x
                    , y + 90 - 40 * i, fontPaint);
        }
    }

    private void drawStartFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;
        canvas.drawCircle(x - 200, y, 50, figurePaint);
        canvas.drawCircle(x - 200, y, 50, borderPaint);
        canvas.drawCircle(x + 200, y, 50, figurePaint);
        canvas.drawCircle(x + 200, y, 50, borderPaint);
        canvas.drawRect(x - 200, y - 50, x + 200, y + 50, figurePaint);
        canvas.drawLine(x - 200, y - 50, x + 200, y - 50, borderPaint);
        canvas.drawLine(x - 200, y + 50, x + 200, y + 50, borderPaint);

        fontPaint.setTextSize(80);
        canvas.drawText("Начало", x, y + 25, fontPaint);
        fontPaint.setTextSize(40);
    }

    private void drawEndFigure(Canvas canvas, Figure figure) {
        float x = figure.getX() * 2;
        float y = figure.getY() * 2;
        canvas.drawCircle(x - 200, y, 50, figurePaint);
        canvas.drawCircle(x - 200, y, 50, borderPaint);
        canvas.drawCircle(x + 200, y, 50, figurePaint);
        canvas.drawCircle(x + 200, y, 50, borderPaint);
        canvas.drawRect(x - 200, y - 50, x + 200, y + 50, figurePaint);
        canvas.drawLine(x - 200, y - 50, x + 200, y - 50, borderPaint);
        canvas.drawLine(x - 200, y + 50, x + 200, y + 50, borderPaint);

        fontPaint.setTextSize(80);
        canvas.drawText("Конец", x, y + 25, fontPaint);
        fontPaint.setTextSize(40);
    }

    private void drawSelectedFigure(Canvas canvas) {
        if (selectedFigure != null) {
            Bitmap bitmapSource;

            bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.button_move);
            Bitmap moveImage = Bitmap.createBitmap(bitmapSource);

            bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.button_delete);
            Bitmap deleteImage = Bitmap.createBitmap(bitmapSource);

            float x = selectedFigure.getX() * 2;
            float y = selectedFigure.getY() * 2;

            float x1 = 0;
            float y1 = 0;
            float x2 = 0;
            float y2 = 0;
            if (!addingLine) {
                borderPaint.setColor(Color.GREEN);
            } else {
                borderPaint.setColor(Color.RED);
            }
            switch (selectedFigure.getType()) {
                case FigureType.ACTIVITY:
                    canvas.drawRect(x - 260, y - 120, x + 260, y + 120, borderPaint);
                    x1 = x - 260;
                    y1 = y - 120 - 45 * 3;
                    x2 = x + 260 - 40 * 3;
                    y2 = y1;
                    break;
                case FigureType.START:
                case FigureType.END:
                    canvas.drawRect(x - 260, y - 60, x + 260, y + 60, borderPaint);
                    x1 = x - 260;
                    y1 = y - 60 - 45 * 3;
                    x2 = x + 260 - 40 * 3;
                    y2 = y1;
                    break;
                case FigureType.INPUT:
                case FigureType.OUTPUT:
                    canvas.drawRect(x - 350, y - 210, x + 350, y + 110, borderPaint);
                    x1 = x - 350;
                    y1 = y - 210 - 45 * 3;
                    x2 = x + 350 - 40 * 3;
                    y2 = y1;
                    break;
                case FigureType.CONDITION:
                    canvas.drawRect(x - 490, y - 210, x + 490, y + 210, borderPaint);
                    x1 = x - 490;
                    y1 = y - 210 - 45 * 3;
                    x2 = x + 490 - 40 * 3;
                    y2 = y1;
                    break;
                case FigureType.CYCLE_START:
                    canvas.drawRect(x - 260, y - 170, x + 260, y + 120, borderPaint);
                    x1 = x - 260;
                    y1 = y - 170 - 45 * 3;
                    x2 = x + 260 - 40 * 3;
                    y2 = y1;
                    break;
                case FigureType.CYCLE_END:
                    canvas.drawRect(x - 260, y - 120, x + 260, y + 170, borderPaint);
                    x1 = x - 260;
                    y1 = y - 120 - 45 * 3;
                    x2 = x + 260 - 40 * 3;
                    y2 = y1;
                    break;
            }
            if (!addingLine) {
                canvas.drawBitmap(moveImage, x1, y1, new Paint(Paint.ANTI_ALIAS_FLAG));
                canvas.drawBitmap(deleteImage, x2, y2, new Paint(Paint.ANTI_ALIAS_FLAG));
            }
            borderPaint.setColor(borderColor);
        }
    }

    private void drawLine(Canvas canvas, Line line) {
        for (int i = 0; i < line.getPoints().size() - 1; i++) {
            canvas.drawLine(line.getPoints().get(i).getX() * 2, line.getPoints().get(i).getY() * 2,
                    line.getPoints().get(i + 1).getX() * 2, line.getPoints().get(i + 1).getY() * 2, borderPaint);
        }
    }

    private void drawSelectedLine(Canvas canvas) {
        if (selectedLine != null) {
            Bitmap bitmapSource;

            bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.button_delete);
            Bitmap deleteImage = Bitmap.createBitmap(bitmapSource);
            borderPaint.setColor(Color.GREEN);
            figurePaint.setColor(Color.GREEN);
            for (int i = 0; i < selectedLine.getPoints().size() - 1; i++) {
                if (i == 0) {
                    float x1 = selectedLine.getPoints().get(i).getX() * 2 - 200;
                    float y1 = selectedLine.getPoints().get(i).getY() * 2 + 50;
                    canvas.drawBitmap(deleteImage, x1, y1, new Paint(Paint.ANTI_ALIAS_FLAG));
                }
                canvas.drawLine(selectedLine.getPoints().get(i).getX() * 2, selectedLine.getPoints().get(i).getY() * 2,
                        selectedLine.getPoints().get(i + 1).getX() * 2, selectedLine.getPoints().get(i + 1).getY() * 2, borderPaint);
                canvas.drawCircle(selectedLine.getPoints().get(i).getX() * 2,
                        selectedLine.getPoints().get(i).getY() * 2, 20, figurePaint);
                canvas.drawCircle(selectedLine.getPoints().get(i + 1).getX() * 2,
                        selectedLine.getPoints().get(i + 1).getY() * 2, 20, figurePaint);
            }
            borderPaint.setColor(borderColor);
            figurePaint.setColor(figureColor);
        }
    }

    private ArrayList<String> getLines(String text) {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            if (text.length() >= 20 * (i - 1)) {
                if (text.length() <= 20 * i) {
                    lines.add(text.substring((i - 1) * 20));
                } else {
                    lines.add(text.substring((i - 1) * 20, i * 20));
                }
            }
        }
        return lines;
    }
}
