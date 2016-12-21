package br.com.dextra.poccanvas;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    private float x;
    private float y;
    private Double originalValue;

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

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
