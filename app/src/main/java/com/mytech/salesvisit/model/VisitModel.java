package com.mytech.salesvisit.model;

public class VisitModel {
     int id;//": 20,

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;//": "Performance Evaluation"
    public String toString()
    {
        return text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value;
}
