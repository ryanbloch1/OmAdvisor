package com.cobi.puresurveyandroid.view;

/**
 * Created by admin on 2017/10/19.
 */

/*
 * EditText.java
 * medscheme-android
 *
 * Copyright (c) 2017 Medscheme. All rights reserved.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.lang.reflect.Field;

/**
 * Created by shadrack on 2017/04/18.
 */

public class EditText extends LinearLayout implements View.OnFocusChangeListener, TextWatcher {
    public static final int INPUT_TYPE_TEXT = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    public static final int INPUT_TYPE_NUMBER = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED;

    private TextInputLayout mHint;
    private AppCompatEditText mText;
    private TextView mError;

    public EditText(Context context) {
        super(context);
        init(null, 0);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EditText, defStyle, 0);

        String text = typedArray.getString(R.styleable.EditText_android_text);
        String hint = typedArray.getString(R.styleable.EditText_android_hint);
//        boolean showBackground = typedArray.getBoolean(R.styleable.EditText_showBackground, true);
        boolean enabled = typedArray.getBoolean(R.styleable.EditText_enabled, true);
        boolean errorEnabled = typedArray.getBoolean(R.styleable.EditText_errorEnabled, false);
        int inputType = typedArray.getInt(R.styleable.EditText_android_inputType, EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);

        typedArray.recycle();

        // Inflate layout
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_text_field, this, true);

        int color = ResourceHelper.getColour(android.R.color.white);
        setBackgroundColor(color);
        setOrientation(VERTICAL);

        mHint = findViewById(R.id.hint);
        mText = findViewById(R.id.text);
        if (inputType == InputType.TYPE_CLASS_NUMBER) {
            mText.setInputType(INPUT_TYPE_NUMBER);
        } else {
            mText.setInputType(inputType);
        }
        mText.setText(text);
        mError = findViewById(R.id.error);
        mHint.setHint(hint);
        mText.addTextChangedListener(this);
        mText.setOnFocusChangeListener(this);
        //mText.setOnClickListener(this);
        disable(enabled);
        mError.setVisibility(errorEnabled ? INVISIBLE : GONE);
        mText.setId(this.getId());

//        if (!showBackground) {
//            mText.setBackgroundColor(ResourceHelper.getColour(android.R.color.transparent));
//        }
    }

    //    private Typeface getTypeFace() {
    //        return Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
    //    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mHint.getHint().toString(), mText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        mHint.setHint(savedState.getHint());
        mText.setText(savedState.getText());
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        mText.setOnClickListener(onClickListener);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        view.dispatchWindowFocusChanged(hasFocus);
    }

    public Editable getText() {
        return mText.getText();
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void setHint(String hint) {
        mHint.setHint(hint);
    }

    public void setError(String error) {
        mError.setText(error != null ? error : "");
        mText.setTextColor(error != null ? ResourceHelper.getColour(R.color.colorError) : ResourceHelper.getColour(R.color.colorAccent));
        mError.setVisibility(error != null ? VISIBLE : INVISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setInputTextLayoutColor(error != null ? ResourceHelper.getColour(R.color.colorError) : ResourceHelper.getColour(R.color.colorAccent));
        }
    }

    public void setPasswordToggleEnabled(boolean enabled) {
        mHint.setPasswordVisibilityToggleEnabled(enabled);
    }

    public void setInputType(int inputType) {
        mText.setInputType(inputType);
    }

    public void setIsVisible(boolean isVisible) {
        setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        mText.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        mText.removeTextChangedListener(textWatcher);
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        mText.setOnKeyListener(onKeyListener);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        mText.setOnEditorActionListener(onEditorActionListener);
    }

    public void disable(boolean disable) {
        mText.setFocusable(disable);
        mText.setFocusableInTouchMode(disable);
        mText.setLongClickable(disable);
    }

    public void setInputTextLayoutColor(@ColorInt int color) {
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(mHint, new ColorStateList(new int[][]{{0}}, new int[]{color}));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(mHint, new ColorStateList(new int[][]{{0}}, new int[]{color}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mError.getVisibility() == View.VISIBLE) {
            setError(null);
        }
        if (mHint != null && mHint.isPasswordVisibilityToggleEnabled()) {
            setPasswordToggleEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setInputFilter(InputFilter[] inputFilters) {
        mText.setFilters(inputFilters);
    }

    /**
     * Convenience class to save / restore the EditText state.
     */
    protected static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private final String hint;
        private final String text;

        private SavedState(Parcelable superState, String hint, String text) {
            super(superState);
            this.hint = hint;
            this.text = text;
        }

        private SavedState(Parcel in) {
            super(in);
            hint = in.readString();
            text = in.readString();
        }

        public String getHint() {
            return hint;
        }

        public String getText() {
            return text;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeString(hint);
            destination.writeString(text);
        }
    }
}

