package br.com.nextel.cleanversion.bill.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.nextel.cleanversion.bill.activity.PriceUtils;
import br.com.nextel.cleanversion.bill.listener.PagerGraphListener;

public class LineChart extends View {

    private final Float paddingY = convertDpToPixel(25f);
    private Float paddingX;

    private List<ChartPoint> points = new ArrayList<>();
    private Integer space = 40;
    private Float width;
    private Float height;
    private Integer paddingScreenCircle = 10;

    private Integer circleRadius = convertDpToPixel(10f).intValue();
    private int position;
    private float radiusPosition = 0;
    private boolean open = true;

    List<Path> paths = new ArrayList<Path>();
    private Paint paintStrokePath;
    private Paint paintFillPath;

    private PagerGraphListener pagerListener;
    private boolean notFill = false;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        PaintUtil.setStrokeWidth(convertDpToPixel(2.5f).intValue());

        paintFillPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillPath.setStrokeWidth(convertDpToPixel(10f));
        paintFillPath.setStyle(Paint.Style.FILL);

        paintStrokePath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillPath.setStrokeWidth(convertDpToPixel(10f));
        paintStrokePath.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(space < 0){
            invalidate();
            return;
        }
        convertDataToPoints();
        drawAllLines(canvas);
        drawPath(canvas);
        drawAllCircles(canvas);
        invalidate();
    }

    private void drawAllLines(Canvas canvas) {
        ChartPoint last = null;
        Iterator<ChartPoint> it = points.iterator();
        int i = -1;
        while(it.hasNext()){
            i++;
            if(last == null){
                last = it.next();
                if(position != i) {
                    drawText(canvas, last.getOriginalValue(), last);
                }
                continue;
            }
            ChartPoint point = it.next();
            if(position != i) {
                drawText(canvas, point.getOriginalValue(), point);
            }
            drawLine(canvas, last, point);
            last = point;
        }
    }

    private void drawAllCircles(Canvas canvas) {
        int i= 0;
        for(ChartPoint point : points){
            if(position == i) {
                pulseCircle(canvas, point);
            }
            drawCircle(canvas, point);
            i++;
        }
    }

    private void drawPath(Canvas canvas){
        if(this.paths.isEmpty()){
            return;
        }
        int i = 0;
        notFill = true;
        for(Path path: this.paths) {
            int alpha = (i > position) ? 10 : 40;
            paintFillPath.setColor(Color.argb(alpha, 255, 255, 255));
            canvas.drawPath(path, paintFillPath);
            paintStrokePath.setColor(Color.argb(alpha + 5, 255, 255, 255));
            canvas.drawPath(path, paintStrokePath);
            i++;
        }
    }

    private void calcPath(ChartPoint init, ChartPoint end) {
        if(notFill){
            return;
        }
        Path path = new Path();
        path.moveTo(init.getX(), init.getY());
        path.lineTo(end.getX(), end.getY());
        path.lineTo(end.getX(), height);
        path.lineTo(init.getX(), height);
        path.lineTo(init.getX(), init.getY());
        paths.add(path);
    }

    private void pulseCircle(Canvas canvas, ChartPoint point) {
        Paint paint = PaintUtil.pulsePaint();
        paint.setColor(Color.argb(100, 255, 255, 255));
        paint.setStyle(Paint.Style.STROKE);
        int radius = calcPulseRadius();
        canvas.drawCircle(point.getX(), point.getY(), radius, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(70, 255, 255, 255));
        canvas.drawCircle(point.getX(), point.getY(), radius, paint);
    }

    private int calcPulseRadius() {
        int maxRadius = circleRadius + 20;
        float speed = 0.8f;
        if(open) {
            radiusPosition = radiusPosition + speed;
            if(maxRadius < radiusPosition){
                open = false;
            }
            return (int)radiusPosition;
        }
        radiusPosition = radiusPosition - speed;
        if(1 > radiusPosition){
            open = true;
        }
        return (int)radiusPosition;

    }

    private void drawCircle(Canvas canvas, ChartPoint point) {
        if(point == null){
            return;
        }
        canvas.drawCircle(point.getX(), point.getY(), circleRadius, PaintUtil.defaultPaint());
        canvas.drawCircle(point.getX(), point.getY(), circleRadius - 4, point.getStatusPaint());

        int diff = circleRadius / 2;
        canvas.drawBitmap(point.status().bitmap(getContext()), point.getX() - diff, point.getY() - diff, PaintUtil.defaultPaint());
    }

    private void drawText(Canvas canvas, Object text, ChartPoint point){
        if(text == null){
            return;
        }
        canvas.drawText(PriceUtils.formatValueToText(text), point.getX() - 50, point.getY() - 30, PaintUtil.textPaint(this.getContext()));
    }

    public LineChart setData(List<ChartPoint> originalData) {
        this.points = originalData;
        return this;
    }

    public List<ChartPoint> getPoints() {
        return points;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int visiblePoints = 5;
        int parentWidht = this.getRootView().getWidth();
        float half = parentWidht / 2;
        width = (float) parentWidht;
        paddingX = half;
        space = (int) (width / visiblePoints);
        if (points != null) {
            this.width = calWidth(paddingX);
        }
        height = new Float(MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(this.width.intValue(), height.intValue());
        this.pagerListener.onPageSelected(this.position);
    }

    public Integer calcScroll(int position) {
        if(space == null){
            return null;
        }
        return (int) (space * position);
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

    private float minY() {
        float min = 1000000000000000f;
        for (ChartPoint point : this.points) {
            Double value = point.getOriginalValue();
            if (min > value.floatValue()) {
                min = value.floatValue();
            }
        }
        return min;
    }

    private void drawLine(Canvas canvas, ChartPoint initial, ChartPoint end) {
        canvas.drawLine(initial.getX(), initial.getY(), end.getX(), end.getY(), PaintUtil.defaultPaint());
    }

    @NonNull
    private void convertDataToPoints() {
        float max = maxY();
        float min = minY();
        float i = paddingX;
        ChartPoint old = new ChartPoint(0,this.height);
        for (ChartPoint point : points) {
            float yScreenPoint = discoverYScreenPoint(point.getOriginalValue(), max, min);
            point.setX(i).setY(addPaddingY(yScreenPoint));
            calcPath(old, point);
            old = point;
            i = i + space;
        }
    }

    private float addPaddingY(float y) {
        if (y < paddingY) {
            return y + paddingY;
        }

        if (y > height - paddingY + convertDpToPixel(10f)) {
            return y - paddingY;
        }

        return y;
    }

    private float discoverYScreenPoint(Double point, float maxValue, float min) {
        float less = convertDpToPixel(point.floatValue());
        maxValue = convertDpToPixel(maxValue);
        min = convertDpToPixel(min);
        float convertedHeight = height * (less - min) / (maxValue - min);
        return height  - convertedHeight;
    }

    private Float convertDpToPixel(Float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, this.getResources().getDisplayMetrics());
    }

    private float calWidth(float padding) {
        return ((points.size() - 1) * space) + (padding * 2);
    }

    public ChartPoint position(int position) {
        this.position = position;
        return this.points.get(position);
    }

    public LineChart scrollView(PagerGraphListener pagerListener){
        this.pagerListener = pagerListener;
        return this;
    }
}

