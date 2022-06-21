package com.mytech.salesvisit.net;

import androidx.annotation.NonNull;

public class BaseId {
    public  int id;
    public  String text;

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

    @NonNull
    @Override
    public String toString() {
        return text;
    }
}
