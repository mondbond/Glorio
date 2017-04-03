package exp.glorio.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import exp.glorio.R;

public class SexStatistic extends View {

    private final int DEFAULT_MAN_COLOR = Color.parseColor("#4f4dff");
    private final int DEFAULT_WOMAN_COLOR = Color.parseColor("#fe8c8c");
    private final int NO_DATA_COLOR = Color.parseColor("#D6D6D7");

    int contentWidth;
    int contentHeight;

    private float mExampleDimension = 0; // TODO: use a default from R.dimen..

    private Integer manColor;
    private Integer womanColor;

    private Paint paint;

    private Integer manPart;

    public SexStatistic(Context context) {
        super(context);
        init(null, 0);
    }

    public SexStatistic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SexStatistic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SexStatistic, defStyle, 0);

        }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        if(manPart != null) {
            drawWomanRec (canvas);
            drawManRec (canvas);
        }else {
            drawNoDataRec(canvas);
        }
    }

    private void drawNoDataRec(Canvas canvas) {
        paint.setColor(NO_DATA_COLOR);
        Rect womanRec = new Rect(getPaddingLeft(), getPaddingTop(),
                contentWidth, contentHeight);
        canvas.drawRect(womanRec, paint);
    }

    private void drawManRec(Canvas canvas) {
        if(manColor!= null) {
        paint.setColor(manColor);
    }else {
            paint.setColor(DEFAULT_MAN_COLOR);
    }
        Rect manRec = new Rect(getPaddingLeft(), getPaddingTop(),
                contentWidth/100*manPart, contentHeight);
        canvas.drawRect(manRec, paint);
}

    private void drawWomanRec(Canvas canvas) {
        if(womanColor!= null) {
            paint.setColor(womanColor);
        }else {
            paint.setColor(DEFAULT_WOMAN_COLOR);
        }
        Rect womanRec = new Rect(getPaddingLeft(), getPaddingTop(),
                contentWidth, contentHeight);

        canvas.drawRect(womanRec, paint);
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
    }

    public Integer getManPart() {
        return manPart;
    }

    public void setManPart(Integer manPart) {
        this.manPart = manPart;
        invalidate();
    }

    public Integer getManColor() {
        return manColor;
    }

    public void setManColor(Integer manColor) {
        this.manColor = manColor;
    }

    public Integer getWomanColor() {
        return womanColor;
    }

    public void setWomanColor(Integer womanColor) {
        this.womanColor = womanColor;
    }
}
