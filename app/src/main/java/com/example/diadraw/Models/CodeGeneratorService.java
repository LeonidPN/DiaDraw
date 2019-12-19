package com.example.diadraw.Models;

import com.example.diadraw.Models.WorkModels.Figure;
import com.example.diadraw.Models.WorkModels.FigureType;

import java.util.ArrayList;

public class CodeGeneratorService {

    private static final String INCORRECT = "Некорректная блок-схема";

    private ArrayList<String> code = new ArrayList<>();

    private ArrayList<Figure> figures = new ArrayList<>();

    public ArrayList<String> getCode(ArrayList<Figure> figures) {
        this.figures = figures;
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
                if (!figure.getType().equals(FigureType.END)) {
                    if (figure.getOutput() == null) {
                        code = new ArrayList<>();
                        code.add(INCORRECT);
                        return code;
                    }
                }
            }
        }
        for (int i = 0; i < figures.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < figures.size(); j++) {
                if (!figures.get(j).getType().equals(FigureType.START)) {
                    if (figures.get(i).getType().equals(FigureType.CONDITION)) {
                        if (figures.get(i).getOutputRight().getId() == (figures.get(j).getId()) ||
                                figures.get(i).getOutputLeft().getId() == (figures.get(j).getId())) {
                            flag = false;
                            break;
                        }
                    } else {
                        if (!figures.get(i).getType().equals(FigureType.END)) {
                            if (figures.get(i).getOutput().getId() == (figures.get(j).getId())) {
                                flag = false;
                                break;
                            }
                        } else {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (flag) {
                code = new ArrayList<>();
                code.add(INCORRECT);
                return code;
            }
        }
        int count = 0;
        for (Figure figure : figures) {
            if (figure.getType().equals(FigureType.CYCLE_START)) {
                count++;
            }
            if (figure.getType().equals(FigureType.CYCLE_END)) {
                count--;
            }
        }
        if (count != 0) {
            code = new ArrayList<>();
            code.add(INCORRECT);
            return code;
        }
        code.add("public static void main(String[] args) {");
        if (input(figures)) {
            code.add("Scanner in;");
        }
        Figure start = figures.get(0);
        for (Figure figure : figures) {
            if (figure.getType().equals(FigureType.START)) {
                start = figure;
                break;
            }
        }
        start = start.getOutput();
        code.addAll(getNextFigureCode(start, null));
        code.add("}");
        return code;
    }

    private ArrayList<String> getNextFigureCode(Figure figure, Figure end) {
        for (Figure _figure : figures) {
            if (_figure.getId() == figure.getId()) {
                figure = _figure;
                break;
            }
        }
        ArrayList<String> res = new ArrayList<>();
        if (figure.getType().equals(FigureType.END)) {
            return res;
        }
        if (end != null && figure == end) {
            return res;
        }
        if (figure.getType().equals(FigureType.CONDITION)) {
            Figure right = figure.getOutputRight();
            Figure left = figure.getOutputLeft();
            end = getConditionEnd(right, left);
            res.addAll(getConditionCode(figure, end));
            res.addAll(getNextFigureCode(end, null));
        } else {
            res.addAll(getFigureCode(figure));
            res.addAll(getNextFigureCode(figure.getOutput(), null));
        }
        return res;
    }

    private Figure getConditionEnd(Figure right, Figure left) {
        Figure end = null;
        while (!right.getType().equals(FigureType.END)) {
            Figure _left = left;
            while (!_left.getType().equals(FigureType.END)) {
                if (right.equals(_left)) {
                    end = right;
                }
                if (_left.getType().equals(FigureType.CONDITION)) {
                    _left = _left.getOutputRight();
                } else {
                    _left = _left.getOutput();
                }
            }
            if (right.getType().equals(FigureType.CONDITION)) {
                right = right.getOutputRight();
            } else {
                right = right.getOutput();
            }
        }
        if (end == null) {
            end = right;
        }
        return end;
    }

    private ArrayList<String> getFigureCode(Figure figure) {
        ArrayList<String> res = new ArrayList<>();
        switch (figure.getType()) {
            case FigureType.ACTIVITY:
                res.add(figure.getText() + ";");
                break;
            case FigureType.INPUT:
                res.add("in = new Scanner(System.in);");
                res.add(figure.getText() + " = in.next();");
                res.add("in.close();");
                break;
            case FigureType.OUTPUT:
                res.add("System.out.println(" + figure.getText() + ");");
                break;
            case FigureType.CYCLE_START:
                res.add("while(" + figure.getText() + "){");
                break;
            case FigureType.CYCLE_END:
                res.add("}");
                break;
        }
        return res;
    }

    private ArrayList<String> getConditionCode(Figure figure, Figure end) {
        ArrayList<String> res = new ArrayList<>();
        res.add("if(" + figure.getText() + "){");
        res.addAll(getNextFigureCode(figure.getOutputLeft(), end));
        res.add("}else{");
        res.addAll(getNextFigureCode(figure.getOutputRight(), end));
        res.add("}");
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
