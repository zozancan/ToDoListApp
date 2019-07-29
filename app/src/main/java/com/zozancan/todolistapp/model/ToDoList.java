package com.zozancan.todolistapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToDoList implements Serializable {
    private String id;
    private String name;

    private List<ToDoListItem> toDoListItems = new ArrayList<ToDoListItem>();

    public ToDoList() {

    }

    public ToDoList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToDoListItem> getToDoListItems() {
        return toDoListItems;
    }

    public void setToDoListItems(List<ToDoListItem> toDoListItems) {
        this.toDoListItems = toDoListItems;
    }

    public void addToDoListItem(ToDoListItem toDoListItem){
        this.toDoListItems.add(toDoListItem);
    }
}
