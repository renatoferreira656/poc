package br.com.dextra.poccanvas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import static android.R.attr.strokeWidth;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    public enum Status {
        PAID("#5ACD76"), OVERDUE("#C43C3C"), PENDING("#F6F913"), LATE_PAID("#FFFFFF");

        private Paint paint;

        private Status(String color) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.parseColor(color));
            paint.setStrokeWidth(strokeWidth);
        }

        public Paint getPaint() {
            return paint;
        }
    }

    private float x;
    private float y;
    private Double originalValue;
    private Status status;

    public ChartPoint(Double originalValue, Status status) {
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

    public Paint getStatusPaint() {
        return this.status.getPaint();
    }

    public Status status(){
        return this.status;
    }

    @Override
    public String toString() {
        return "ChartPoint{ 'x'=" + x + ", 'y'=" + y + '}';
    }
}
