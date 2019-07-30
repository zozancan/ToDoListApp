package com.zozancan.todolistapp.model;

import java.io.Serializable;

public class Filter implements Serializable {
    private String name;
    private Boolean expired;
    private Boolean completed;

    public Filter() {
        this.name = "";
        this.expired = false;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
