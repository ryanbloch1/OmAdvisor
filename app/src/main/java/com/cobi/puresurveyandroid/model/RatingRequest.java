package com.cobi.puresurveyandroid.model;

public class RatingRequest {

    public String DigitalId;
    public String Id;
    public String QuestionId;
    public int Answer;
    public String Comment;

    public RatingRequest(String digitalId, String eventId, String questionId, int answer, String comment) {
        DigitalId = digitalId;
        Id = eventId;
        QuestionId = questionId;
        Answer = answer;
        Comment = comment;
    }

    public String getDigitalId() {
        return DigitalId;
    }

    public void setDigitalId(String digitalId) {
        DigitalId = digitalId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String eventId) {
        Id = eventId;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int answer) {
        Answer = answer;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
