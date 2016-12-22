package br.com.nextel.cleanversion.bill.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.dextra.cleanversion.R;

public class LineChart extends View {

    private final Integer SPACE_DEFAULT = 40;
    private final Integer QTD_PER_SCREEN_DEFAULT = 3;
    private final Float paddingY;
    private Bitmap alert;
    private Bitmap ok;

    private Float paddingX;

    private List<ChartPoint> points;
    private Integer space;
    private Integer qtdPerScreen;
    private Float width;
    private Float height;

    private Integer circleRadius;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        space = SPACE_DEFAULT;
        qtdPerScreen = QTD_PER_SCREEN_DEFAULT;
        paddingY = convertDpToPixel(40f);
        points = new ArrayList<>();
        circleRadius = 20;

        alert = BitmapFactory.decodeResource(context.getResources(), R.drawable.alert);
        ok = BitmapFactory.decodeResource(context.getResources(), R.drawable.ok);
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
        canvas.drawCircle(point.getX(), point.getY(), circleRadius, PaintUtil.defaultPaint());
        canvas.drawCircle(point.getX(), point.getY(), circleRadius - 4, point.getStatusPaint());


        int diff = circleRadius / 2;
        Bitmap bitmap = getBitmapCircle(point.status());
        if(bitmap !=null) {
            canvas.drawBitmap(bitmap, point.getX() - diff, point.getY() - diff, PaintUtil.defaultPaint());
        }
    }

    private Bitmap getBitmapCircle(ChartPoint.Status status) {
        if(ChartPoint.Status.OVERDUE.equals(status)){
            return alert;
        }
        if(ChartPoint.Status.PAID.equals(status)){
            return ok;
        }
        if(ChartPoint.Status.PENDING.equals(status)){
            return ok;
        }
        return null;
    }

    private void drawText(Canvas canvas, Object text, ChartPoint point){
        if(text == null){
            return;
        }
        canvas.drawText(formatValueToText(text), point.getX() - 50, point.getY() - 30, PaintUtil.textPaint());
    }

    private String formatValueToText(Object text) {
        return "R$ " + text.toString().replace(".", ",");
    }

    private void drawLine(Canvas canvas, ChartPoint initial, ChartPoint end) {
        canvas.drawLine(initial.getX(), initial.getY(), end.getX(), end.getY(), PaintUtil.defaultPaint());
    }

    public LineChart setData(List<ChartPoint> originalData) {
        this.points = originalData;
        return this;
    }

    @NonNull
    private void convertDataToPoints() {
        float max = maxY();
        float i = paddingX;
        for (ChartPoint point : points) {
            float yScreenPoint = discoverYScreenPoint(point.getOriginalValue(), max);
            point.setX(i).setY(addPaddingY(yScreenPoint));
            i = i + space;
        }
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

    public List<ChartPoint> getPoints() {
        return points;
    }

    public float width(){
        return this.width;
    }

    public float padding() {
        return paddingX;
    }

    public ChartPoint point(int position) {
        ChartPoint chartPoint = this.points.get(position);
        if(chartPoint == null){
            return null;
        }
        return chartPoint;
    }

    public float maxX() {
        float max = 0;
        for(ChartPoint point : this.points){
            if(max < point.getX()){
                max = point.getX();
            }
        }
        return max;
    }

    private float maxY() {
        float max = 0;
        for (ChartPoint point : this.points) {
            Double value = point.getOriginalValue();
            if (max < value.floatValue()) {
                max = value.floatValue();
            }
        }
        return max;
    }
}

