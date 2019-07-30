package com.zozancan.todolistapp.toDoList;
 
import com.zozancan.todolistapp.model.ToDoList;

public interface OnToDoListClick {

    void onToDoListClick(ToDoList toDoList);
    void onToDoListDeleteClick(ToDoList toDoList);
}
