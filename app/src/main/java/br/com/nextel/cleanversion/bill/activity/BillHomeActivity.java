package br.com.nextel.cleanversion.bill.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.chart.ChartPointStatus;
import br.com.nextel.cleanversion.bill.fragment.BillDescriptionFragment;
import br.com.nextel.cleanversion.bill.listener.LineChartListener;
import br.com.nextel.cleanversion.bill.listener.PagerGraphListener;
import br.com.nextel.cleanversion.bill.listener.ScrollListener;
import br.com.nextel.cleanversion.bill.pager.BillPagerAdapter;
import br.com.nextel.cleanversion.bill.chart.LineChart;
import br.com.dextra.cleanversion.R;
import br.com.nextel.cleanversion.bill.pager.BillPagerTabStrip;

public class BillHomeActivity extends AppCompatActivity {

    private View graphHolderView;
    private LineChart lineChart;
    private ViewPager mViewPager;
    private BillPagerTabStrip tabStrip;
    private HorizontalScrollView chartHorizontal;
    private PagerGraphListener pagerGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        graphHolderView = findViewById(R.id.bill_graph_holder);

        getSupportActionBar().setTitle("Fatura Digital");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6F00")));
        lineChart = (LineChart) findViewById(R.id.line_chart);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabStrip = (BillPagerTabStrip) findViewById(R.id.pager_title_strip);
        chartHorizontal = (HorizontalScrollView) findViewById(R.id.scroll_line_chart);


        initTabStrip();
        configureDataChart();
        configureViewPager();
        ScrollListener scrollListener = new ScrollListener(mViewPager, mViewPager.getAdapter().getCount());
        lineChart.scrollListener(scrollListener);
        tabStrip.scrollLister(scrollListener);
    }

    private void configureDataChart() {
        lineChart.setOnTouchListener(new LineChartListener(mViewPager, lineChart));
        lineChart.setData(hardCodedData());
        chartHorizontal.getViewTreeObserver().addOnScrollChangedListener(getChartListener());
        chartHorizontal.setOnTouchListener(getChartListener());
    }

    private void initTabStrip() {
        tabStrip.setOnTouchListener(getChartListener());
        tabStrip.setViewPager(mViewPager);
    }

    private void configureViewPager() {
        BillPagerAdapter pagerAdpater = new BillPagerAdapter(getSupportFragmentManager(), lineChart.getPoints());
        mViewPager.setAdapter(pagerAdpater);
        mViewPager.addOnPageChangeListener(getChartListener());
    }

    @NonNull
    private PagerGraphListener getChartListener() {
        if (pagerGraph == null) {
            pagerGraph = new PagerGraphListener(this, mViewPager, lineChart, chartHorizontal, tabStrip);
        }
        return pagerGraph;
    }

    @NonNull
    private List<ChartPoint> hardCodedData() {
        List<ChartPoint> originalData = new ArrayList<>();
        originalData.add(new ChartPoint(130.0, ChartPointStatus.PAID, "JUL"));
        originalData.add(new ChartPoint(132.0, ChartPointStatus.PAID, "AGO"));
        originalData.add(new ChartPoint(125.1, ChartPointStatus.OVERDUE, "SET"));
        originalData.add(new ChartPoint(122.0, ChartPointStatus.PAID, "OUT"));
        originalData.add(new ChartPoint(140.0, ChartPointStatus.OVERDUE, "NOV"));
        originalData.add(new ChartPoint(110.0, ChartPointStatus.PENDING, "DEZ"));
        return originalData;
    }


    public void changeDetails(ChartPoint point) {
        BillDescriptionFragment billDescriptionFragment = new BillDescriptionFragment().init(point);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.bill_description, billDescriptionFragment);
        transaction.commit();
    }

    public View graphHolder() {
        return graphHolderView;
    }
}
