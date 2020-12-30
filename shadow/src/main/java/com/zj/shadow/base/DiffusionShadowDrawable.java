package com.zj.shadow.base;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import androidx.annotation.NonNull;

import static com.zj.shadow.base.RectUtils.mirrorX;
import static com.zj.shadow.base.RectUtils.mirrorY;

/**
 * Created by zhangjian on 2020/12/22 in project shadow.
 */
public class DiffusionShadowDrawable extends LayerDrawable {
    protected int radius; //圆角

    protected int shadowColor; //阴影的颜色值

    protected int color; //内容区的颜色值

    private int shadowWidth;  //阴影的宽度

    //四周弥散
    public DiffusionShadowDrawable(int color, int shadowColor, int radius, int shadowWidth) {
        this();
        this.color = color;
        this.shadowColor = shadowColor;
        this.radius = radius;
        this.shadowWidth = shadowWidth;

        colors = new int[]{shadowColor, color & 0x00FFFFFF};
    }

    private int[] colors;
    private float[] positions;

    private DiffusionShadowDrawable() {
        super(new Drawable[]{});
    }

    private final Rect contentBounds = new Rect();
    private final Rect shadowBounds = new Rect();

    //阴影的圆角
    private int sr;

    private Path cornerShadowPath = new Path();

    private RectF tempOut = new RectF(); //圆得外切四边形

    private RectF tempLinear = new RectF();

    private Paint cornerShadowPaint = new Paint();

    private float centX;
    private float centY;

    private float stopOffset;

    @Override
    public boolean isProjected() {
        return true;
    }

    /**
     * Optimized for drawing ripples with a mask layer and optional content.
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.getClipBounds(contentBounds);

        radius = Math.min(radius, contentBounds.height() / 2);  //必须小于一半

        sr = radius + shadowWidth;

        shadowBounds.left = contentBounds.left - shadowWidth;
        shadowBounds.top = contentBounds.top - shadowWidth;
        shadowBounds.right = contentBounds.right + shadowWidth;
        shadowBounds.bottom = contentBounds.bottom + shadowWidth;

        cornerShadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cornerShadowPaint.setAntiAlias(true);

        centX = shadowBounds.exactCenterX();
        centY = shadowBounds.exactCenterY();

        stopOffset = radius / sr;

        positions = new float[]{stopOffset, 1f};

        drawRadius(canvas);

        drawLinear(canvas);

        //画背景
        contentDrawable().draw(canvas);
    }

    private void drawRadius(Canvas canvas) {
        //左上角 阴影圆弧
        tempOut.left = shadowBounds.left;
        tempOut.right = tempOut.left + 2 * sr;
        tempOut.top = shadowBounds.top;
        tempOut.bottom = tempOut.top + 2 * sr;

        int startAngle;

        //四周得圆角，
        for (int i = 0; i < 4; i++) {
            if (i != 0) {    //需要镜像
                if (i % 2 == 0) {
                    mirrorY(tempOut, centY);
                } else {
                    mirrorX(tempOut, centX);
                }
            }
            startAngle = 180 + 90 * i;
            cornerShadowPath.reset();
            cornerShadowPath.moveTo(tempOut.centerX(), tempOut.centerY());
            cornerShadowPath.arcTo(tempOut, startAngle, 90, false);
            cornerShadowPath.close();
            cornerShadowPaint.setShader(new RadialGradient(tempOut.centerX(), tempOut.centerY(), sr, colors, positions, Shader.TileMode.CLAMP));
            canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        }

    }

    private void drawLinear(Canvas canvas) {
        //画三边的线性渐变
        float broadsideResidue = shadowBounds.height() - tempOut.height();

        if (broadsideResidue > 0) {
            //左右侧边剩余直线渐变大于0
            tempLinear.left = shadowBounds.left;
            tempLinear.right = shadowBounds.left + sr;
            tempLinear.top = shadowBounds.top + sr;
            tempLinear.bottom = shadowBounds.bottom - sr;
            cornerShadowPaint.setShader(new LinearGradient(tempLinear.right, 0, tempLinear.left, 0, colors, positions, Shader.TileMode.CLAMP));
            canvas.drawRect(tempLinear, cornerShadowPaint);

            mirrorX(tempLinear, centX);
            cornerShadowPaint.setShader(new LinearGradient(tempLinear.left, 0, tempLinear.right, 0, colors, positions, Shader.TileMode.CLAMP));
            canvas.drawRect(tempLinear, cornerShadowPaint);
        }

        float baseResidue = shadowBounds.width() - tempOut.width();
        if (baseResidue > 0) {
            tempLinear.left = shadowBounds.left + sr;
            tempLinear.right = shadowBounds.right - sr;
            tempLinear.top = shadowBounds.top;
            tempLinear.bottom = shadowBounds.top + sr;
            cornerShadowPaint.setShader(new LinearGradient(0, tempLinear.bottom, 0, tempLinear.top, colors, positions, Shader.TileMode.CLAMP));
            canvas.drawRect(tempLinear, cornerShadowPaint);

            mirrorY(tempLinear, centY);
            cornerShadowPaint.setShader(new LinearGradient(0, tempLinear.top, 0, tempLinear.bottom, colors, positions, Shader.TileMode.CLAMP));
            canvas.drawRect(tempLinear, cornerShadowPaint);
        }
    }

    /**
     * 可重写改方法以处理中间内容区是一些负责drawable的情况，比如带外圈线的drawable等
     *
     * @return
     */
    protected Drawable contentDrawable() {
        return new RadiusDrawable(radius, color);
    }

}
