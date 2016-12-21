package br.com.dextra.poccanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class LineChart extends View {
    private List<Double> arr;
    private Integer space;
    private Integer qtdPerScreen;
    private Double width;
    private Double height;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        arr = values();
        space = 40;
        qtdPerScreen = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = paint();

        float max = max(arr);
        float height = this.height.floatValue();
        float i = space;

        List<ChartPoint> points = new ArrayList<ChartPoint>();
        for (Double point: arr) {
            float xgoing = convertDataToScreenPoint(point, height, max);
            points.add(new ChartPoint(i, xgoing));
            i = i + space;
        }
        ChartPoint last = null;
        for (ChartPoint point: points) {
            if(last == null){
                canvas.drawCircle(0, height, 20, paint);
                canvas.drawLine(0, height, point.getX(), point.getY(), paint);
            } else {
                canvas.drawLine(last.getX(), last.getY(), point.getX(), point.getY(), paint);
                canvas.drawCircle(last.getX(), last.getY(), 20, paint);
            }
            last = point;
        }
        canvas.drawCircle(last.getX(), last.getY(), 20, paint);
    }

    private float max(List<Double> arr) {
        float max = 0;
        for( Double value : arr){
            if(max < value.floatValue()){
                max = value.floatValue();
            }
        }
        return max;
    }

    @NonNull
    private Paint paint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        return paint;
    }

    @NonNull
    private List<Double> values() {
        List<Double> arr = new ArrayList<Double>(100);
        for(int i = 0; i < 100; i++){
            double size = Math.random() * 10;
            arr.add(size);
        }
        return arr;
    }

    private float convertDataToScreenPoint(Double point, float height, float maxValue) {
        float less = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, point.floatValue(), this.getResources().getDisplayMetrics());
        maxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxValue, this.getResources().getDisplayMetrics());
        return height - (height * less / maxValue);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        space = (this.getRootView().getWidth() / qtdPerScreen);
        width = new Double(arr.size() * space + ( space * 4));
        height = Integer.valueOf(MeasureSpec.getSize(heightMeasureSpec)).doubleValue();
        this.setMeasuredDimension(width.intValue(), height.intValue());
    }
}

