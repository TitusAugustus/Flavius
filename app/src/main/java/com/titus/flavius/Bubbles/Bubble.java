package com.titus.flavius.Bubbles;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;

import com.titus.flavius.GetContactsService;

import java.util.Random;


public class Bubble extends View {
    private RectF bounds = new RectF();
    float cx = 0, cy = 0;
    float radius = 1;

    Paint paint;
    Shader shader;

    private Scroller scroller;
    private ValueAnimator scrollAnimator;

    GestureDetector mDetector;


    public Bubble(Context context, float centerX, float centerY, float newRadius) {
        super(context);

        cx = centerX;
        cy = centerY;
        radius = newRadius;

        shader = new RadialGradient(cx,cy,radius,0xff171EEB,0xff35D6E8, Shader.TileMode.CLAMP);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);

        mDetector = new GestureDetector(Bubble.this.getContext(), new GestureListener());
        mDetector.setIsLongpressEnabled(false);

        scroller = new Scroller(getContext(), null, true);
        scroller.startScroll((int)cx, (int)cy, 0, 0);
        scroller.setFriction(.001f);
        scrollAnimator = ValueAnimator.ofFloat(0, 1);
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                moveAnimation();
            }
        });
    }

    private boolean isPointWithin(float x, float y) {
        return dist(x,y) < radius;
    }
    private double dist(float x, float y) {
        float distX = x - cx, distY = y - cy;
        return Math.sqrt((distX*distX) + (distY*distY));
    }

    private boolean isOutOfBoundsLeft(){
        return (cx - radius < bounds.left);
    }
    private boolean isOutOfBoundsRight(){
        return (cx + radius > bounds.right);
    }
    private boolean isOutOfBoundsX(){
        return isOutOfBoundsLeft() || isOutOfBoundsRight();
    }
    private boolean isOutOfBoundsTop(){
        return (cy - radius < bounds.top);
    }
    private boolean isOutOfBoundsBottom(){
        return (cy + radius > bounds.bottom);
    }
    private boolean isOutOfBoundsY(){
        return isOutOfBoundsTop() || isOutOfBoundsBottom();
    }
    private void putInBounds(){
        if(isOutOfBoundsLeft()) cx = bounds.left + radius;
        if(isOutOfBoundsRight()) cx = bounds.right - radius;
        if(isOutOfBoundsTop()) cy = bounds.top + radius;
        if(isOutOfBoundsBottom()) cy = bounds.bottom - radius;
    }

    private void redraw(){
        shader = new RadialGradient(cx,cy,radius,0xff171EEB,0xff35D6E8, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(cx, cy, radius, paint);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        bounds = new RectF(0,0,w,h);
        Random rand = new Random();
        cx = (float)(w - 2*radius) * rand.nextFloat() + radius;
        cy = (float)(h - 2*radius) * rand.nextFloat() + radius;
        redraw();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            cx -= distanceX;
            cy -= distanceY;
            putInBounds();

            redraw();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(!isPointWithin(e2.getX(), e2.getY())) return false;
            scroller.fling((int) cx, (int) cy, (int) velocityX/2, (int) velocityY/2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            scrollAnimator.setDuration(scroller.getDuration());
            scrollAnimator.start();

            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if(!isPointWithin(e.getX(), e.getY())) return false;
            doneAnimating();
            return true;
        }
    }

    private void moveAnimation() {
        if (!scroller.isFinished()){
            scroller.computeScrollOffset();
            cx = scroller.getCurrX();
            cy = scroller.getCurrY();

            if(isOutOfBoundsX() || isOutOfBoundsY()) {
                int finalX = scroller.getFinalX();
                int finalY = scroller.getFinalY();
                double newFinalX = 0, newFinalY = 0;
                double velocity = scroller.getCurrVelocity();

                if (isOutOfBoundsX()) {
                    putInBounds();
                    newFinalX = cx - finalX;
                } else
                    newFinalX = finalX - cx;
                if (isOutOfBoundsY()) {
                    putInBounds();
                    newFinalY = cy - finalY;
                }
                else
                    newFinalY = finalY - cy;

                double hyp = Math.sqrt(newFinalX * newFinalX + newFinalY * newFinalY);
                double velX = (newFinalX / hyp) * velocity;
                double velY = (newFinalY / hyp) * velocity;

                scroller.forceFinished(true);
                scroller.fling((int) cx, (int) cy, (int)velX, (int) velY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                scrollAnimator.setDuration(scroller.getDuration());
                scrollAnimator.start();
            }

            redraw();
        }
        else {
            doneAnimating();
        }
    }
    private void doneAnimating(){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        scrollAnimator.cancel();
        scroller.forceFinished(true);
    }
}
