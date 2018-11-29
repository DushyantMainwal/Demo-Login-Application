package com.dushyant.loginapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class CustomDottedProgress extends View {

    private int actualDotRadius = 12;
    private int largeDotRadius = 24;
    private int totalDotCount = 3;
    private int currentDotPosition = 0;
    private Paint paint;
    private BounceAnimation bounceAnimation;

    public CustomDottedProgress(Context context) {
        super(context);
    }

    public CustomDottedProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDottedProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        //set the color for the dot that you want to draw
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        //function to create dot
        createDot(canvas, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        //calculate the view width
//        width = (30*9);
//        height = (largeDotRadius*4);
//
//        //MUST CALL THIS
//        setMeasuredDimension(width, height);
    }


    private void createDot(Canvas canvas, Paint paint) {
        for (int i = 0; i < totalDotCount; i++) {
            canvas.drawCircle(100 + (i * 60), canvas.getHeight() / 2, i == currentDotPosition ? largeDotRadius : actualDotRadius, paint);
        }

//        System.out.println("Width: " + canvas.getWidth());
//        System.out.println("Hewight: " + canvas.getWidth());
//        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, actualDotRadius, paint);
    }

    public void startAnimation() {
        bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(200);
//        bounceAnimation.setRepeatCount(25);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                System.out.println("Animation Happened");
                currentDotPosition++;
                //when mDotPosition == mDotAmount , then start again applying animation from 0th positon , i.e  mDotPosition = 0;
                if (currentDotPosition == totalDotCount) {
                    currentDotPosition = 0;
                }

            }
        });
        startAnimation(bounceAnimation);
    }

    public void stopAnimation() {
        if (bounceAnimation == null) {
            return;
        }
        bounceAnimation.cancel();
    }


    private class BounceAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //call invalidate to redraw your view againg.
            invalidate();
        }
    }
}
