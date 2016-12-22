package br.com.nextel.cleanversion.bill.chart;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import static android.R.attr.strokeWidth;

/**
 * Created by renato.soares on 12/22/16.
 */
public class PaintUtil {

    private static Paint paintText;
    private static Paint paint;
    private static Integer strokeWidth = 5;

    public static Paint textPaint() {
        if (paintText != null) {
            return paintText;
        }
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.argb(150, 255, 255, 255));
        paintText.setTextSize(28);
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
}
