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

package com.gigamole.tintlayout.lib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by GIGAMOLE on 21.06.2015.
 */
public class TintLayout extends FrameLayout {

    private double angle;
    private int[] colors;

    private int width;
    private int height;
    private double radius;

    private double xOffset;
    private double yOffset;

    private double left;
    private double top;

    private Canvas canvas;
    private Bitmap bitmap;

    private Canvas tintCanvas;
    private Bitmap tintBitmap;

    private int childLeft;
    private int childTop;

    private int childWidth;
    private int childHeight;

    private Bitmap childBitmap;

    private boolean isChildGet;
    private boolean isTintGet;
    private boolean isGet;

    private final Matrix matrix = new Matrix();
    private final PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private Paint tintPaint = new Paint(Paint.ANTI_ALIAS_FLAG) {
        {
            setDither(true);
            setAntiAlias(true);
            setFilterBitmap(true);
            setXfermode(porterDuffXfermode);
        }
    };

    private Paint ditherPaint = new Paint(Paint.ANTI_ALIAS_FLAG) {
        {
            setFilterBitmap(true);
            setDither(true);
            setAntiAlias(true);
        }
    };

    public TintLayout(Context context) {
        this(context, null);
    }

    public TintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TintLayout);

        try {
            final double tempAngle = typedArray.getFloat(R.styleable.TintLayout_angle, 45f);
            if (tempAngle >= 0 && tempAngle <= 360) {
                this.angle = tempAngle;
            } else {
                throw new IllegalArgumentException(getResources().getString(R.string.angle_exception));
            }

            final int colorsId = typedArray.getResourceId(R.styleable.TintLayout_colors, 0);
            if (colorsId != 0) {
                setColors(getResources().getIntArray(colorsId));
            } else {
                setColors(getResources().getIntArray(R.array.default_colors));
            }
        } finally {
            typedArray.recycle();
        }


        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        if (angle >= 0 && angle <= 360) {
            this.angle = angle;

            requestLayout();
            invalidate();
        } else {
            throw new IllegalArgumentException(getResources().getString(R.string.angle_exception));
        }
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = MeasureSpec.getSize(heightMeasureSpec);

        if (this.width > this.height) {
            this.radius = this.width / 2;
        } else {
            this.radius = this.height / 2;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (this.isGet && this.isChildGet && this.isTintGet) {
            this.canvas.drawBitmap(this.tintBitmap, this.matrix, this.tintPaint);

            if (this.colors.length == 1) {
                this.canvas.drawColor(this.colors[0], PorterDuff.Mode.SRC_IN);
            } else {
                final Shader shader = new RadialGradient(
                        (float) this.width / 2,
                        (float) this.height / 2,
                        (float) Math.sqrt(this.width * this.width + this.height * this.height) / 2,
                        this.colors,
                        null,
                        Shader.TileMode.CLAMP
                );

                this.ditherPaint.setShader(shader);
                this.ditherPaint.setXfermode(this.porterDuffXfermode);

                this.canvas.drawRect(new Rect(
                                0,
                                0,
                                this.width,
                                this.height),
                        this.ditherPaint
                );

                this.ditherPaint.setShader(null);
                this.ditherPaint.setXfermode(null);
            }

            canvas.drawBitmap(this.bitmap, this.matrix, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (getChildCount() == 1) {
            getChildBitmap();
            getBitmap();
            getTintBitmap();
        } else {
            throw new IllegalArgumentException(getResources().getString(R.string.child_exception));
        }
    }

    private void getTintBitmap() {
        this.tintBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        this.tintCanvas = new Canvas(this.tintBitmap);

        this.left = (float) this.childLeft;
        this.top = (float) this.childTop;

        final double x = this.left + Math.cos(angle * Math.PI / 180) * this.radius;
        final double y = this.top + Math.sin(angle * Math.PI / 180) * this.radius;

        this.xOffset = (x - this.left) / this.radius;
        this.yOffset = (y - this.top) / this.radius;

        if (this.angle >= 0 && this.angle < 90) {
            while (this.left < this.width && this.top < this.height) {
                drawTintBitmap();
            }
        } else if (this.angle >= 90 && this.angle < 180) {
            while (this.left > -this.childWidth && this.top < this.height) {
                drawTintBitmap();
            }
        } else if (this.angle >= 180 && this.angle < 270) {
            while (this.left > -this.childWidth && this.top > -this.childHeight) {
                drawTintBitmap();
            }
        } else if (this.angle >= 270 && this.angle <= 360) {
            while (this.left < this.width && this.top > -this.childHeight) {
                drawTintBitmap();
            }
        }

        this.isTintGet = true;
    }

    private void getBitmap() {
        this.bitmap = Bitmap.createBitmap(convertToMutable(drawableToBitmap(getBackground())));
        this.canvas = new Canvas(this.bitmap);

        this.isGet = true;
    }

    private void getChildBitmap() {
        final View child = getChildAt(0);
        ((LayoutParams) child.getLayoutParams()).gravity = Gravity.CENTER;

        this.childLeft = child.getLeft();
        this.childTop = child.getTop();

        this.childWidth = child.getRight() - this.childLeft;
        this.childHeight = child.getBottom() - this.childTop;

        child.setDrawingCacheEnabled(true);
        child.buildDrawingCache();
        this.childBitmap = Bitmap.createBitmap(child.getDrawingCache());
        child.setDrawingCacheEnabled(false);

        this.isChildGet = true;
    }

    private void drawTintBitmap() {
        this.tintCanvas.drawBitmap(this.childBitmap, (float) this.left, (float) this.top, this.ditherPaint);

        this.left += this.xOffset;
        this.top += this.yOffset;
    }

    private Bitmap drawableToBitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Bitmap convertToMutable(final Bitmap imgIn) {
        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();
        final Bitmap.Config type = imgIn.getConfig();

        File outputFile = null;
        final File outputDir = getContext().getCacheDir();
        try {
            outputFile = File.createTempFile(Long.toString(System.currentTimeMillis()), null, outputDir);
            outputFile.deleteOnExit();

            final RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw");
            final FileChannel channel = randomAccessFile.getChannel();
            final MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.getRowBytes() * height);

            imgIn.copyPixelsToBuffer(map);

            final Bitmap result = Bitmap.createBitmap(width, height, type);

            map.position(0);
            result.copyPixelsFromBuffer(map);

            channel.close();
            randomAccessFile.close();

            outputFile.delete();
            return result;
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (outputFile != null)
                outputFile.delete();
        }
        return null;
    }
}
