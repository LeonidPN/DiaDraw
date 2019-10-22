package com.example.diadraw.Presenters;

import com.example.diadraw.Views.ChooseFileListAdapter;

public class ChooseFileListPresenter {
    private ChooseFileListAdapter.ChooseFileListViewHolder holder;
    private int position;

    public  ChooseFileListPresenter(ChooseFileListAdapter.ChooseFileListViewHolder holder, int position){
        this.holder=holder;
        this.position=position;
    }

}
