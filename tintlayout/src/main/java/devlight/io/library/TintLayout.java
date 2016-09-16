/*
 * Copyright (C) 2015 Basil Miller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package devlight.io.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by GIGAMOLE on 21.06.2015.
 */
public class TintLayout extends FrameLayout {

    private final static int FLAGS =
            Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG;

    private final static float DEFAULT_ANGLE = 45.0F;
    private final static int DEFAULT_COLOR = Color.LTGRAY;

    // Max and min end angle
    private final static float MAX_ANGLE = 360.0F;
    private final static float MIN_ANGLE = 0.0F;

    private final RectF mBounds = new RectF();
    private final RectF mChildBounds = new RectF();

    private Paint mColorPaint = new Paint(FLAGS);

    private final Canvas mCanvas = new Canvas();
    private Bitmap mBitmap;

    private final Canvas mTintCanvas = new Canvas();
    private Bitmap mTintBitmap;
    private Bitmap mChildBitmap;

    private float mLeftCounter;
    private float mTopCounter;

    private float mOffsetX;
    private float mOffsetY;
    private float mRadius;

    private float mAngle;
    private int[] mColors;
    private int mColor;

    private Shader mTintGradient;

    public TintLayout(Context context) {
        this(context, null);
    }

    public TintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, null);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TintLayout);
        try {
            setAngle(typedArray.getFloat(R.styleable.TintLayout_tl_angle, DEFAULT_ANGLE));
            setColor(typedArray.getColor(R.styleable.TintLayout_tl_color, DEFAULT_COLOR));

            final int colorsId = typedArray.getResourceId(R.styleable.TintLayout_tl_colors, 0);
            setColors(colorsId == 0 ? null : getResources().getIntArray(colorsId));
        } finally {
            typedArray.recycle();
        }
    }

    public double getAngle() {
        return mAngle;
    }

    public void setAngle(@FloatRange(from = MIN_ANGLE, to = MAX_ANGLE) float angle) {
        mAngle = Math.max(MIN_ANGLE, Math.min(angle, MAX_ANGLE));
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(final int color) {
        mColor = color;
        mColorPaint.setColor(mColor);
    }

    public int[] getColors() {
        return mColors;
    }

    public void setColors(int[] colors) {
        mColors = colors;
        requestLayout();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBounds.set(0.0F, 0.0F, getMeasuredWidth(), getMeasuredHeight());

        mBitmap = Bitmap.createBitmap(
                (int) mBounds.width(), (int) mBounds.height(), Bitmap.Config.ARGB_8888
        );
        mCanvas.setBitmap(mBitmap);

        mTintBitmap = Bitmap.createBitmap(
                (int) mBounds.width(), (int) mBounds.height(), Bitmap.Config.ARGB_8888
        );
        mCanvas.setBitmap(mTintBitmap);

        mRadius = mBounds.width() > mBounds.height() ? mBounds.centerX() : mBounds.centerY();
        mTintGradient = new RadialGradient(
                mBounds.centerX(), mBounds.centerY(), mRadius, mColors, null, Shader.TileMode.CLAMP
        );
        mColorPaint.setShader(mTintGradient);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Check for availability
//        if (mTintBitmap == null || mChildBitmap == null) return;

//        mCanvas.drawRect(mBounds, mColorPaint);
//        mCanvas.drawBitmap(mTintBitmap, 0.0F, 0.0F, mTintPaint);

//        mTintCanvas.drawRect(mBounds, );

        canvas.drawBitmap(mTintBitmap, 0.0F, 0.0F, null);

//        if (this.mColors.length == 1) {
//            this.mCanvas.drawColor(this.mColors[0], PorterDuff.Mode.SRC_IN);
//        } else {
//
//
//            this.mColorPaint.setShader(shader);
//            this.mColorPaint.setXfermode(this.porterDuffXfermode);
//
//            this.mCanvas.drawRect(new Rect(
//                            0,
//                            0,
//                            this.width,
//                            this.height),
//                    this.mColorPaint
//            );
//
//            this.mColorPaint.setShader(null);
//            this.mColorPaint.setXfermode(null);
//        }
//
//        canvas.drawBitmap(this.mBitmap, this.matrix, null);
    }

    //
    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (getChildCount() > 1)
            throw new IllegalArgumentException(getResources().getString(R.string.child_exception));

        final View child = getChildAt(0);
        ((LayoutParams) child.getLayoutParams()).gravity = Gravity.CENTER;

        child.setDrawingCacheEnabled(true);
        child.buildDrawingCache();

        final Bitmap drawingCache = child.getDrawingCache();
        if (drawingCache == null) return;

        // Obtain child screenshot
        mChildBitmap = Bitmap.createBitmap(drawingCache);
        drawingCache.recycle();

        mChildBounds.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        child.setDrawingCacheEnabled(false);

        invalidateTint();
    }

    public void invalidateTint() {
//        mTintCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

        final float x = (float) (mChildBounds.left + Math.cos(mAngle * Math.PI / 180.0F) * mRadius);
        final float y = (float) (mChildBounds.top + Math.sin(mAngle * Math.PI / 180.0F) * mRadius);

        mLeftCounter = mChildBounds.left;
        mTopCounter = mChildBounds.top;

        mOffsetX = (x - mChildBounds.left) / mRadius;
        mOffsetY = (y - mChildBounds.top) / mRadius;

        if (mAngle >= 0.0F && mAngle <= 90.0F) {
            while (mLeftCounter < mBounds.width() && mTopCounter < mBounds.height())
                drawTintBitmap();
        } else if (mAngle >= 90.0F && mAngle <= 180.0F) {
            while (mLeftCounter > -mChildBounds.width() && mTopCounter < mBounds.height())
                drawTintBitmap();
        } else if (mAngle >= 180.0F && mAngle <= 270.0F) {
            while (mLeftCounter > -mChildBounds.width() && mTopCounter > -mChildBounds.height())
                drawTintBitmap();
        } else if (mAngle >= 270.0F && mAngle <= 360.0F) {
            while (mLeftCounter < mBounds.width() && mTopCounter > -mChildBounds.height())
                drawTintBitmap();
        }

        postInvalidate();
    }

    private void drawTintBitmap() {
        mTintCanvas.drawBitmap(mChildBitmap, mLeftCounter, mTopCounter, null);
        mLeftCounter += mOffsetX;
        mTopCounter += mOffsetY;
    }
}
