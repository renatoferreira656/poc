package br.com.nextel.cleanversion.bill.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import br.com.dextra.cleanversion.R;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    public enum Status {
        PAID("#5ACD76", R.drawable.ok),
        OVERDUE("#C43C3C", R.drawable.alert),
        PENDING("#F6F913", R.drawable.ok),
        LATE_PAID("#FFFFFF", R.drawable.alert);

        private Paint paint;
        private int bitmapRes;
        private Bitmap bitmap;

        private Status(String color, int bitmapRes) {
            this.paint = PaintUtil.newPaint(color);
            this.bitmapRes = bitmapRes;
        }

        public Paint getPaint() {
            return paint;
        }

        public Bitmap bitmap(Context context) {
            if(this.bitmap != null){
                return this.bitmap;
            }

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), this.bitmapRes);
            this.bitmap = bitmap;
            return bitmap;
        }
    }

    private float x;
    private float y;
    private Double originalValue;
    private Status status;
    private String title;

    public ChartPoint(Double originalValue, Status status, String title) {
        this.originalValue = originalValue;
        this.status = status;
        this.title = title;
    }

    public ChartPoint(float x, float y) {
        this.x = x;
        this.y = y;
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

    public Status status() {
        return this.status;
    }

    @Override
    public String toString() {
        return "ChartPoint{ 'x'=" + x + ", 'y'=" + y + '}';
    }
}
