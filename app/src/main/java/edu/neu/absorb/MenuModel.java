package edu.neu.absorb;

public class MenuModel {
    String title, description;
    int image;

    public MenuModel(String title, String description, int image) {
        this.title = title;
        this.description = description;
        //this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }*/

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
