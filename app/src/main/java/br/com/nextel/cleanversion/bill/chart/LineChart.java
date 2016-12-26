package br.com.nextel.cleanversion.bill.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.nextel.cleanversion.bill.util.CanvasUtil;
import br.com.nextel.cleanversion.bill.listener.ScrollListener;
import br.com.nextel.cleanversion.bill.util.PaintUtil;

public class LineChart extends View {

    private final Float paddingY = CanvasUtil.convertDpToPixel(LineChart.this.getContext(), 25f);
    private Float paddingX;

    private List<ChartPoint> points = new ArrayList<>();
    private Integer space = 40;
    private Float width;
    private Float height;

    private Integer circleRadius = CanvasUtil.convertDpToPixel(LineChart.this.getContext(), 10f).intValue();
    private int position;
    private float radiusPosition = 0;
    private boolean open = true;

    List<Path> paths = new ArrayList<Path>();
    private Paint paintStrokePath;
    private Paint paintFillPath;

    private boolean notFill = false;
    private ScrollListener scrollListener;
    private CanvasUtil.PulsePoint pulsePoint;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        PaintUtil.setStrokeWidth(CanvasUtil.convertDpToPixel(LineChart.this.getContext(),2.5f).intValue());

        paintFillPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillPath.setStrokeWidth(CanvasUtil.convertDpToPixel(LineChart.this.getContext(),10f));
        paintFillPath.setStyle(Paint.Style.FILL);

        paintStrokePath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillPath.setStrokeWidth(CanvasUtil.convertDpToPixel(LineChart.this.getContext(), 10f));
        paintStrokePath.setStyle(Paint.Style.STROKE);
        pulsePoint = new CanvasUtil.PulsePoint(this.circleRadius);
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
                    CanvasUtil.drawText(this.getContext(), canvas, last.getOriginalValue(), last);
                }
                continue;
            }
            ChartPoint point = it.next();
            if(position != i) {
                CanvasUtil.drawText(this.getContext(), canvas, point.getOriginalValue(), point);
            }
            CanvasUtil.drawLine(canvas, last, point);
            last = point;
        }
    }

    private void drawAllCircles(Canvas canvas) {
        int i= 0;
        for(ChartPoint point : points){
            if(position == i) {
                CanvasUtil.pulseCircle(canvas, point, pulsePoint);
            }
            CanvasUtil.drawCircle(this.getContext(), canvas, point, this.circleRadius);
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

    public LineChart setData(List<ChartPoint> originalData) {
        this.points = originalData;
        return this;
    }

    public List<ChartPoint> getPoints() {
        return points;
    }

    public ChartPoint position(int position) {
        this.position = position;
        return this.points.get(position);
    }

    public void scrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int visiblePoints = 5;
        int parentWidht = this.getRootView().getWidth();
        float half = parentWidht / 2;
        width = (float) parentWidht;
        if(width < 1){
            return;
        }
        paddingX = half;
        space = (int) (width / visiblePoints);
        if (points != null) {
            this.width = calWidth(paddingX);
        }
        height = new Float(MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(this.width.intValue(), height.intValue());
        this.scrollListener.graph(true);
    }

    public Integer calcScroll(int position) {
        if(space == null){
            return null;
        }
        return (int) (space * position);
    }

    @NonNull
    private void convertDataToPoints() {
        float max = ChartPoint.maxY(points);
        float min = ChartPoint.minY(points);
        float i = paddingX;
        ChartPoint old = new ChartPoint(0,this.height);
        for (ChartPoint point : points) {
            float yScreenPoint = CanvasUtil.discoverYScreenPoint(this.getContext(), height, point.getOriginalValue(), max, min);
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

        if (y > height - paddingY + CanvasUtil.convertDpToPixel(this.getContext(), 10f)) {
            return y - paddingY;
        }
        return y;
    }

    private float calWidth(float padding) {
        return ((points.size() - 1) * space) + (padding * 2);
    }

}

