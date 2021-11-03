package com.cobi.puresurveyandroid.view;

import android.content.Context;
import android.util.AttributeSet;

import com.cobi.puresurveyandroid.util.ViewHelper;

/**
 * Created by admin on 2018/04/20.
 */

public class ResizeTextView extends androidx.appcompat.widget.AppCompatTextView {

    private final static int sMinSize = 20;
    private final int mOriginalTextSize;
    private final int mMinTextSize;

    public ResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOriginalTextSize = (int) getTextSize();
        mMinTextSize = (int) sMinSize;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        ViewHelper.resizeText(this, mOriginalTextSize, mMinTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ViewHelper.resizeText(this, mOriginalTextSize, mMinTextSize);
    }
}
