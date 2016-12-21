package br.com.dextra.poccanvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    public enum Status{
        PAID, OVERDUE, PENDING, LATE_PAID;
    }

    private float x;
    private float y;
    private Double originalValue;
    private Status status;

    public ChartPoint(Double originalValue, Status status){
        this.originalValue = originalValue;
        this.status = status;
    }

    public ChartPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public ChartPoint(float x, float y, Double originalValue) {
        this.x = x;
        this.y = y;
        this.originalValue = originalValue;
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

    public void setOriginalValue(Double originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ChartPoint{ 'x'=" + x + ", 'y'=" + y + '}';
    }
}
