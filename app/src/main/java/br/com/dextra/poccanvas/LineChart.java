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
    private final Integer QTD_PER_SCREEN_DEFAULT = 3;
    private final Float paddingY;
    private Float paddingX;

    private List<ChartPoint> points;
    private List<Double> originalData;
    private Integer space;
    private Integer qtdPerScreen;
    private Float width;
    private Float height;
    private Paint paint;
    private Integer strokeWidth;

    private Integer circleRadius;
    private ChartPoint initialPoint;

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
        points = this.convertDataToPoints();
        ChartPoint last = initialPoint;
        for (ChartPoint point : points) {
            System.out.println(last);
            canvas.drawLine(last.getX(), last.getY(), point.getX(), point.getY(), paint);
            canvas.drawText(point.getOriginalValue().toString(), point.getX() - 15, point.getY() - 30, paint);
            canvas.drawCircle(last.getX(), last.getY(), circleRadius, paint);
            last = point;
        }
        if (last != null) {
            canvas.drawCircle(last.getX(), last.getY(), circleRadius, paint);
        }
    }

    public LineChart setData(List<Double> originalData) {
        this.originalData = originalData;
        if (originalData == null) {
            return this;
        }
        this.invalidate();
        return this;
    }

    @NonNull
    private List<ChartPoint> convertDataToPoints() {
        float max = max(originalData);
        float i = space;
        List<ChartPoint> points = new ArrayList<ChartPoint>();
        for (Double point : originalData) {
            float yScreenPoint = discoverYScreenPoint(point, max);
            yScreenPoint = addPaddingY(yScreenPoint);
            i = addLeftPaddingX(i);
            points.add(new ChartPoint(i, yScreenPoint, point));
            i = i + space;
        }
        return points;
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

    private float max(List<Double> arr) {
        float max = 0;
        for (Double value : arr) {
            if (max < value.floatValue()) {
                max = value.floatValue();
            }
        }
        return max;
    }

    @NonNull
    private LineChart defaultPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
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
        if (originalData == null) {
            this.width = (float) parentWidth;
        } else {
            this.width = calWidth(half);
        }
        height = new Float(MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(this.width.intValue(), height.intValue());
        initialPoint = new ChartPoint(half, height - paddingY);
    }

    private float calWidth(float padding) {
        return originalData.size() * space + (padding*2);
    }
}

