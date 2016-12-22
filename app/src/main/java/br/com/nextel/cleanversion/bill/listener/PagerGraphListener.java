package br.com.nextel.cleanversion.bill.listener;

import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import br.com.dextra.cleanversion.R;
import br.com.nextel.cleanversion.bill.activity.MainActivity;
import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.LineChart;

/**
 * Created by renato.soares on 12/22/16.
 */
public class PagerGraphListener implements ViewPager.OnPageChangeListener, ViewTreeObserver.OnScrollChangedListener, View.OnTouchListener {
    private GestureDetector gestureDetector;
    private MainActivity activity;
    private ViewPager viewPager;
    private LineChart lineChart;
    private HorizontalScrollView scrollView;

    public PagerGraphListener(MainActivity activity, ViewPager viewPager, LineChart lineChart, HorizontalScrollView scrollView) {
        this.activity = activity;
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
        Integer paddingViewPort = this.lineChart.paddingViewPort();
        Float padding = this.lineChart.padding();
        ChartPoint point = this.lineChart.position(position);
        if(paddingViewPort == null || padding == null){
            return;
        }
        int calcScroll = (int) (padding * position) - position * paddingViewPort;
        ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollX", calcScroll);
        animator.setDuration(400);
        animator.start();
        this.activity.changeDetails(point);


        if(point.status().equals(ChartPoint.Status.OVERDUE)){
            this.activity.graphHolder().setBackgroundResource(R.drawable.red_green_transition);
            TransitionDrawable transition = (TransitionDrawable) this.activity.graphHolder().getBackground();
            transition.startTransition(200);
        } else if(point.status().equals(ChartPoint.Status.PAID)){
            this.activity.graphHolder().setBackgroundResource(R.drawable.green_green_transition);
            TransitionDrawable transition = (TransitionDrawable) this.activity.graphHolder().getBackground();
            transition.startTransition(200);
        } else if(point.status().equals(ChartPoint.Status.PENDING)) {
            this.activity.graphHolder().setBackgroundResource(R.drawable.yellow_green_transition);
            TransitionDrawable transition = (TransitionDrawable) this.activity.graphHolder().getBackground();
            transition.startTransition(200);
        }
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
        if (viewPager.getCurrentItem() <= viewPager.getAdapter().getCount()) {
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
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                    return true;
                }
                onSwipeLeft();
            }
            return true;
        }
    }
}
