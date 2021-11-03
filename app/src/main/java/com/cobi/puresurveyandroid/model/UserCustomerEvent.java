package com.cobi.puresurveyandroid.model;

import android.content.Context;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.database.PureSurveyDatabase;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;
import java.util.Map;

/**
 * Created by admin on 2017/10/04.
 */
@Table(database = PureSurveyDatabase.class)
public class UserCustomerEvent extends BaseModel implements IMessage {
    @Column
    private String type;

    @Column
    @PrimaryKey
    @SerializedName("message_id")
    private String messageId;

    @Column
    private String subject;

    @Column
    private String from;

    @Column
    private String body;

    @Column
    private String time;

    @Column
    private boolean reply;

    @Column
    @SerializedName("replyto_message_id")
    private String replyToMessageId;

    @Column
    @SerializedName("read")
    private boolean isRead;

    public static UserCustomerEvent createFromMap(Map<String, String> properties) {

        Gson gson = new GsonBuilder().create();
        JsonElement jsonElement = gson.toJsonTree(properties);
        return gson.fromJson(jsonElement, UserCustomerEvent.class);
    }

    public static void handleRecall(Context context, String messageId) {
        UserCustomerEvent event = SQLite.select().from(UserCustomerEvent.class).where(UserCustomerEvent_Table.messageId.eq(messageId)).querySingle();
        if (event != null) {
            ModelAdapter<UserCustomerEvent> adapter = FlowManager.getModelAdapter(UserCustomerEvent.class);
            adapter.delete(event);
            ((PureSurveyApplication) context.getApplicationContext()).setDialogs(null);
        }
    }

    public boolean isRead() {
//        if (type.toLowerCase().equals("reply")) {
//            return true;
//
//        } else {
            return isRead;
//        }
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(String replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    @Override
    public String getId() {
        return messageId;
    }

    @Override
    public String getText() {
        return body;
    }

    @Override
    public Date getCreatedAt() {
        return DateHelper.ISO8601ToDate(time);
    }

    @Override
    public User getUser() {
        return new User(from, from);
    }
}
