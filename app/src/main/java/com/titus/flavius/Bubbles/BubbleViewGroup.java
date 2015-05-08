package com.titus.flavius.Bubbles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.res.TypedArray;

import com.titus.flavius.R;

import java.util.ArrayList;

public class BubbleViewGroup extends ViewGroup {
    private ArrayList<Bubble> allBubbles = new ArrayList<Bubble>();


    public BubbleViewGroup(Context context) {
        super(context);
        init();
    }

    public BubbleViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();

        TypedArray infoArr = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.BubbleViewGroup,
                0, 0
        );

        try {
            boolean bl = infoArr.getBoolean(R.styleable.BubbleViewGroup_bol, false);
            float flt = infoArr.getFloat(R.styleable.BubbleViewGroup_flt, 0.0f);
        } finally {
            infoArr.recycle();
        }
    }

    private void init() {
        if (!this.isInEditMode())
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void addBubble(Context context) {
        Bubble tempBubble = new Bubble(context,400,400,300);
        addView(tempBubble);
        allBubbles.add(tempBubble);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        for(Bubble bub : allBubbles)
            bub.layout(0,0,w,h);
    }
}
