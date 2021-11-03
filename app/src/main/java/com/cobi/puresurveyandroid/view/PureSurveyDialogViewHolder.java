package com.cobi.puresurveyandroid.view;

import android.view.View;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.Dialog;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

/**
 * Created by admin on 2017/10/10.
 */

public class PureSurveyDialogViewHolder extends DialogsListAdapter.DialogViewHolder<Dialog> {
    private TextView userTextView;

    public PureSurveyDialogViewHolder(View itemView) {
        super(itemView);
        userTextView = itemView.findViewById(R.id.dialogUsername);
    }

    @Override
    public void onBind(final Dialog dialog) {
        super.onBind(dialog);
        if (dialog.getUsers() != null && !dialog.getUsers().isEmpty()) {
            IUser user = dialog.getUsers().get(0);
            userTextView.setText(user.getName());

            boolean isUnread = dialog.getUnreadCount() > 0;

            //Setting Background Colour
            container.setBackgroundColor(isUnread ? ResourceHelper.getColour(R.color.white) : ResourceHelper.getColour(R.color.inbox_read_background));
            //Setting Title TypeFace
            userTextView.setTypeface(isUnread ? ResourceHelper.getFont(R.font.montserrat_black) : ResourceHelper.getFont(R.font.montserrat_light));
            //Setting Subject TypeFace
            tvName.setTypeface(isUnread ?  ResourceHelper.getFont(R.font.montserrat_bold) : ResourceHelper.getFont(R.font.montserrat_light));
            //Setting Date TypeFace
            tvDate.setTypeface(isUnread ? ResourceHelper.getFont(R.font.montserrat_bold) : ResourceHelper.getFont(R.font.montserrat_regular));
            //Setting Date TextColour
            tvDate.setTextColor(isUnread ? ResourceHelper.getColour(R.color.inbox_unread_date) : ResourceHelper.getColour(R.color.inbox_read_date));

            tvLastMessage.setText(tvLastMessage.getText().toString().replaceAll("<[^>]*>", "").trim());
        }
    }
}
