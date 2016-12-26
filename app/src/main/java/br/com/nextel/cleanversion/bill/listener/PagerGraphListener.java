package br.com.nextel.cleanversion.bill.listener;

import android.support.v4.view.ViewPager;
import android.widget.HorizontalScrollView;

import br.com.nextel.cleanversion.bill.activity.BillHomeActivity;
import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.LineChart;
import br.com.nextel.cleanversion.bill.pager.BillPagerTabStrip;
import br.com.nextel.cleanversion.bill.util.AnimationUtil;

public class PagerGraphListener implements ViewPager.OnPageChangeListener {
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
        this.headerTab = strip;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(final int position) {
        Integer chartXPosition = this.lineChart.calcScroll(position);
        Integer tabXPosition = this.headerTab.scrollChild(position);
        if (chartXPosition == null || tabXPosition == null) {
            return;
        }
        ChartPoint point = this.lineChart.position(position);
        AnimationUtil.animateScroll(this.scrollView, chartXPosition);
        AnimationUtil.animateScroll(this.headerTab, tabXPosition);
        this.activity.changeDetails(point);
        if (oldPosition != null && position >= 0 && position < this.viewPager.getAdapter().getCount()) {
            AnimationUtil.transition(this.activity.graphHolder(), this.lineChart.getPoints().get(this.oldPosition).status(), point.status());
        } else {
            AnimationUtil.transition(this.activity.graphHolder(), point.status(), point.status());
        }

        this.oldPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

