package br.com.nextel.cleanversion.bill.listener;

import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.LineChart;

/**
 * Created by renato.soares on 12/22/16.
 */
public class TabStripListener implements View.OnTouchListener, GraphEventListener {

    public TabStripListener() {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
    }


    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void touch(float x, float y){
    }
}

