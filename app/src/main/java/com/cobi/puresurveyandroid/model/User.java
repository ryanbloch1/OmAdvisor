package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.database.PureSurveyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by admin on 2017/10/09.
 */

@Table(database = PureSurveyDatabase.class)
public class User implements IUser {

    @PrimaryKey
    private String id;
    @Column
    private String name;
    private boolean online;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
