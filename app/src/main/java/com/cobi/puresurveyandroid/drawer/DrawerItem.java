package com.cobi.puresurveyandroid.drawer;

import android.graphics.drawable.Drawable;

import com.cobi.puresurveyandroid.model.MenuEnum;

public class DrawerItem {

    public int id;
    public String title;
    public boolean canNavigate;
    public boolean hasDivider;
    public DrawerType type;
    public Drawable menuIcon;
    public DrawerItem(int id, String title, boolean canNavigate, boolean hasDivider, DrawerType type, Drawable menuIcon) {
        this.title = title;
        this.canNavigate = canNavigate;
        this.hasDivider = hasDivider;
        this.type = type;
        this.menuIcon = menuIcon;
        this.id = id;
    }

    public final int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Drawable menuIcon) {
        this.menuIcon = menuIcon;
    }

    public enum DrawerType {
        TITLE, BUTTON
    }
}
