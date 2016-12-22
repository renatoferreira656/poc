package br.com.nextel.cleanversion.bill.listener;

import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import br.com.nextel.cleanversion.bill.chart.LineChart;

/**
 * Created by renato.soares on 12/22/16.
 */
public class PagerGraphListener implements ViewPager.OnPageChangeListener, ViewTreeObserver.OnScrollChangedListener, View.OnTouchListener {
    private GestureDetector gestureDetector;
    private ViewPager viewPager;
    private LineChart lineChart;
    private HorizontalScrollView scrollView;

    public PagerGraphListener(ViewPager viewPager, LineChart lineChart, HorizontalScrollView scrollView) {
        this.viewPager = viewPager;
        this.lineChart = lineChart;
        this.scrollView = scrollView;
        this.gestureDetector = new GestureDetector(lineChart.getContext(), new GestureListener());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int calcScroll = (int) (this.lineChart.padding() * position) - position * (this.lineChart.paddingScreen());
        ObjectAnimator animator=ObjectAnimator.ofInt(scrollView, "scrollX", calcScroll);
        animator.setDuration(400);
        animator.start();
//        scrollView.smoothScrollTo(calcScroll, 0);
    }

    @Override
    public void onScrollChanged() {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
    }


    public void onSwipeRight() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        }
    }

    public void onSwipeLeft() {
        if (viewPager.getCurrentItem() <= viewPager.getChildCount()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 60;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
}
