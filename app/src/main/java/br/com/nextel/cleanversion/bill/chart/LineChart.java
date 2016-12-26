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

    private Float paddingX;

    private List<ChartPoint> points = new ArrayList<>();
    private Float space = 40f;
    private Float width;
    private Float height;

    private Integer circleRadius;
    private int position;

    List<Path> paths = new ArrayList<Path>();
    private Paint paintStrokePath;
    private Paint paintFillPath;

    private boolean notFill = false;
    private ScrollListener scrollListener;
    private CanvasUtil.PulsePoint pulsePoint;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        CanvasUtil.context(context);
        circleRadius = CanvasUtil.convertDpToPixel(10f).intValue();

        PaintUtil.setStrokeWidth(CanvasUtil.convertDpToPixel(2.5f).intValue());

        paintFillPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillPath.setStrokeWidth(CanvasUtil.convertDpToPixel(10f));
        paintFillPath.setStyle(Paint.Style.FILL);

        paintStrokePath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillPath.setStrokeWidth(CanvasUtil.convertDpToPixel(10f));
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
        CanvasUtil.drawLines(canvas, this.points, this.position);
        drawPath(canvas);
        drawAllCircles(canvas);
        invalidate();
    }

    public Integer calcScroll(int position) {
        if(space == null){
            return null;
        }
        return (int) (space * position);
    }


    private void drawAllCircles(Canvas canvas) {
        int i= 0;
        for(ChartPoint point : points){
            if(position == i) {
                CanvasUtil.pulseCircle(canvas, point, pulsePoint);
            }
            CanvasUtil.drawCircle(canvas, point, this.circleRadius);
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
        space = (width / visiblePoints);
        if (points != null) {
            this.width = calWidth(paddingX);
        }
        height = new Float(MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(this.width.intValue(), height.intValue());
        this.scrollListener.graph(true);
        CanvasUtil.convertDataToPoints(points, space, paddingX, height);
        this.paths = CanvasUtil.calcPaths(height, points);
    }


    private float calWidth(float padding) {
        return ((points.size() - 1) * space) + (padding * 2);
    }

}

