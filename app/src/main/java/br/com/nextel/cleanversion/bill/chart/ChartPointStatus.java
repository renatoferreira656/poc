package br.com.nextel.cleanversion.bill.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import br.com.dextra.cleanversion.R;
import br.com.nextel.cleanversion.bill.util.PaintUtil;

public enum ChartPointStatus {
    PAID("#5ACD76", R.drawable.ok, R.drawable.bill_graph_green_gradient),
    OVERDUE("#C43C3C", R.drawable.alert, R.drawable.bill_graph_red_gradient),
    PENDING("#F6F913", R.drawable.ok, R.drawable.bill_graph_yellow_gradient);

    private Paint paint;
    private int bitmapRes;
    private int backgroundColorRes;
    private Bitmap bitmap;
    private Drawable backgroundColor;

    private ChartPointStatus(String color, int bitmapRes, int backgroundColor) {
        this.paint = PaintUtil.newPaint(color);
        this.bitmapRes = bitmapRes;
        this.backgroundColorRes = backgroundColor;
    }

    public Paint getPaint() {
        return paint;
    }

    public Bitmap bitmap(Context context) {
        if (this.bitmap != null) {
            return this.bitmap;
        }

        this.bitmap = BitmapFactory.decodeResource(context.getResources(), this.bitmapRes);
        return this.bitmap;
    }

    public Drawable background(Context context) {
        if (this.backgroundColor != null) {
            return this.backgroundColor;
        }
        this.backgroundColor = ContextCompat.getDrawable(context, this.backgroundColorRes);
        return this.backgroundColor;
    }
}