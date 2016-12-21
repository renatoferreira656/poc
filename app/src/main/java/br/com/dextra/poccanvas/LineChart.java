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
import java.util.Iterator;
import java.util.List;

public class LineChart extends View {

    private final Integer SPACE_DEFAULT = 40;
    private final Integer QTD_PER_SCREEN_DEFAULT = 3;
    private final Float paddingY;
    private Float paddingX;

    private List<ChartPoint> points;
    private Integer space;
    private Integer qtdPerScreen;
    private Float width;
    private Float height;
    private Paint paint;
    private Paint paintPaid;
    private Paint paintOverdue;

    private Integer strokeWidth;
    private Integer circleRadius;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        space = SPACE_DEFAULT;
        qtdPerScreen = QTD_PER_SCREEN_DEFAULT;
        paddingY = convertDpToPixel(40f);
        points = new ArrayList<>();
        circleRadius = 20;
        strokeWidth = 5;
        defaultPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        convertDataToPoints();
        ChartPoint last = null;
        Iterator<ChartPoint> it = points.iterator();
        while(it.hasNext()){
            if(last == null){
                last = it.next();
                drawText(canvas, last.getOriginalValue(), last);
                continue;
            }
            ChartPoint point = it.next();
            drawText(canvas, point.getOriginalValue(), point);
            drawLine(canvas, last, point);
            drawCircle(canvas, last);
            last = point;
        }
        drawCircle(canvas, last);
    }

    private void drawCircle(Canvas canvas, ChartPoint point) {
        if(point == null){
            return;
        }
        canvas.drawCircle(point.getX(), point.getY(), circleRadius, paint);
        canvas.drawCircle(point.getX(), point.getY(), circleRadius- 4, point.getStatusPaint());
    }

    private void drawText(Canvas canvas, Object text, ChartPoint point){
        if(text == null){
            return;
        }
        canvas.drawText(text.toString(), point.getX() - 15, point.getY() - 30, paint);
    }

    private void drawLine(Canvas canvas, ChartPoint initial, ChartPoint end) {
        canvas.drawLine(initial.getX(), initial.getY(), end.getX(), end.getY(), paint);
    }

    public LineChart setData(List<ChartPoint> originalData) {
        this.points = originalData;
        return this;
    }

    @NonNull
    private void convertDataToPoints() {
        float max = max(points);
        float i = paddingX;
        for (ChartPoint point : points) {
            float yScreenPoint = discoverYScreenPoint(point.getOriginalValue(), max);
            point.setX(i).setY(addPaddingY(yScreenPoint));
            i = i + space;
        }
    }

    private float addLeftPaddingX(float x) {
        if (x < paddingX) {
            return x + paddingX;
        }
        return x;
    }

    private float addPaddingY(float y) {
        if (y < paddingY) {
            return y + paddingY;
        }

        if (y > height - paddingY) {
            return y - paddingY;
        }

        return y;
    }

    private float max(List<ChartPoint> arr) {
        float max = 0;
        for (ChartPoint point : arr) {
            Double value = point.getOriginalValue();
            if (max < value.floatValue()) {
                max = value.floatValue();
            }
        }
        return max;
    }

    @NonNull
    private LineChart defaultPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
        return this;
    }

    private float discoverYScreenPoint(Double point, float maxValue) {
        float less = convertDpToPixel(point.floatValue());
        maxValue = convertDpToPixel(maxValue);
        return height - (height * less / maxValue);
    }

    private Float convertDpToPixel(Float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, this.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = this.getRootView().getWidth();

        float half = parentWidth / 2;
        paddingX = half;
        space = (parentWidth / qtdPerScreen);
        if (points == null) {
            this.width = (float) parentWidth;
        } else {
            this.width = calWidth(paddingX);
        }
        height = new Float(MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(this.width.intValue(), height.intValue());
    }

    private float calWidth(float padding) {
        return ((points.size() - 1) * space) + (padding*2);
    }
}

