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

public class LineChart extends View {

    private final Integer SPACE_DEFAULT = 40;
    private final Integer QTD_PER_SCREEN_DEFAULT = 5;

    private final List<ChartPoint> points;
    private List<Double> originalData;
    private Integer space;
    private Integer qtdPerScreen;
    private Float width;
    private Float height;
    private Paint paint;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        space = SPACE_DEFAULT;
        qtdPerScreen = QTD_PER_SCREEN_DEFAULT;
        points = new ArrayList<>();
        defaultPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        if(last != null) {
            canvas.drawCircle(last.getX(), last.getY(), 20, paint);
        }
    }

    public LineChart setData(List<Double> originalData){
        if(originalData == null){
            return this;
        }
        this.originalData = originalData;
        this.convertDataToPoints(originalData);
        return this;
    }

    @NonNull
    private List<ChartPoint> convertDataToPoints(List<Double> originalData) {
        float max = max(originalData);
        float i = space;
        List<ChartPoint> points = new ArrayList<ChartPoint>();
        for (Double point: originalData) {
            float yScreenPoint = discoverYScreenPoint(point, max);
            points.add(new ChartPoint(i, yScreenPoint));
            i = i + space;
        }
        return points;
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
    private LineChart defaultPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        return this;
    }

    private float discoverYScreenPoint(Double point, float maxValue) {
        float less = convertDpToPixel(point.floatValue());
        maxValue = convertDpToPixel(maxValue);
        return height - (height * less / maxValue);
    }

    private Float convertDpToPixel(Float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, this.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = this.getRootView().getWidth();
        space = (parentWidth / qtdPerScreen);
        if(originalData == null){
            this.width = (float) parentWidth;
        } else {
            this.width = new Float(calWidth());
        }
        height = new Float(MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(this.width.intValue(), height.intValue());
    }

    private int calWidth() {
        return originalData.size() * space + ( space * 4);
    }
}

