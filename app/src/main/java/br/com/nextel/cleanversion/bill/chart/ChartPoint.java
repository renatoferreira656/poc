package br.com.nextel.cleanversion.bill.chart;

import android.graphics.Paint;

import java.util.List;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    private float x;
    private float y;
    private Float originalValue;
    private ChartPointStatus status;
    private String title;

    public ChartPoint(Float originalValue, ChartPointStatus status, String title) {
        this.originalValue = originalValue;
        this.status = status;
        this.title = title;
    }

    public ChartPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isInside(float x, float y) {
        int diff = 50;
        float aboveY = this.y - diff;
        float bottomY = this.y + diff;
        float leftX = this.x - diff;
        float rightX = this.x + diff;
        return rightX > x && leftX < x && bottomY > y && aboveY < y;
    }

    public String getTitle() {
        return title;
    }

    public float getX() {
        return x;
    }

    public ChartPoint setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public ChartPoint setY(float y) {
        this.y = y;
        return this;
    }

    public Float getOriginalValue() {
        return originalValue;
    }

    public Paint getStatusPaint() {
        return this.status.getPaint();
    }

    public ChartPointStatus status() {
        return this.status;
    }

    @Override
    public String toString() {
        return "ChartPoint{ 'x'=" + x + ", 'y'=" + y + '}';
    }

    public static Float maxY(List<ChartPoint> points) {
        float max = 0;
        for (ChartPoint point : points) {
            Float value = point.getOriginalValue();
            if (max < value.floatValue()) {
                max = value.floatValue();
            }
        }
        return max;
    }

    public static Float minY(List<ChartPoint> points) {
        float min = 1000000000000000f;
        for (ChartPoint point : points) {
            Float value = point.getOriginalValue();
            if (min > value.floatValue()) {
                min = value.floatValue();
            }
        }
        return min;
    }

}
