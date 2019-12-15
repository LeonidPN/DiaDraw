package com.example.diadraw.Models.WorkModels;

import java.util.ArrayList;

public class Line {

    private long figureStartId;

    private long figureEndId;

    private ArrayList<Point> points;

    public Line(Figure start, Figure end) {
        this.figureStartId = start.getId();
        this.figureEndId = end.getId();
        points = new ArrayList<>();
        switch (start.getType()) {
            case FigureType.ACTIVITY:
                points.add(new Point(start.getX(), start.getY() + 110 / 2));
                break;
            case FigureType.START:
            case FigureType.END:
                points.add(new Point(start.getX(), start.getY() + 50 / 2));
                break;
            case FigureType.INPUT:
            case FigureType.OUTPUT:
                points.add(new Point(start.getX(), start.getY() + 100 / 2));
                break;
            case FigureType.CONDITION:
                if (start.getOutputRight() == null) {
                    points.add(new Point(start.getX() - 480 / 2, start.getY()));
                } else {
                    points.add(new Point(start.getX() + 480 / 2, start.getY()));
                }
                break;
        }
        switch (end.getType()) {
            case FigureType.ACTIVITY:
                points.add(new Point(end.getX(), end.getY() - 110 / 2));
                break;
            case FigureType.START:
            case FigureType.END:
                points.add(new Point(end.getX(), end.getY() - 50 / 2));
                break;
            case FigureType.INPUT:
            case FigureType.OUTPUT:
                points.add(new Point(end.getX(), end.getY() - 100 / 2));
                break;
            case FigureType.CONDITION:
                points.add(new Point(end.getX(), end.getY() - 200 / 2));
                break;
        }
    }

    public long getFigureStartId() {
        return figureStartId;
    }

    public long getFigureEndId() {
        return figureEndId;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
