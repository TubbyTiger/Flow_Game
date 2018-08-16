package edu.msu.kyekevin.examkyekevin;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class FlowView extends View {

    public Flow getFlow(){
        return flow;
    }

    public FlowView(Context context) {
        super(context);
        init();
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return flow.onTouchEvent(this, event);
    }

    private Flow flow;
    private void init() {

        flow = new Flow(getContext());

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        flow.draw(canvas);
    }

}
