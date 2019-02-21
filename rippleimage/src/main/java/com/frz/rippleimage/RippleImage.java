package com.frz.rippleimage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * author: siney
 * Date: 2019/2/21
 * description:
 */
public class RippleImage extends AppCompatImageView {

    public RippleImage(Context context) {
        super(context);
    }

    public RippleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RippleImage, defStyleAttr, 0);
        int textSize = a.getDimensionPixelSize(R.styleable.RippleImage_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        int[] ints = Utils.hexArgb2Decimal("#FFFFFFFF");
        int textColor = a.getColor(R.styleable.RippleImage_textColor, Color.argb(ints[0], ints[1], ints[2], ints[3]));
        boolean isShowText = a.getBoolean(R.styleable.RippleImage_isShowText, true);
        ints = Utils.hexArgb2Decimal("#99444444");
        maskColor = a.getColor(R.styleable.RippleImage_maskColor, Color.argb(ints[0], ints[1], ints[2], ints[3]));
        duration = a.getInt(R.styleable.RippleImage_duration, 1000);
        maskPaint = new Paint();
        textPaint = new Paint();
        maskPaint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        maskPaint.setDither(true);
        textPaint.setDither(true);

        maskPaint.setColor(maskColor);
        maskPaint.setStyle(Paint.Style.STROKE);
        if(isShowText){
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
        }
        Log.e("TAG", isShowText+"??");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getWidth();
        h = getHeight();
        double ww = getWidth() / 2.0;
        double hh = getHeight() / 2.0;
        maxRadius = (float) Math.sqrt(ww * ww + hh * hh);
    }

    private Paint maskPaint, textPaint;
    private float maxRadius, currentRadius;
    private int type, duration = 1000, w, h, maskColor;
    private boolean isRunningAnim;
    private ValueAnimator anim;
    private RectF oval = new RectF();
    private String text;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == 1){
            canvas.drawColor(maskColor);
            if(text != null){
                Paint.FontMetrics fm = textPaint.getFontMetrics();
                int x = (int) ((w - textPaint.measureText(text)) / 2);
                int y = (int) (h / 2 + (fm.descent - fm.ascent) / 2 - fm.descent);
                canvas.drawText(text, x, y, textPaint);
            }
        }else if(type == 2){
            for(int i = (int) currentRadius; i < maxRadius; i++){
                oval.left = w / 2 - i;
                oval.top = h / 2 - i;
                oval.right = w / 2 + i;
                oval.bottom = h / 2 + i;
                canvas.drawArc(oval, 0, 360, false, maskPaint);
            }
            Log.e("TAG", "..");
        }
    }

    public void setMask(){
        if(!isRunningAnim){
            type = 1;
            text = null;
            postInvalidate();
        }
    }

    public void setMask(String text){
        if(!isRunningAnim){
            type = 1;
            this.text = text;
            postInvalidate();
        }
    }

    public void animation(){
        if(!isRunningAnim){
            isRunningAnim = true;
            anim = ValueAnimator.ofFloat(0, 1);
            anim.setDuration(duration);
            type = 2;
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float v = (float) animation.getAnimatedValue();
                    currentRadius = v * maxRadius;
                    postInvalidate();
                }
            });
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isRunningAnim = false;
                    type = -1;
                    postInvalidate();
                }
            });
            anim.start();
        }
    }

    public String getText() {
        return text;
    }

    public boolean isRunningAnim() {
        return isRunningAnim;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
