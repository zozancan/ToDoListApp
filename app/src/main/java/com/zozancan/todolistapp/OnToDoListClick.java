package com.zozancan.todolistapp;

import android.view.View;

import com.zozancan.todolistapp.model.ToDoList;

public interface OnToDoListClick {

    void onToDoListClick(ToDoList toDoList);
    void onToDoListDeleteClick(ToDoList toDoList);
}
