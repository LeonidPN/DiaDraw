package com.example.diadraw.Models.WorkModels;

import java.util.ArrayList;

public class Figure {

    private long id;

    private String type;

    private float x;

    private float y;

    private String text;

    private Figure output;

    private Figure outputLeft;

    private Figure outputRight;

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

    public Figure getOutput() {
        return output;
    }

    public void setOutput(Figure output) {
        this.output = output;
    }

    public Figure getOutputLeft() {
        return outputLeft;
    }

    public void setOutputLeft(Figure outputLeft) {
        this.outputLeft = outputLeft;
    }

    public Figure getOutputRight() {
        return outputRight;
    }

    public void setOutputRight(Figure outputRight) {
        this.outputRight = outputRight;
    }
}
