package com.mytech.salesvisit.net;

import androidx.annotation.NonNull;

public class Company  {
    public  String id;

    public  String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }
}
