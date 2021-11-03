package com.cobi.puresurveyandroid.model;

import android.content.Context;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/09.
 */

public class Dialog implements IDialog<UserCustomerEvent> {

    private String id;
    private String dialogName;
    private List<User> users;
    private UserCustomerEvent lastMessage;
    private List<UserCustomerEvent> messages;

    private int unreadCount;

    public Dialog(String id, String name, List<User> users, List<UserCustomerEvent> messages) {

        this.id = id;
        this.dialogName = name;
        this.users = users;
        this.messages = messages;
        this.lastMessage = getMessages().get(0);
        this.unreadCount = lastMessage.isRead() ? 0 : 1;
    }

    public static Dialog getDialogForId(Context context, String id) {
        Map<String, Dialog> dialogMap;

        createDialogsFromEvents(context);

        dialogMap = ((PureSurveyApplication) context.getApplicationContext()).getDialogs();
        if (dialogMap.containsKey(id)) {
            return dialogMap.get(id);
        } else {
            return null;
        }
    }

    public static List<List<Dialog>> createDialogsFromEvents(Context context) {
        Map<String, Dialog> dialogMap = ((PureSurveyApplication) context.getApplicationContext()).getDialogs();
        if (dialogMap == null) {
            dialogMap = new HashMap<>();
        }
        List<List<Dialog>> dialogs = new ArrayList<>();
        List<Date> datesOfDialogGroups = new ArrayList<>();

        List<UserCustomerEvent> messages = SQLite.select().from(UserCustomerEvent.class).where(UserCustomerEvent_Table.replyToMessageId.isNull()).queryList();
        for (int i = 0; i < messages.size(); i++) {

            UserCustomerEvent message = messages.get(i);
            if (message.getType().toLowerCase().equals("recall") || message.getType().toLowerCase().equals("register")) {
                continue;
            }
            List<UserCustomerEvent> replies = SQLite.select().from(UserCustomerEvent.class).where(UserCustomerEvent_Table.replyToMessageId.eq(message.getMessageId())).queryList();
            List<User> users = SQLite.select().
                    from(User.class).
                    where(User_Table.id.in(SQLite.select(UserCustomerEvent_Table.from).
                            from(UserCustomerEvent.class).
                            where(UserCustomerEvent_Table.replyToMessageId.eq(message.getMessageId())).or(User_Table.id.eq(message.getFrom())))).queryList();

            replies.add(message);

            Dialog dialog = new Dialog(message.getMessageId(), message.getSubject(), users, replies);

            dialogMap.put(dialog.getId(), dialog);

            Date dateOfLastMessage = dialog.getLastMessage().getCreatedAt();
            int indexOfDate = -1;
            for (int j = 0; j < datesOfDialogGroups.size(); j++) {
                Date date = datesOfDialogGroups.get(j);
                if (DateHelper.sameDay(date, dateOfLastMessage)) {
                    indexOfDate = j;
                    break;
                }
            }

            if (indexOfDate >= 0) {
                dialogs.get(indexOfDate).add(dialog);
            } else {
                List<Dialog> newGroup = new ArrayList<>();
                newGroup.add(dialog);
                dialogs.add(newGroup);
                datesOfDialogGroups.add(dateOfLastMessage);
            }
        }
        ((PureSurveyApplication) context.getApplicationContext()).setDialogs(dialogMap);

        Collections.sort(dialogs, new Comparator<List<Dialog>>() {
            @Override
            public int compare(List<Dialog> dialogs, List<Dialog> t1) {
                Date firstDate = dialogs.get(0).getLastMessage().getCreatedAt();
                Date secondDate = t1.get(0).getLastMessage().getCreatedAt();
                return secondDate.compareTo(firstDate);
            }
        });
        for (List<Dialog> d : dialogs) {
            Collections.sort(d, new Comparator<Dialog>() {
                @Override
                public int compare(Dialog dialogs, Dialog t1) {
                    Date firstDate = dialogs.getLastMessage().getCreatedAt();
                    Date secondDate = t1.getLastMessage().getCreatedAt();
                    return secondDate.compareTo(firstDate);
                }
            });
        }
        return dialogs;
    }

    public List<UserCustomerEvent> getMessages() {
        Collections.sort(messages, new Comparator<UserCustomerEvent>() {
            @Override
            public int compare(UserCustomerEvent event, UserCustomerEvent t1) {
                return t1.getCreatedAt().compareTo(event.getCreatedAt());
            }
        });
        return messages;
    }

    public void setMessages(List<UserCustomerEvent> messages) {
        this.messages = messages;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return null;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public UserCustomerEvent getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(UserCustomerEvent lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}

