package br.com.nextel.cleanversion.bill.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * Created by renato.soares on 12/22/16.
 */
public class PaintUtil {

    private static Paint paintText;
    private static Paint paint;
    private static Integer strokeWidth;
    private static Paint pulsePaint;

    public static void setStrokeWidth(Integer strokeWidth) {
        PaintUtil.strokeWidth = strokeWidth;
    }

    public static Paint textPaint(Context context) {
        if (paintText != null) {
            return paintText;
        }
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.argb(150, 255, 255, 255));
        paintText.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics()));
        return paintText;
    }

    public static Paint defaultPaint() {
        if (paint != null) {
            return paint;
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
        return paint;
    }

    public static Paint newPaint(String color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(strokeWidth);
        return paint;
    }

    public static Paint pulsePaint() {
        if (pulsePaint != null) {
            return pulsePaint;
        }
        pulsePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pulsePaint.setStrokeWidth(2);
        return pulsePaint;
    }
}
