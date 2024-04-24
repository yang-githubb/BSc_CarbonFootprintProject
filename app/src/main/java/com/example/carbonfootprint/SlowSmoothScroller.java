package com.example.carbonfootprint;
import android.content.Context;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearSmoothScroller;

public class SlowSmoothScroller extends LinearSmoothScroller {

    public SlowSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 8000f / displayMetrics.densityDpi;
    }
}
