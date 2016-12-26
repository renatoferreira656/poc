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

import br.com.nextel.cleanversion.bill.activity.BillHomeActivity;
import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.ChartPointStatus;
import br.com.nextel.cleanversion.bill.chart.LineChart;
import br.com.nextel.cleanversion.bill.pager.BillPagerTabStrip;

/**
 * Created by renato.soares on 12/22/16.
 */
public class PagerGraphListener implements ViewPager.OnPageChangeListener, ViewTreeObserver.OnScrollChangedListener,
        View.OnTouchListener, GraphEventListener {
    private GestureDetector gestureDetector;
    private BillPagerTabStrip headerTab;
    private BillHomeActivity activity;
    private ViewPager viewPager;
    private LineChart lineChart;
    private HorizontalScrollView scrollView;
    private Integer oldPosition;

    public PagerGraphListener(BillHomeActivity activity, ViewPager viewPager, LineChart lineChart,
                              HorizontalScrollView scrollView, BillPagerTabStrip strip) {
        this.activity = activity;
        this.viewPager = viewPager;
        this.lineChart = lineChart;
        this.scrollView = scrollView;
        this.gestureDetector = new GestureDetector(lineChart.getContext(), new GestureListener(this));
        this.headerTab = strip;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Integer calcScroll = this.lineChart.calcScroll(position);
        if(calcScroll == null){
            return;
        }
        ChartPoint point = this.lineChart.position(position);
        ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollX", calcScroll);
        animator.setDuration(400);
        animator.start();
        this.activity.changeDetails(point);
        if(oldPosition != null && position >= 0 && position < this.viewPager.getAdapter().getCount()){
            transition(this.lineChart.getPoints().get(this.oldPosition).status(), point.status());
        } else {
            transition(point.status(), point.status());
        }

        this.headerTab.smoothScrollTo(this.headerTab.scrollChild(position), 0);
        this.oldPosition = position;
    }

    public TransitionDrawable transition(ChartPointStatus from, ChartPointStatus to){
        Drawable[] drawable = new Drawable[2];
        drawable[0] = from.background(this.activity);
        drawable[1] = to.background(this.activity);
        TransitionDrawable transition = new TransitionDrawable(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.activity.graphHolder().setBackground(transition);
        } else{
            this.activity.graphHolder().setBackgroundDrawable(transition);
        }
        transition.startTransition(200);
        return transition;
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

    public void touch(float x, float y){
    }
}

