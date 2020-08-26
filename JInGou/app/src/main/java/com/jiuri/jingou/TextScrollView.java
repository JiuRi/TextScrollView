package com.jiuri.jingou;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;

public class TextScrollView extends NestedScrollView {
    private int textLineNum = 2;
    private Context mContext;
    private LinearLayout mLinearLayout;
    private TextView mTextView0;
    private int textViewHeight;
    private int textColor = Color.parseColor("#1a1a1a");
    private int textSize = 13;
    private int textBackGround = Color.parseColor("#ff0aff");
    ;
    private int[] textColorArrays;

    public TextScrollView(@NonNull Context context) {
        this(context, null);
    }

    public TextScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setGravity(Gravity.CENTER);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(mLinearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewHeight = dp2px(mContext, 100);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setText(String text) {


        if (mLinearLayout.getChildCount() > textLineNum) {
            startAnimal(text);
        } else {

            if (mTextView0 != null) {
                startAnimal(text);
            }
            else {
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setLines(1);
                textView.setShadowLayer(18f, 0, 0, Color.parseColor("#000000")); //设置阴影
                textView.setText(text);
                if (textColorArrays != null && textColorArrays.length > 0) {
                    textView.setTextColor(textColorArrays[mLinearLayout.getChildCount()]);
                } else {
                    textView.setTextColor(textColor);
                }

                textView.setBackgroundColor(textBackGround);
                textView.setTextSize(textSize);
                mLinearLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, textViewHeight);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 5, 0, 5);

                if (mLinearLayout.getChildCount() > textLineNum) {
                    startAnimal(text);
                }
            }


        }


    }

    private void startAnimal(String text) {

        if (mTextView0 != null) {
            mTextView0.setText(text);
            mLinearLayout.addView(mTextView0);
        }


        final TextView textView2 = (TextView) mLinearLayout.getChildAt(2);
        final TextView textView1 = (TextView) mLinearLayout.getChildAt(1);
        final TextView textView0 = (TextView) mLinearLayout.getChildAt(0);

        textView2.setAlpha(0f);
        textView1.setAlpha(1f);
        textView0.setAlpha(1f);


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1000);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Object animatedValue = valueAnimator.getAnimatedValue();

                float value = (float) animatedValue;
                textView0.setAlpha(1 - value / 1000);
                textView1.setAlpha(1f);
                textView2.setAlpha(value / 1500);


                scrollTo(0, (int) ((10 + textViewHeight) * (value / 1000)));

                if (value == 1000) {

                    mTextView0 = (TextView) mLinearLayout.getChildAt(0);
                    mTextView0.setAlpha(1f);
                    textView2.setAlpha(1f);
                    mLinearLayout.removeViewAt(0);
                    Log.e("oooo", "onAnimationUpdate: ___________________" + mLinearLayout.getChildCount());


                }

            }

        });
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mLinearLayout.getChildCount() < textLineNum) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((10 + textViewHeight) * mLinearLayout.getChildCount(), MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((10 + textViewHeight) * textLineNum, MeasureSpec.EXACTLY));
        }


    }

    public int getTextLineNum() {
        return textLineNum;
    }

    public void setTextColorArrays(int[] textColorArrays) {
        this.textColorArrays = textColorArrays;
    }

    public void setTextViewHeight(int heightDp) {
        textViewHeight = dp2px(mContext, 50);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


    public void setTextBackGround(int textBackGround) {
        this.textBackGround = textBackGround;
    }

    public void setTextLineNum(int textLineNum) {
        this.textLineNum = textLineNum;
    }

    public void setTextSize(int textSizeSp) {
        this.textSize = sp2px(mContext, textSizeSp);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
