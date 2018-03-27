package com.newler.vrvideoplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 *
 * @author
 * @time 2018/3/14
 */
public class ScrollNumberView extends View {

    private int mDeltaNum;
    private int mCurNum;
    private int mNextNum;
    private int mTargetNum;
    private Context mContext;

    private float mOffset;
    private Paint mPaint;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private boolean isEquals = false;
    private int mTextCenterX;
    private int mTextHeight;
    private Rect mTextBounds = new Rect();
    private float mTextSize = sp2px(130);
    private int mTextColor = 0xFF000000;

    private long scrollTime = 10;

    public ScrollNumberView(Context context) {
        this(context, null);
    }

    public ScrollNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mTextSize);
        mPaint.setFakeBoldText(true);
        mPaint.setColor(mTextColor);

        measureTextHeight();

//        setNumber(0, 6, 1000);

    }

    public void setEquals() {
        isEquals = true;
    }

    public void setNumber(final int from, final int to, long delay) {
        this.scrollTime = delay;
        postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isEquals) {
                    invalidate();
                } else {
                    setFromNumber(from);
                    setTargetNumber(to);
                    mDeltaNum = to - from;
                }
            }
        },delay);
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        mPaint.setTextSize(mTextSize);
        measureTextHeight();
        requestLayout();
        invalidate();
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mPaint.setColor(mTextColor);
        invalidate();
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    private void measureTextHeight() {
        mPaint.getTextBounds(mCurNum + "", 0, 1, mTextBounds);
        mTextHeight = mTextBounds.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mTextCenterX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mPaint.getTextBounds("0", 0, 1, mTextBounds);
                result = mTextBounds.height();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result + getPaddingTop() + getPaddingBottom() + dp2px(10);
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mPaint.getTextBounds("0", 0, 1, mTextBounds);
                result = mTextBounds.width();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result + getPaddingLeft() + getPaddingRight() +dp2px(1);
    }

    public   void setCurNum(int num) {
        this.mCurNum = num;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isEquals) {
            this.drawSelf(canvas);
            return;
        }
        if (mCurNum != mTargetNum) {

            postDelayed(mScrollRunnable, scrollTime);
            if (mOffset <= -1) {
                mOffset = 0;
                calNum(mCurNum + 1);
            }
        }

        canvas.translate(0, mOffset * getMeasuredHeight());
        drawSelf(canvas);
        drawNext(canvas);
//        canvas.restore();
    }

    private void setFromNumber(int number) {
        if (number < 0 || number > 9)
            throw new RuntimeException("invalidate number , should in [0,9]");
        calNum(number);
        mOffset = 0;
        invalidate();
    }


    private void calNum(int number) {
        number = number == -1 ? 9 : number;
        number = number == 10 ? 0 : number;
        mCurNum = number;
        mNextNum = number + 1 == 10 ? 0 : number + 1;
    }

    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            float x = (float) (1 - 1.0 * (mTargetNum - mCurNum) / mDeltaNum);
            mOffset -= 0.15f * (1 - mInterpolator.getInterpolation(x) + 0.1);
            invalidate();
        }
    };

    private void drawNext(Canvas canvas) {
        int y = getMeasuredHeight() * 3 / 2;
        canvas.drawText(mNextNum + "", mTextCenterX, y + mTextHeight / 2,
                mPaint);
    }

    private void drawSelf(Canvas canvas) {
        int y = getMeasuredHeight() / 2;
        canvas.drawText(mCurNum + "", mTextCenterX, y + mTextHeight / 2, mPaint);
    }


    private void setTargetNumber(int nextNum) {
        this.mTargetNum = nextNum;
        invalidate();
    }

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    private int sp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getResources().getDisplayMetrics());
    }


}
