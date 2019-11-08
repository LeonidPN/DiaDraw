package com.example.diadraw.Models.WorkModels;

import java.util.ArrayList;
import java.util.Date;

public class FileModel {

    private String name;

    private Date date;

    private ArrayList<Figure> figures;

    public FileModel() {
    }

    public FileModel(String name, Date date, ArrayList<Figure> figures) {
        this.name = name;
        this.date = date;
        this.figures=figures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void setFigures(ArrayList<Figure> figures) {
        this.figures = figures;
    }
}
