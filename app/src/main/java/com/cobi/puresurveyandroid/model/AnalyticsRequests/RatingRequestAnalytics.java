package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public class RatingRequestAnalytics {

    public String UserId;
    public int Rating;
    public String Comment;
    public int PageType;
    public String Suggestions;

    public RatingRequestAnalytics(String userId, int rating, String comment, int pageType, String suggestions) {
        UserId = userId;
        Rating = rating;
        Comment = comment;
        PageType = pageType;
        Suggestions = suggestions;
    }

}


