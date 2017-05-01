package com.project.ync.blooddonation.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.util.ScreenUtil;

/**
 * @author YNC
 */
public class CustomButton extends FrameLayout {
    private static final int POSITION_LEFT = 1;
    private static final int POSITION_RIGHT = 2;
    private static final int POSITION_TOP = 3;
    private static final int POSITION_BOTTOM = 4;
    private final Context mContext;

    // Background
    private int mDefaultBackgroundColor = Color.BLACK;
    private int mFocusBackgroundColor = 0;
    private int mDisabledBackgroundColor = Color.parseColor("#e0e0e0");
    private int mDisabledTextColor = Color.WHITE;
    private int mDisabledBorderColor = Color.parseColor("#dddfe2");
    private int mPadding = ScreenUtil.convertDpToPixel(getContext(), 11); //default padding 11dp

    // Text
    private int mDefaultTextColor = Color.WHITE;
    private String mText = null;
    private int mTextStyle = Typeface.NORMAL;
    private int mDefaultTextSize = ScreenUtil.convertDpToPixel(getContext(), 20); // default text size

    // Icon
    private Drawable mIconResource;
    private int mIconPosition = 1;
    private int mIconPadding = ScreenUtil.convertDpToPixel(getContext(), 6); // default drawable padding

    // Border
    private int mBorderColor = Color.TRANSPARENT;
    private int mBorderWidth = 0;
    private int mRadius = ScreenUtil.convertDpToPixel(getContext(), 50); //default corner radius
    private boolean mEnabled = true;

    private TextView mTextView;

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttributesArray(attrs);
        initializeButton();
    }

    private void initializeButton() {
        initializeButtonContainer();
        mTextView = setupTextView();
        this.removeAllViews();
        setupBackground();
        if (mTextView != null) {
            this.addView(mTextView);
        }
    }

    private TextView setupTextView() {
        if (mText == null) {
            mText = mContext.getString(R.string.app_name);
        }
        TextView textView = new TextView(mContext);
        textView.setText(mText);
        textView.setTextColor(mEnabled ? mDefaultTextColor : mDisabledTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ScreenUtil.convertPixelToDp(getContext(), mDefaultTextSize));
        textView.setCompoundDrawablePadding(mIconPadding);
        textView.setTypeface(null, mTextStyle);
        textView.setPadding(mPadding, mPadding, mPadding, mPadding);
        if (mIconResource != null) {
            switch (mIconPosition) {
                case POSITION_LEFT:
                    textView.setCompoundDrawablesWithIntrinsicBounds(mIconResource, null, null, null);
                    break;

                case POSITION_RIGHT:
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, mIconResource, null);
                    break;

                case POSITION_TOP:
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, mIconResource, null, null);
                    break;

                case POSITION_BOTTOM:
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, mIconResource);
                    break;
            }
        }
        FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private void initAttributesArray(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        mDefaultBackgroundColor = typedArray.getColor(R.styleable.CustomButton_btn_defaultColor, mDefaultBackgroundColor);
        mFocusBackgroundColor = typedArray.getColor(R.styleable.CustomButton_btn_focusColor, mFocusBackgroundColor);
        mDisabledBackgroundColor = typedArray.getColor(R.styleable.CustomButton_btn_disabledColor, mDisabledBackgroundColor);
        mPadding = (int) typedArray.getDimension(R.styleable.CustomButton_btn_padding, mPadding);
        mEnabled = typedArray.getBoolean(R.styleable.CustomButton_android_enabled, true);

        mDisabledTextColor = typedArray.getColor(R.styleable.CustomButton_btn_disabledTextColor, mDisabledTextColor);
        mDisabledBorderColor = typedArray.getColor(R.styleable.CustomButton_btn_disabledBorderColor, mDisabledBorderColor);
        mDefaultTextColor = typedArray.getColor(R.styleable.CustomButton_btn_textColor, mDefaultTextColor);
        mDefaultTextSize = (int) typedArray.getDimension(R.styleable.CustomButton_android_textSize, mDefaultTextSize);

        mBorderColor = typedArray.getColor(R.styleable.CustomButton_btn_borderColor, mBorderColor);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.CustomButton_btn_borderWidth, mBorderWidth);
        mRadius = (int) typedArray.getDimension(R.styleable.CustomButton_btn_radius, mRadius);

        mIconPadding = (int) typedArray.getDimension(R.styleable.CustomButton_btn_iconPadding, mIconPadding);

        mTextStyle = typedArray.getInt(R.styleable.CustomButton_android_textStyle, mTextStyle);
        mText = typedArray.getString(R.styleable.CustomButton_btn_text);
        if (mText == null) {
            mText = typedArray.getString(R.styleable.CustomButton_android_text);
        }
        mIconPosition = typedArray.getInt(R.styleable.CustomButton_btn_iconPosition, mIconPosition);

        try {
            mIconResource = typedArray.getDrawable(R.styleable.CustomButton_btn_iconResource);
        } catch (Exception e) {
            mIconResource = null;
        }
        typedArray.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable, Drawable disabledDrawable) {
        if (!mEnabled) {
            return disabledDrawable;
        } else {
            return new RippleDrawable(ColorStateList.valueOf(mFocusBackgroundColor), defaultDrawable, focusDrawable);
        }
    }

    private void setupBackground() {
        // Default drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setCornerRadius(mRadius);
        defaultDrawable.setColor(mDefaultBackgroundColor);

        // Focus Drawable
        GradientDrawable focusDrawable = new GradientDrawable();
        focusDrawable.setCornerRadius(mRadius);
        if (mFocusBackgroundColor == 0) {
            mFocusBackgroundColor = manipulateColor(mDefaultBackgroundColor);
        }
        focusDrawable.setColor(mFocusBackgroundColor);

        // Disabled Drawable
        GradientDrawable disabledDrawable = new GradientDrawable();
        disabledDrawable.setCornerRadius(mRadius);
        disabledDrawable.setColor(mDisabledBackgroundColor);
        disabledDrawable.setStroke(mBorderWidth, mDisabledBorderColor);

        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor);
        }

        // Handle disable border color
        if (!mEnabled) {
            defaultDrawable.setStroke(mBorderWidth, mDisabledBorderColor);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setBackground(getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable));
        } else {
            StateListDrawable states = new StateListDrawable();

            // Focus/Pressed Drawable
            GradientDrawable pressDrawable = new GradientDrawable();
            pressDrawable.setCornerRadius(mRadius);
            pressDrawable.setColor(mFocusBackgroundColor);

            // Handle Button Border
            if (mBorderColor != 0) {
                pressDrawable.setStroke(mBorderWidth, mBorderColor);
            }

            if (!mEnabled) {
                pressDrawable.setStroke(mBorderWidth, mDisabledBorderColor);
            }

            if (mFocusBackgroundColor != 0) {
                states.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
                states.addState(new int[]{android.R.attr.state_focused}, pressDrawable);
                states.addState(new int[]{-android.R.attr.state_enabled}, disabledDrawable);
            }
            states.addState(new int[]{}, defaultDrawable);
            this.setBackground(states);
        }
    }

    public int manipulateColor(int color) {
        float factor = 0.7f;
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
    }

    private void initializeButtonContainer() {
        if (this.getLayoutParams() == null) {
            LayoutParams containerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(containerParams);
        }
        setClickable(true);
        setFocusable(true);
    }

    public void setText(String text) {
        mText = text;
        if (mTextView == null) {
            initializeButton();
        } else {
            mTextView.setText(text);
        }
    }

    public void setTextColor(int color) {
        this.mDefaultTextColor = color;
        if (mTextView == null) {
            initializeButton();
        } else {
            mTextView.setTextColor(color);
        }
    }

    public void setBackgroundColor(int color) {
        this.mDefaultBackgroundColor = color;
        initializeButton();
    }

    public void setFocusBackgroundColor(int color) {
        this.mFocusBackgroundColor = color;
        initializeButton();
    }

    public void setDisableBackgroundColor(int color) {
        this.mDisabledBackgroundColor = color;
        initializeButton();
    }

    public void setDisableTextColor(int color) {
        this.mDisabledTextColor = color;
        if (mTextView == null) {
            initializeButton();
        } else if (!mEnabled) {
            mTextView.setTextColor(color);
        }
    }

    public void setDisableBorderColor(int color) {
        this.mDisabledBorderColor = color;
        initializeButton();
    }

    public void setTextSize(int textSize) {
        this.mDefaultTextSize = ScreenUtil.convertDpToPixel(getContext(), textSize);
        if (mTextView != null) {
            mTextView.setTextSize(textSize);
        }
    }

    public void setIconPadding(int padding) {
        this.mIconPadding = padding;
        initializeButton();
    }

    public void setIconResource(int drawable) {
        this.mIconResource = ContextCompat.getDrawable(mContext, drawable);
        initializeButton();
    }

    public void setIconResource(Drawable drawable) {
        this.mIconResource = drawable;
        initializeButton();
    }

    public void setIconPosition(int position) {
        if (position > 0 && position < 5) {
            mIconPosition = position;
        } else {
            mIconPosition = POSITION_LEFT;
        }
        initializeButton();
    }

    public void setBorderColor(int color) {
        this.mBorderColor = color;
        initializeButton();
    }

    public void setBorderWidth(int width) {
        this.mBorderWidth = width;
        initializeButton();
    }

    public void setPadding(int padding) {
        this.mPadding = padding;
        initializeButton();
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        initializeButton();
    }

    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        this.mEnabled = value;
        initializeButton();
    }

    public CharSequence getText() {
        return mTextView != null ? mTextView.getText() : "";
    }

    public TextView getTextViewObject() {
        return mTextView;
    }
}

