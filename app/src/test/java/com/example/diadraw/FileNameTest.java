package com.example.diadraw;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class FileNameTest {

    private FileService fileService = new FileService();

    @Test
    public void newFileName_empty(){
        String fileName = "";
        Assert.assertFalse(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_letters(){
        String fileName = "abcd";
        Assert.assertTrue(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_digits(){
        String fileName = "158796";
        Assert.assertTrue(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_specialSigns(){
        String fileName = "!@$%&*+-_";
        Assert.assertFalse(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_all_startLetter(){
        String fileName = "a!@*12+-_g";
        Assert.assertTrue(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_all_startDigit(){
        String fileName = "6a!@*12+-_g";
        Assert.assertTrue(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_all_startSpecialSign(){
        String fileName = "!6a!@#$b%&*12+-_g";
        Assert.assertFalse(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_1length20(){
        String fileName = "111111111";
        Assert.assertTrue(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_length20(){
        String fileName = "11111111111111111111";
        Assert.assertTrue(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

    @Test
    public void newFileName_20length(){
        String fileName = "111111111111111111111";
        Assert.assertFalse(fileService.checkFileName(fileName,new ArrayList<FileModel>()));
    }

}
