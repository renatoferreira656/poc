package br.com.nextel.cleanversion.bill.listener;

import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;

/**
 * Created by renato.soares on 12/26/16.
 */
public class ChartGestureListener implements GestureListener.GraphEventListener, View.OnTouchListener {

    private ViewPager viewPager;
    private GestureDetector gestureDetector;
    private List<ChartPoint> points;

    public ChartGestureListener(ViewPager viewPager, List<ChartPoint> points) {
        this.viewPager = viewPager;
        this.gestureDetector = new GestureDetector(viewPager.getContext(), new GestureListener(this));
        this.points = points;
    }


    @Override
    public void onSwipeRight() {
    }

    @Override
    public void onSwipeLeft() {
    }

    @Override
    public void touch(float x, float y) {
        int i = 0;
        for(ChartPoint point: this.points){
            if(point.isInside(x, y)){
                viewPager.setCurrentItem(i, true);
                return;
            }
            i++;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
    }

}
