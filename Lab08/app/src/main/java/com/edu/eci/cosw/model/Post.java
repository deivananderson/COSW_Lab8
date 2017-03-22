package com.edu.eci.cosw.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Julian Gonzalez Prieto (Avuuna, la Luz del Alba) on 11/9/16.
 */
public class Post implements Serializable {
    private String text;
    private String photo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
