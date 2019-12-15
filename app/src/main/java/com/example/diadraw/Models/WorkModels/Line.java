package com.example.diadraw.Models.WorkModels;

import java.util.ArrayList;

public class Line {

    private long figureStartId;

    private long figureEndId;

    private ArrayList<Point> points;

    public Line(long figureStartId, long figureEndId, ArrayList<Point> points) {
        this.figureStartId = figureStartId;
        this.figureEndId = figureEndId;
        this.points = points;
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
