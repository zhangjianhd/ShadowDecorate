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
 * Created by zhangjian on 2020/12/16 in project Shadow.
 */
public class DropShadowDrawable extends LayerDrawable {

    protected int radius; //圆角

    protected int color; //阴影的颜色值，也是内容区默认的颜色值

    private int offset;  //竖直方向的偏移量，也就是阴影的宽度

    //四周弥散
    public DropShadowDrawable(int color, int radius) {
        this();
        this.color = color;
        this.radius = radius;

        colors = new int[]{color, color & 0x00FFFFFF};
        positions = new float[]{stopOffset, 1f};
    }

    private int[] colors;
    private float[] positions;

    private DropShadowDrawable() {
        super(new Drawable[]{});
    }

    private final Rect contentBounds = new Rect();
    private final Rect shadowBounds = new Rect();

    //投影和内容区的比例
    private float scale;

    //阴影的圆角
    private int sr;

    //x轴的投影偏移
    private int offsetX;

    private Path cornerShadowPath = new Path();

    private RectF tempOut = new RectF(); //圆得外切四边形

    private RectF tempLinear = new RectF();

    private Paint cornerShadowPaint = new Paint();

    private float centX;
    private float centY;

    private float stopOffset = 0.2f;

    /**
     * Optimized for drawing ripples with a mask layer and optional content.
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.getClipBounds(contentBounds);

        offset = (int) (contentBounds.height() / 5f + 4);  //根据ui给出的 50->3.5/30->2.5的得出曲线y = 1/20x+1

        scale = (contentBounds.height() + offset) / (float) contentBounds.height();

        sr = (int) (scale * radius);  //限制radius之前计算出得阴影圆角

        sr = Math.max(sr, (int) (offset * 1.2));

        radius = Math.min(radius, contentBounds.height() / 2);  //必须小于一半

        offsetX = (int) (offset * scale / 2);

        shadowBounds.left = contentBounds.left - offsetX;
        shadowBounds.top = contentBounds.top;
        shadowBounds.right = contentBounds.right + offsetX;
        shadowBounds.bottom = contentBounds.bottom + offset;

        sr = Math.min(sr, shadowBounds.height() / 2);  //边长限制得阴影圆角

        cornerShadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cornerShadowPaint.setAntiAlias(true);

        centX = shadowBounds.exactCenterX();
        centY = shadowBounds.exactCenterY();

        drawRadius(canvas);

        drawLinear(canvas);

        //画背景
        contentDrawable().draw(canvas);
        invalidateSelf();
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
        float broadsideResidue = shadowBounds.height() - 2 * sr;

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

        tempLinear.left = shadowBounds.left + sr;
        tempLinear.right = shadowBounds.right - sr;
        tempLinear.top = shadowBounds.bottom - sr;
        tempLinear.bottom = shadowBounds.bottom;
        cornerShadowPaint.setShader(new LinearGradient(0, tempLinear.top, 0, tempLinear.bottom, colors, positions, Shader.TileMode.CLAMP));
        canvas.drawRect(tempLinear, cornerShadowPaint);
    }

    //允许投影，硬件加速可绘制到可绘制区域以外的区域
    @Override
    public boolean isProjected() {
        return true;
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
