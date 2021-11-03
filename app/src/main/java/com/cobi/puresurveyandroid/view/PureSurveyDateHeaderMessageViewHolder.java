package com.cobi.puresurveyandroid.view;

import androidx.core.content.res.ResourcesCompat;
import android.view.View;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.stfalcon.chatkit.messages.MessageHolders;

import java.util.Date;

/**
 * Created by admin on 2017/10/17.
 */

public class PureSurveyDateHeaderMessageViewHolder extends MessageHolders.DefaultDateHeaderViewHolder {

    public PureSurveyDateHeaderMessageViewHolder(final View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Date date) {
        text.setTypeface(ResourcesCompat.getFont(PureSurveyApplication.getContext(), R.font.montserrat_regular));
        super.onBind(date);
    }
}
