package com.example.diadraw.Models.WorkModels;

public class Figure {

    private long id;

    private String type;

    private float x;

    private float y;

    private String text;

    public Figure(long id, String type, float x, float y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        text = "";
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
