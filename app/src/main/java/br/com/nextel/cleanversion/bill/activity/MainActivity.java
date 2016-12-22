package br.com.nextel.cleanversion.bill.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.listener.PagerGraphListener;
import br.com.nextel.cleanversion.bill.pager.BillPagerAdapter;
import br.com.nextel.cleanversion.bill.chart.LineChart;
import br.com.dextra.cleanversion.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Fatura Digital");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6F00")));
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.setData(hardCodedData());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);

        HorizontalScrollView horiz = (HorizontalScrollView) findViewById(R.id.scroll_line_chart);
        PagerGraphListener listener = new PagerGraphListener(mViewPager, lineChart, horiz);
        horiz.getViewTreeObserver().addOnScrollChangedListener(listener);
        horiz.setOnTouchListener(listener);

        BillPagerAdapter mDemoCollectionPagerAdapter = new BillPagerAdapter(getSupportFragmentManager(), lineChart.getPoints());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.addOnPageChangeListener(listener);
    }

    @NonNull
    private List<ChartPoint> hardCodedData() {
        List<ChartPoint> originalData = new ArrayList<>();
        originalData.add(new ChartPoint(300.0, ChartPoint.Status.PAID, "JUL"));
        originalData.add(new ChartPoint(100.0, ChartPoint.Status.PAID, "AGO"));
        originalData.add(new ChartPoint(200.0, ChartPoint.Status.OVERDUE, "SET"));
        originalData.add(new ChartPoint(230.0, ChartPoint.Status.PAID, "OUT"));
        originalData.add(new ChartPoint(180.0, ChartPoint.Status.PAID, "NOV"));
        originalData.add(new ChartPoint(150.0, ChartPoint.Status.PENDING, "DEZ"));
        originalData.add(new ChartPoint(50.0, ChartPoint.Status.PENDING, "JAN"));
        originalData.add(new ChartPoint(10.0, ChartPoint.Status.PENDING, "FEV"));
        originalData.add(new ChartPoint(90.0, ChartPoint.Status.PENDING, "MAR"));
        return originalData;
    }


}
