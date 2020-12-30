package com.zj.shadow.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by zhangjian on 2020/12/16 in project Shadow.
 */
public class DropShadowGradientDrawable extends DropShadowDrawable {

    private int colorGradientStart;

    public DropShadowGradientDrawable( int colorGradientStart,int color, int radius) {
        super(color, radius);
        this.colorGradientStart = colorGradientStart;
    }

    @Override
    protected Drawable contentDrawable() {
        return new RadiusGradientDrawable();
    }

    private class RadiusGradientDrawable extends Drawable{
        @Override
        public void draw(@NonNull Canvas canvas) {

            final Rect contentBounds = new Rect();
            canvas.getClipBounds(contentBounds);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new LinearGradient(0,contentBounds.top,0,contentBounds.bottom,colorGradientStart,color, Shader.TileMode.CLAMP));
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
}
