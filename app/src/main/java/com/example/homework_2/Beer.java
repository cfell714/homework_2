package com.example.homework_2;

import android.graphics.drawable.Drawable;

public class Beer {
    private String name;
    private String description;
    private String image;
    private Drawable greenSaber;
    private Drawable redSaber;
    private boolean showRed;


    public Beer(String name, String description, String image, Drawable greenSaber, Drawable redSaber){
        this.name = name;
        this.description = description;
        this.image = image;
        this.greenSaber = greenSaber;
        this.redSaber = redSaber;
        showRed = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Drawable getGreenSaber() {
        return greenSaber;
    }

    public void setGreenSaber(Drawable greenSaber) {
        this.greenSaber = greenSaber;
    }

    public Drawable getRedSaber() {
        return redSaber;
    }

    public void setRedSaber(Drawable redSaber) {
        this.redSaber = redSaber;
    }

    public boolean isShowRed() {
        return showRed;
    }

    public void setShowRed(boolean showRed) {
        this.showRed = showRed;
    }
}
