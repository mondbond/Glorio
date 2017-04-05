package exp.glorio.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import exp.glorio.R;

public class SexStatisticView extends View {

    private final int DEFAULT_MAN_COLOR = Color.parseColor("#4f4dff");
    private final int DEFAULT_WOMAN_COLOR = Color.parseColor("#fe8c8c");
    private final int NO_DATA_COLOR = Color.parseColor("#D6D6D7");

    int contentWidth;
    int contentHeight;

    private Integer manColor;
    private Integer womanColor;

    private Paint paint = new Paint();

    private Integer manPart;

    public SexStatisticView(Context context) {
        super(context);
        init(null, 0);
    }

    public SexStatisticView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SexStatisticView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SexStatisticView, defStyle, 0);

        }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
