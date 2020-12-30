package com.zj.shadow.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by zhangjian on 2020/12/22 in project shadow.
 */
class RadiusDrawable extends Drawable {

    private int radius;
    private int color;

    public RadiusDrawable(int radius, int color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        final Rect contentBounds = new Rect();
        canvas.getClipBounds(contentBounds);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        int rx = Math.min(radius, contentBounds.height() / 2);
        int ry = Math.min(radius, contentBounds.height() / 2);
        canvas.drawRoundRect(new RectF(contentBounds), rx, ry, paint);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}