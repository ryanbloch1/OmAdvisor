package com.cobi.puresurveyandroid.model;

public class ProfileItem {

    CustomerProfileEnum id;
    int image;
    String title;

    public CustomerProfileEnum getId() {
        return id;
    }

    public void setId(CustomerProfileEnum id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProfileItem(CustomerProfileEnum id, int image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }
}
