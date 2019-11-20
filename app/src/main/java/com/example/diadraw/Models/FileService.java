package com.example.diadraw.Models;

import android.content.Context;

import com.example.diadraw.Models.WorkModels.Figure;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileService {

    private static final String EXTENSION = ".json";

    public List<FileModel> getFilesList(Context context) throws IOException {
        Gson gson = new GsonBuilder().create();
        List<FileModel> list = new ArrayList<>();
        String[] files = context.fileList();
        Type typeOfFileModel = new TypeToken<FileModel>() {
        }.getType();
        for (int i = 0; i < files.length; i++) {
            String[] fileName = files[i].split("\\.");
            if (fileName.length == 2) {
                String ext = fileName[1];
                if (ext.equals("json")) {
                    FileInputStream fin = context.openFileInput(files[i]);
                    byte[] bytes = new byte[fin.available()];
                    fin.read(bytes);
                    String text = new String(bytes);
                    FileModel file = gson.fromJson(text, typeOfFileModel);
                    list.add(file);
                    fin.close();
                }
            }
        }
        return list;
    }

    public FileModel getFile(Context context, String fileName) throws IOException {
        Gson gson = new GsonBuilder().create();
        Type typeOfFileModel = new TypeToken<FileModel>() {
        }.getType();

        FileInputStream fin = context.openFileInput(fileName + EXTENSION);
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        String text = new String(bytes);
        FileModel file = gson.fromJson(text, typeOfFileModel);
        fin.close();

        return file;
    }

    public void createFile(Context context, String fileName) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName + EXTENSION, Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        FileModel fileModel = new FileModel(fileName, new Date(), new ArrayList<Figure>());
        Type typeOfFileModel = new TypeToken<FileModel>() {
        }.getType();
        String text = gson.toJson(fileModel, typeOfFileModel);
        fos.write(text.getBytes());
        fos.close();
    }

    public void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName + EXTENSION);
    }

    public void renameFile(Context context, String fileNameOld, String fileNameNew) throws IOException {
        Gson gson = new GsonBuilder().create();
        Type typeOfFileModel = new TypeToken<FileModel>() {
        }.getType();

        FileInputStream fin = context.openFileInput(fileNameOld + EXTENSION);
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        String text = new String(bytes);
        FileModel file = gson.fromJson(text, typeOfFileModel);
        fin.close();

        context.deleteFile(fileNameOld + EXTENSION);
        file.setName(fileNameNew);

        FileOutputStream fos = context.openFileOutput(fileNameNew + EXTENSION, Context.MODE_PRIVATE);
        text = gson.toJson(file, typeOfFileModel);
        fos.write(text.getBytes());
        fos.close();
    }

    public void changeFile(Context context, FileModel model) throws IOException {
        Gson gson = new GsonBuilder().create();
        Type typeOfFileModel = new TypeToken<FileModel>() {
        }.getType();

        FileInputStream fin = context.openFileInput(model.getName() + EXTENSION);
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        String text = new String(bytes);
        FileModel file = gson.fromJson(text, typeOfFileModel);
        fin.close();

        context.deleteFile(model.getName() + EXTENSION);

        FileOutputStream fos = context.openFileOutput(model.getName() + EXTENSION, Context.MODE_PRIVATE);
        text = gson.toJson(model, typeOfFileModel);
        fos.write(text.getBytes());
        fos.close();
    }

}
