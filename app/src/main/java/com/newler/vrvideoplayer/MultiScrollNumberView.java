package com.newler.vrvideoplayer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字滚动控件
 * @author
 * @time 2018/3/14
 */
public class MultiScrollNumberView extends LinearLayout {
    private Context mContext;
    private List<Integer> mTargetNumbers = new ArrayList<>();
    private List<Integer> mPrimaryNumbers = new ArrayList<>();
    private List<ScrollNumberView> mScrollNumbers = new ArrayList<>();
    private float mTextSize = 130;
    private int mTextColor = Color.BLACK;
    private int beforeNum;
    private boolean mInit = true;
    private int numLength = 0;

    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private String mFontFileName;

    public MultiScrollNumberView(Context context) {
        this(context, null);
    }

    public MultiScrollNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiScrollNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MultiScrollNumberView);

        float numberSize = typedArray.getDimension(R.styleable.MultiScrollNumberView_number_size, 130);
        int color = typedArray.getColor(R.styleable.MultiScrollNumberView_number_color, Color.BLACK);
        setTextSize(numberSize);
        setTextColor(color);
        typedArray.recycle();

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void setNumber(int val) {
        resetView();
        if (mInit) {
            this.beforeNum = val;
            setNumber(beforeNum, val);
            return;
        }

        setNumber(beforeNum, val);
        this.beforeNum = val;
    }

    private void resetView() {
        mTargetNumbers.clear();
        mScrollNumbers.clear();
        mPrimaryNumbers.clear();
        removeAllViews();
    }


    private void setNumber(int from, int to) {
        setTargetNumbers(to);
        setPrimaryNumbers(from);
        if (mInit && from==0 && to == 0) {
            fromNumAndToNumIsZero();
            mInit = false;
            return;
        }
        if (from >= to && !mInit) {
            if (to == 0) {
                fromNumAndToNumIsZero();
                return;
            }
            fromNumEqualsToNum(from, to);
            return;
        }
        mInit = false;
        fromNumNotEqualsToNum();
    }



    private void setTargetNumbers(int toNum) {
        numLength = 0;

        while (toNum > 0) {
            int i = toNum % 10;
            mTargetNumbers.add(i);
            toNum /= 10;
            numLength++;
        }
    }

    private void setPrimaryNumbers(int formNum) {
        while (numLength > 0) {
            int i = formNum % 10;
            mPrimaryNumbers.add(i);
            formNum /= 10;
            numLength--;
        }
    }

    /**
     * 之前的数据和目标数据一样，初始化数据例外，如果一样就不滚动
     */
    private void fromNumEqualsToNum(int formNum, int toNum) {
        for (int i = mTargetNumbers.size() - 1; i >= 0; i--) {
            ScrollNumberView scrollNumber = new ScrollNumberView(mContext);
            scrollNumber.setTextColor(mTextColor);
            scrollNumber.setTextSize(mTextSize);
            scrollNumber.setEquals();
            scrollNumber.setCurNum(mTargetNumbers.get(i));
            scrollNumber.setNumber(mTargetNumbers.get(i), mTargetNumbers.get(i), 1);
            mScrollNumbers.add(scrollNumber);
            addView(scrollNumber);
        }
    }

    /**
     * 之前的数据和目标数据不一样，就滚动数据
     */
    private void fromNumNotEqualsToNum() {
        for (int i = mTargetNumbers.size() - 1; i >= 0; i--) {
            ScrollNumberView scrollNumber = new ScrollNumberView(mContext);
            scrollNumber.setTextColor(mTextColor);
            scrollNumber.setTextSize(mTextSize);
            Log.e("fromNumNotEqualsToNum", (i+1)  * 10 + "ss");
            scrollNumber.setNumber(mPrimaryNumbers.get(i), mTargetNumbers.get(i), (i+1) * 20);
            mScrollNumbers.add(scrollNumber);
            addView(scrollNumber);
        }
    }

    private void fromNumAndToNumIsZero() {
        ScrollNumberView scrollNumber = new ScrollNumberView(mContext);
        scrollNumber.setTextColor(mTextColor);
        scrollNumber.setTextSize(mTextSize);
        scrollNumber.setEquals();
        scrollNumber.setCurNum(0);
        scrollNumber.setNumber(0, 0,0);
        mScrollNumbers.add(scrollNumber);
        addView(scrollNumber);
    }

    public void setTextColor(int color) {
        mTextColor = color;
        for (int i = mScrollNumbers.size() - 1; i >= 0; i--) {
            ScrollNumberView scrollNumber = mScrollNumbers.get(i);
            scrollNumber.setTextColor(color);
        }
    }

    public void setTextSize(float textSize) {
        if (textSize <= 0) throw new IllegalArgumentException("text size must > 0!");
        mTextSize = textSize;
        for (ScrollNumberView s : mScrollNumbers) {
            s.setTextSize(textSize);
        }
    }

    public void setInterpolator(Interpolator interpolator) {
        if (interpolator == null)
            throw new IllegalArgumentException("interpolator couldn't be null");
        mInterpolator = interpolator;
        for (ScrollNumberView s : mScrollNumbers) {
            s.setInterpolator(interpolator);
        }
    }
}
