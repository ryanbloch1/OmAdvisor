/*
 * PieChart.java
 * medscheme-android
 *
 * Copyright (c) 2017 Medscheme. All rights reserved.
 */

package com.cobi.puresurveyandroid.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.load.engine.Resource;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.util.ResourceHelper;

/**
 * Created by shadrack on 2017/05/15.
 */

public class PieChart extends View {
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_START_STROKE_COLOR = "start_stroke_color";
    private static final String INSTANCE_END_STROKE_COLOR = "end_stroke_color";
    private static final String INSTANCE_EMPTY_STROKE_COLOR = "empty_stroke_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width";
    private static final String INSTANCE_BACKGROUND_COLOR = "inner_background_color";
    private static final String INSTANCE_STARTING_DEGREE = "starting_degree";
    private static final String INSTANCE_INNER_DRAWABLE = "inner_drawable";
    private final float default_stroke_width;
//    private final int default_start_color = getResources().getColor(R.color.graphStart);
//    private final int default_end_color = getResources().getColor(R.color.graphEnd);
//    private final int default_empty_color = getResources().getColor(android.R.color.darker_gray);
    private final int default_inner_background_color = Color.TRANSPARENT;
    private final int default_max = 100;
    private final int default_startingDegree = 0;
    private final int min_size;
    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private Paint innerCirclePaint;
    private RectF finishedOuterRect = new RectF();
    private RectF unfinishedOuterRect = new RectF();
    private int attributeResourceId = 0;
    private float progress = 0;
    private int max;
    private int startStrokeColor;
    private int endStrokeColor;
    private int emptyStrokeColor;
    private int startingDegree;
    private float finishedStrokeWidth;
    private float unfinishedStrokeWidth;
    private int innerBackgroundColor;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        min_size = (int) dp2px(getResources(), 100);
        default_stroke_width = dp2px(getResources(), 20);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChart, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    protected void initPainters() {

        finishedPaint = new Paint();
//        finishedPaint.setShader(new LinearGradient(0, 0, 0, getHeight(), ResourceHelper.getColour(R.color.graphStart), ResourceHelper.getColour(R.color.graphEnd), Shader.TileMode.MIRROR));
        finishedPaint.setStyle(Paint.Style.STROKE);
        finishedPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        finishedPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        finishedPaint.setPathEffect(new CornerPathEffect(10));   // set the path effect when they join.
        finishedPaint.setAntiAlias(true);
        finishedPaint.setStrokeWidth(finishedStrokeWidth);

        unfinishedPaint = new Paint();
        unfinishedPaint.setColor(emptyStrokeColor);
        unfinishedPaint.setStyle(Paint.Style.STROKE);
        unfinishedPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        unfinishedPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        unfinishedPaint.setPathEffect(new CornerPathEffect(10));   // set the path effect when they join.
        unfinishedPaint.setAntiAlias(true);
        unfinishedPaint.setStrokeWidth(unfinishedStrokeWidth);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(innerBackgroundColor);
        innerCirclePaint.setAntiAlias(true);
    }

    protected void initByAttributes(TypedArray attributes) {
//        startStrokeColor = attributes.getColor(R.styleable.PieChart_pie_start_color, default_start_color);
//        endStrokeColor = attributes.getColor(R.styleable.PieChart_pie_end_color, default_end_color);
//        emptyStrokeColor = attributes.getColor(R.styleable.PieChart_pie_empty_color, default_empty_color);

        attributeResourceId = attributes.getResourceId(R.styleable.PieChart_pie_inner_drawable, 0);

        setMax(attributes.getInt(R.styleable.PieChart_pie_max, default_max));
        setProgress(attributes.getFloat(R.styleable.PieChart_pie_progress, 0));
        finishedStrokeWidth = attributes.getDimension(R.styleable.PieChart_pie_finished_stroke_width, default_stroke_width);
        unfinishedStrokeWidth = attributes.getDimension(R.styleable.PieChart_pie_unfinished_stroke_width, default_stroke_width);

        startingDegree = attributes.getInt(R.styleable.PieChart_pie_circle_starting_degree, default_startingDegree);
        innerBackgroundColor = attributes.getColor(R.styleable.PieChart_pie_background_color, default_inner_background_color);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getFinishedStrokeWidth() {
        return finishedStrokeWidth;
    }

    public void setFinishedStrokeWidth(float finishedStrokeWidth) {
        this.finishedStrokeWidth = finishedStrokeWidth;
        this.invalidate();
    }

    public float getUnfinishedStrokeWidth() {
        return unfinishedStrokeWidth;
    }

    public void setUnfinishedStrokeWidth(float unfinishedStrokeWidth) {
        this.unfinishedStrokeWidth = unfinishedStrokeWidth;
        this.invalidate();
    }

    private float getProgressAngle() {
        return getProgress() / (float) max * 360f;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress = getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public int getStartStrokeColor() {
        return startStrokeColor;
    }

    public void setStartStrokeColor(int startStrokeColor) {
        this.startStrokeColor = startStrokeColor;
        String hexColor = String.format("#%06X", (0xFFFFFF & startStrokeColor));
        this.invalidate();
    }

    public int getEndStrokeColor() {
        return endStrokeColor;
    }

    public void setEndStrokeColor(int endStrokeColor) {
        this.endStrokeColor = endStrokeColor;
        String hexColor = String.format("#%06X", (0xFFFFFF & endStrokeColor));
        this.invalidate();
    }

    public int getEmptyStrokeColor() {
        return emptyStrokeColor;
    }

    public void setEmptyStrokeColor(int emptyStrokeColor) {
        this.emptyStrokeColor = emptyStrokeColor;
        this.invalidate();
    }

    public int getInnerBackgroundColor() {
        return innerBackgroundColor;
    }

    public void setInnerBackgroundColor(int innerBackgroundColor) {
        this.innerBackgroundColor = innerBackgroundColor;
        this.invalidate();
    }

    public int getStartingDegree() {
        return startingDegree;
    }

    public void setStartingDegree(int startingDegree) {
        this.startingDegree = startingDegree;
        this.invalidate();
    }

    public int getAttributeResourceId() {
        return attributeResourceId;
    }

    public void setAttributeResourceId(int attributeResourceId) {
        this.attributeResourceId = attributeResourceId;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = min_size;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        finishedPaint.setShader((new LinearGradient(0, 0, 0, getHeight(), ResourceHelper.getColour(R.color.graphStart), ResourceHelper.getColour(R.color.graphEnd), Shader.TileMode.MIRROR)));

        float delta = Math.max(finishedStrokeWidth, unfinishedStrokeWidth);
        finishedOuterRect.set(delta, delta, getWidth() - delta, getHeight() - delta);
        unfinishedOuterRect.set(delta, delta, getWidth() - delta, getHeight() - delta);

        float innerCircleRadius = (getWidth() - Math.min(finishedStrokeWidth, unfinishedStrokeWidth) + Math.abs(finishedStrokeWidth - unfinishedStrokeWidth)) / 2f;
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, innerCircleRadius, innerCirclePaint);
        canvas.drawArc(unfinishedOuterRect, getStartingDegree() + getProgressAngle(), 360 - getProgressAngle(), false, unfinishedPaint);
        canvas.drawArc(finishedOuterRect, getStartingDegree(), getProgressAngle(), false, finishedPaint);

        if (attributeResourceId != 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), attributeResourceId);
            canvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2.0f, (getHeight() - bitmap.getHeight()) / 2.0f, null);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_START_STROKE_COLOR, getStartStrokeColor());
        bundle.putInt(INSTANCE_END_STROKE_COLOR, getEndStrokeColor());
        bundle.putInt(INSTANCE_EMPTY_STROKE_COLOR, getEmptyStrokeColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_STARTING_DEGREE, getStartingDegree());
        bundle.putFloat(INSTANCE_PROGRESS, getProgress());
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, getFinishedStrokeWidth());
        bundle.putFloat(INSTANCE_UNFINISHED_STROKE_WIDTH, getUnfinishedStrokeWidth());
        bundle.putInt(INSTANCE_BACKGROUND_COLOR, getInnerBackgroundColor());
        bundle.putInt(INSTANCE_INNER_DRAWABLE, getAttributeResourceId());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            startStrokeColor = bundle.getInt(INSTANCE_START_STROKE_COLOR);
            endStrokeColor = bundle.getInt(INSTANCE_END_STROKE_COLOR);
            emptyStrokeColor = bundle.getInt(INSTANCE_EMPTY_STROKE_COLOR);
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH);
            unfinishedStrokeWidth = bundle.getFloat(INSTANCE_UNFINISHED_STROKE_WIDTH);
            innerBackgroundColor = bundle.getInt(INSTANCE_BACKGROUND_COLOR);
            attributeResourceId = bundle.getInt(INSTANCE_INNER_DRAWABLE);
            initPainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setStartingDegree(bundle.getInt(INSTANCE_STARTING_DEGREE));
            setProgress(bundle.getFloat(INSTANCE_PROGRESS));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
