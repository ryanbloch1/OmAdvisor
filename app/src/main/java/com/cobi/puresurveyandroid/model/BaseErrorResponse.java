package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseErrorResponse {
    private long status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("message")
    @Expose
    private List<Message> message = null;
    @SerializedName("errors")
    @Expose
    private List<Error> errors = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public long getStatus() {
        if(code!=null && !code.isEmpty()) {
            return Long.parseLong(code);
        }
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public class Error {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("level")
        @Expose
        private String level;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("more_info")
        @Expose
        private String moreInfo;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMoreInfo() {
            return moreInfo;
        }

        public void setMoreInfo(String moreInfo) {
            this.moreInfo = moreInfo;
        }

    }


    public class Message {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("messageSections")
        @Expose
        private List<MessageSection> messageSections = null;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public List<MessageSection> getMessageSections() {
            return messageSections;
        }

        public void setMessageSections(List<MessageSection> messageSections) {
            this.messageSections = messageSections;
        }

    }




}
