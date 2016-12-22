package br.com.nextel.cleanversion.bill.listener;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.LineChart;

/**
 * Created by renato.soares on 12/22/16.
 */
public class PagerGraphListener implements ViewPager.OnPageChangeListener, ViewTreeObserver.OnScrollChangedListener, View.OnTouchListener {
    private LineChart lineChart;
    private HorizontalScrollView scrollView;

    public PagerGraphListener(LineChart lineChart, HorizontalScrollView scrollView){
        this.lineChart = lineChart;
        this.scrollView = scrollView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int calcScroll = (int) (this.lineChart.padding() * position) - position * (this.lineChart.paddingScreen());
        scrollView.smoothScrollTo(calcScroll, 0);
    }

    @Override
    public void onScrollChanged() {
        int scrollX = scrollView.getScrollX();
        System.out.println("scroll - x: " + scrollX);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
    }
}
