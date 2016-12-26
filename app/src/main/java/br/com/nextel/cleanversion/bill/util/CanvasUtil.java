package br.com.nextel.cleanversion.bill.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;

/**
 * Created by renato.soares on 12/26/16.
 */

public class CanvasUtil {
    public static class PulsePoint {
        private Integer circleRadius;
        private Integer radiusPosition = 0;
        private Boolean open = false;

        public PulsePoint(Integer circleRadius){
            this.circleRadius = circleRadius;
        }

        private int calcPulseRadius() {
            int maxRadius = circleRadius + 20;
            float speed = 0.8f;
            if(open) {
                radiusPosition = Math.round(radiusPosition + speed);
                if(maxRadius < radiusPosition){
                    open = false;
                }
                return (int)radiusPosition;
            }
            radiusPosition = Math.round(radiusPosition - speed);
            if(1 > radiusPosition){
                open = true;
            }
            return (int)radiusPosition;

        }
    }

    public static void pulseCircle(Canvas canvas, ChartPoint point, PulsePoint pulse) {
        Paint paint = PaintUtil.pulsePaint();
        paint.setColor(Color.argb(100, 255, 255, 255));
        paint.setStyle(Paint.Style.STROKE);
        int radius = pulse.calcPulseRadius();
        canvas.drawCircle(point.getX(), point.getY(), radius, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(70, 255, 255, 255));
        canvas.drawCircle(point.getX(), point.getY(), radius, paint);
    }

    public static void drawLine(Canvas canvas, ChartPoint initial, ChartPoint end) {
        canvas.drawLine(initial.getX(), initial.getY(), end.getX(), end.getY(), PaintUtil.defaultPaint());
    }

    public static void drawCircle(Context context, Canvas canvas, ChartPoint point, Integer radius) {
        if(point == null){
            return;
        }
        canvas.drawCircle(point.getX(), point.getY(), radius, PaintUtil.defaultPaint());
        canvas.drawCircle(point.getX(), point.getY(), radius - 4, point.getStatusPaint());

        int diff = radius / 2;
        canvas.drawBitmap(point.status().bitmap(context), point.getX() - diff, point.getY() - diff, PaintUtil.defaultPaint());
    }

    public static void drawText(Context context, Canvas canvas, Object text, ChartPoint point){
        if(text == null){
            return;
        }
        canvas.drawText(PriceUtils.formatValueToText(text), point.getX() - 50, point.getY() - 30, PaintUtil.textPaint(context));
    }

    public static Float discoverYScreenPoint(Context context, Float height, Float point, float maxValue, float min) {
        float less = convertDpToPixel(context, point.floatValue());
        maxValue = convertDpToPixel(context, maxValue);
        min = convertDpToPixel(context, min);
        float convertedHeight = height * (less - min) / (maxValue - min);
        return height  - convertedHeight;
    }

    public static Float convertDpToPixel(Context context, Float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
