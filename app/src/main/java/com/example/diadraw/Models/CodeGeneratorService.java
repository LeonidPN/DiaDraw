package com.example.diadraw.Models;

import com.example.diadraw.Models.WorkModels.Figure;
import com.example.diadraw.Models.WorkModels.FigureType;

import java.util.ArrayList;

public class CodeGeneratorService {

    private static final String INCORRECT = "Некорректная блок-схема";

    public ArrayList<String> getCode(ArrayList<Figure> figures) {
        ArrayList<String> code = new ArrayList<>();
        if (!startEnd(figures)) {
            code = new ArrayList<>();
            code.add(INCORRECT);
            return code;
        }
        for (Figure figure : figures) {
            if (figure.getType().equals(FigureType.CONDITION)) {
                if (figure.getOutputRight() == null || figure.getOutputLeft() == null) {
                    code = new ArrayList<>();
                    code.add(INCORRECT);
                    return code;
                }
            } else {
                if (figure.getOutput() == null) {
                    code = new ArrayList<>();
                    code.add(INCORRECT);
                    return code;
                }
            }
        }
        code.add("public static void main(String[] args) {");
        if (input(figures)) {
            code.add("Scanner in = new Scanner(System.in);");
        }
        Figure start = figures.get(0);
        for (Figure figure : figures) {
            if (figure.getType().equals(FigureType.START)) {
                start = figure;
                break;
            }
        }
        start = start.getOutput();
        while (!start.getType().equals(FigureType.END)) {
            if (!start.getType().equals(FigureType.CONDITION)) {
                code.add(getFigureCode(start));
                start = start.getOutput();
            } else {

            }
        }
        if (input(figures)) {
            code.add("in.close();");
        }
        code.add("}");
        return code;
    }

    private String getFigureCode(Figure figure) {
        String res = "";
        switch (figure.getType()) {
            case FigureType.ACTIVITY:
                res = figure.getText() + ";";
                break;
            case FigureType.INPUT:
                res = figure.getText() + " = in.next();";
                break;
            case FigureType.OUTPUT:
                res = "System.out.println(" + figure.getText() + ");";
                break;
            case FigureType.CYCLE_START:
                res = "while(" + figure.getText() + "){";
                break;
            case FigureType.CYCLE_END:
                res = "}";
                break;
        }
        return res;
    }

    private ArrayList<String> getConditionCode(Figure figure) {
        Figure left;
        Figure right = figure.getOutputRight();
        Figure end = figure.getOutputLeft();
        while (!right.getType().equals(FigureType.END)) {
            left = figure.getOutputLeft();
            while (!left.getType().equals(FigureType.END)) {
                if (right.equals(left)) {
                    end = right;
                }
                left = left.getOutput();
            }
            right = right.getOutput();
        }
        left = figure.getOutputLeft();
        right = figure.getOutputRight();
        ArrayList<String> codeLeft = new ArrayList<>();
        ArrayList<String> codeRight = new ArrayList<>();
        while (!left.equals(end)) {
            codeLeft.add(getFigureCode(left));
            left.getOutput();
        }
        while (!right.equals(end)) {
            codeRight.add(getFigureCode(right));
            right.getOutput();
        }
        ArrayList<String> res = new ArrayList<>();
        res.add("if(" + figure.getText() + "){");
        for (String str : codeRight) {
            res.add(str);
        }
        res.add("}");
        if (codeLeft.size() > 0) {
            res.add("else{");
            for (String str : codeLeft) {
                res.add(str);
            }
            res.add("}");
        }
        return res;
    }

    private boolean startEnd(ArrayList<Figure> figures) {
        boolean start = false;
        boolean end = false;
        for (Figure figure : figures) {
            if (figure.getType().equals(FigureType.START)) {
                start = true;
            }
            if (figure.getType().equals(FigureType.END)) {
                end = true;
            }
        }
        return start && end;
    }

    private boolean input(ArrayList<Figure> figures) {
        boolean input = false;
        for (Figure figure : figures) {
            if (figure.getType().equals(FigureType.INPUT)) {
                input = true;
            }
        }
        return input;
    }

}
