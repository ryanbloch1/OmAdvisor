package com.cobi.puresurveyandroid.util;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.cobi.puresurveyandroid.PureSurveyApplication;

public class ResourceHelper {

    @ColorInt
    public static int getColour(@ColorRes int colorRes) {

        try{
            return ContextCompat.getColor(PureSurveyApplication.getContext(), colorRes);
        }catch(Exception e){
            return  0;
        }


    }

    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(PureSurveyApplication.getContext(), drawableRes);
    }

    public static Typeface getFont(@FontRes int fontRes) {
        return ResourcesCompat.getFont(PureSurveyApplication.getContext(), fontRes);
    }

    public static String getString(@StringRes int stringRes) {
        return PureSurveyApplication.getContext().getString(stringRes);
    }
}
