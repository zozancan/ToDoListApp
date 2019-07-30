package com.zozancan.todolistapp;

import com.zozancan.todolistapp.model.ToDoList;
import com.zozancan.todolistapp.model.ToDoListItem;

public interface OnItemClick {

    void onItemClick(ToDoListItem ToDoListItem);
    void onItemDeleteClick(ToDoListItem ToDoListItem);
    void onItemStatusClick(ToDoListItem toDoListItem);
}
