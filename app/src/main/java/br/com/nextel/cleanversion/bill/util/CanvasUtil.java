package br.com.nextel.cleanversion.bill.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;

/**
 * Created by renato.soares on 12/26/16.
 */

public class CanvasUtil {

    private static Float paddingY;

    public static Context context;

    public static void context(Context context){
        CanvasUtil.context = context;
        paddingY = CanvasUtil.convertDpToPixel(40f);
    }

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

    public static void drawCircle(Canvas canvas, ChartPoint point, Integer radius) {
        if(point == null){
            return;
        }
        canvas.drawCircle(point.getX(), point.getY(), radius, PaintUtil.defaultPaint());
        canvas.drawCircle(point.getX(), point.getY(), radius - 4, point.getStatusPaint());

        int diff = radius / 2;
        canvas.drawBitmap(point.status().bitmap(context), point.getX() - diff, point.getY() - diff, PaintUtil.defaultPaint());
    }

    public static void drawText(Canvas canvas, Object text, ChartPoint point){
        if(text == null){
            return;
        }
        canvas.drawText(PriceUtils.formatValueToText(text), point.getX() - 50, point.getY() - 30, PaintUtil.textPaint(context));
    }

    public static Float discoverYScreenPoint(Float height, Float point, float maxValue, float min) {
        float less = convertDpToPixel(point.floatValue());
        maxValue = convertDpToPixel(maxValue);
        min = convertDpToPixel(min);
        float convertedHeight = height * (less - min) / (maxValue - min);
        return height  - convertedHeight;
    }

    public static Float convertDpToPixel(Float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static List<ChartPoint> convertDataToPoints(List<ChartPoint> points, Float space, Float paddingX, Float height) {
        float max = ChartPoint.maxY(points);
        float min = ChartPoint.minY(points);
        float i = paddingX;
        for (ChartPoint point : points) {
            float yScreenPoint = CanvasUtil.discoverYScreenPoint(height, point.getOriginalValue(), max, min);
            point.setX(i).setY(addPaddingY(height, yScreenPoint));
            i = i + space;
        }
        return points;
    }

    private static float addPaddingY(Float height, Float y) {
        if (y < paddingY) {
            return y + paddingY;
        }

        if (y > height - paddingY + CanvasUtil.convertDpToPixel(10f)) {
            return y - paddingY;
        }
        return y;
    }

    public static List<Path> calcPaths(Float height, List<ChartPoint> points) {
        List<Path> paths = new ArrayList<>();
        ChartPoint old = new ChartPoint(0, height);
        for(ChartPoint point : points){
            paths.add(calcPath(height, old, point));
            old = point;
        }
        return paths;
    }

    public static Path calcPath(Float height, ChartPoint init, ChartPoint end) {
        Path path = new Path();
        path.moveTo(init.getX(), init.getY());
        path.lineTo(end.getX(), end.getY());
        path.lineTo(end.getX(), height);
        path.lineTo(init.getX(), height);
        path.lineTo(init.getX(), init.getY());
        return path;
    }

    public static void drawLines(Canvas canvas, List<ChartPoint> points, int position) {
        ChartPoint last = null;
        Iterator<ChartPoint> it = points.iterator();
        int i = -1;
        while(it.hasNext()){
            i++;
            if(last == null){
                last = it.next();
                if(position != i) {
                    CanvasUtil.drawText(canvas, last.getOriginalValue(), last);
                }
                continue;
            }
            ChartPoint point = it.next();
            if(position != i) {
                CanvasUtil.drawText(canvas, point.getOriginalValue(), point);
            }
            CanvasUtil.drawLine(canvas, last, point);
            last = point;
        }
    }
}
