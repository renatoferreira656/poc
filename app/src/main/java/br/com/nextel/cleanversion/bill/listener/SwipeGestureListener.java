package br.com.nextel.cleanversion.bill.listener;

import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;

/**
 * Created by renato.soares on 12/26/16.
 */

public class SwipeGestureListener implements GestureListener.GraphEventListener, View.OnTouchListener {
    private ViewPager viewPager;
    private GestureDetector gestureDetector;

    public SwipeGestureListener(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.gestureDetector = new GestureDetector(viewPager.getContext(), new GestureListener(this));
    }

    public void onSwipeRight() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        }
    }

    public void onSwipeLeft() {
        if (viewPager.getCurrentItem() <= viewPager.getAdapter().getCount()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    @Override
    public void touch(float x, float y) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
    }
}
