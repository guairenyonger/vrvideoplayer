package com.newler.vrvideoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DemoView extends android.support.v7.widget.AppCompatTextView {
    public DemoView(Context context) {
        super(context);
    }

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}
