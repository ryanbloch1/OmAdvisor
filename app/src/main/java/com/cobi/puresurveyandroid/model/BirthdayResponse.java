package com.cobi.puresurveyandroid.model;

import java.util.List;

public class BirthdayResponse {
    List<Birthday> birthdays;

    public BirthdayResponse(List<Birthday> birthdays) {
        this.birthdays = birthdays;
    }

    public List<Birthday> getBirthdays() {
        return birthdays;
    }
}
