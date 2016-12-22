package br.com.nextel.cleanversion.bill.listener;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import br.com.nextel.cleanversion.bill.activity.MainActivity;
import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.ChartPointStatus;
import br.com.nextel.cleanversion.bill.chart.LineChart;

/**
 * Created by renato.soares on 12/22/16.
 */
public class LineChartListener implements View.OnTouchListener, GraphEventListener {
    private GestureDetector gestureDetector;
    private ViewPager viewPager;
    private LineChart lineChart;

    public LineChartListener( ViewPager viewPager, LineChart lineChart) {
        this.viewPager = viewPager;
        this.lineChart = lineChart;
        this.gestureDetector = new GestureDetector(lineChart.getContext(), new GestureListener(this));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
    }


    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void touch(float x, float y){
        int i = 0;
        for(ChartPoint point: this.lineChart.getPoints()){
            if(point.isInside(x, y)){
                viewPager.setCurrentItem(i, true);
                return;
            }
            i++;
        }
    }
}

