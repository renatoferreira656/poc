package br.com.nextel.cleanversion.bill.chart;

import android.graphics.Paint;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    private float x;
    private float y;
    private Double originalValue;
    private ChartPointStatus status;
    private String title;

    public ChartPoint(Double originalValue, ChartPointStatus status, String title) {
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

    public Double getOriginalValue() {
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
}
