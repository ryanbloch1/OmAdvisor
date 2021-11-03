
package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.DateHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class ReIntermediary {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("initials")
    @Expose
    private String initials;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("action")
    @Expose
    private Integer action;

    @SerializedName("actionDate")
    @Expose
    private Object  actionDate;

    public ReIntermediary(String id, String firstName, String lastName, String initials, String title, Integer action, Object actionDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials = initials;
        this.title = title;
        this.action = action;
        this.actionDate = actionDate;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Object getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }




    public static Comparator<ReIntermediary> pComparator = new Comparator<ReIntermediary>() {

        public int compare(ReIntermediary s1, ReIntermediary s2) {

            if (s1.getAction() == s2.getAction()) {

                if (s1.getAction() == 0) {

//                    return DateHelper.StringToDate(s1.getActionDate()).compareTo(DateHelper.StringToDate(s2.getActionDate()));

                    return s1.getFirstName().compareTo(s2.getFirstName());

                } else {
                    return s1.getFirstName().compareTo(s2.getFirstName());
                }


            } else {
                return s1.getAction().compareTo(s2.getAction());
            }

        }
    };


}