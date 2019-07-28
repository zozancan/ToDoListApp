package com.zozancan.todolistapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String email;

    private List<ToDoList> toDoLists = new ArrayList<ToDoList>();

    public User() {

    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addNewList(ToDoList list){
        this.toDoLists.add(list);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", toDoLists=" + toDoLists +
                '}';
    }
}
