package br.com.nextel.cleanversion.bill.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import br.com.dextra.cleanversion.R;

/**
 * Created by renato.soares on 12/21/16.
 */
public class ChartPoint {

    public enum Status {
        PAID("#5ACD76", R.drawable.ok, R.drawable.bill_graph_green_gradient),
        OVERDUE("#C43C3C", R.drawable.alert, R.drawable.bill_graph_red_gradient),
        PENDING("#F6F913", R.drawable.ok, R.drawable.bill_graph_yellow_gradient),
        LATE_PAID("#FFFFFF", R.drawable.alert, R.drawable.bill_graph_green_gradient);

        private Paint paint;
        private int bitmapRes;
        private int backgroundColorRes;
        private Bitmap bitmap;
        private Drawable backgroundColor;

        private Status(String color, int bitmapRes, int backgroundColor) {
            this.paint = PaintUtil.newPaint(color);
            this.bitmapRes = bitmapRes;
            this.backgroundColorRes = backgroundColor;
        }

        public Paint getPaint() {
            return paint;
        }

        public Bitmap bitmap(Context context) {
            if(this.bitmap != null){
                return this.bitmap;
            }

            this.bitmap = BitmapFactory.decodeResource(context.getResources(), this.bitmapRes);
            return this.bitmap;
        }

        public Drawable background(Context context) {
            if(this.backgroundColor != null){
                return this.backgroundColor;
            }
            this.backgroundColor = ContextCompat.getDrawable(context, this.backgroundColorRes);
            return this.backgroundColor;
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
