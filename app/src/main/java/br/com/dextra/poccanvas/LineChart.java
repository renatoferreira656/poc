package br.com.dextra.poccanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class LineChart extends View {
    private Double width;
    private Double height;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        List<Double> arr = new ArrayList<Double>();
        arr.add(10.0);
        arr.add(15.0);
        arr.add(6.0);
        arr.add(20.0);
        arr.add(1.0);
        arr.add(30.0);
        float i = 0;
        float next = 0;
        for (Double point: arr) {
            float height = this.height.floatValue();
            float xgoing = bla(point, height, 30);
            float stopX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, this.getResources().getDisplayMetrics());
            next = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i + 20, this.getResources().getDisplayMetrics());

            canvas.drawLine(next, xgoing, stopX, height, paint);
            canvas.drawText(point.toString(), next, height, paint);
            i = i + 20;
        }
    }

    private float bla(Double point, float height, float maxValue) {
        float less = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, point.floatValue(), this.getResources().getDisplayMetrics());
        maxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxValue, this.getResources().getDisplayMetrics());
        return height - (height * less / maxValue);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Integer.valueOf(MeasureSpec.getSize(widthMeasureSpec)).doubleValue();
        height = Integer.valueOf(MeasureSpec.getSize(heightMeasureSpec)).doubleValue();
    }
}

