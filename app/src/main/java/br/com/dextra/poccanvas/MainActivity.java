package br.com.dextra.poccanvas;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Fatura Digital");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6F00")));
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        List<ChartPoint> originalData = new ArrayList<>();
        originalData.add(new ChartPoint(300.0, ChartPoint.Status.PAID, "JUL"));
        originalData.add(new ChartPoint(100.0, ChartPoint.Status.PAID, "AGO"));
        originalData.add(new ChartPoint(200.0, ChartPoint.Status.OVERDUE, "SET"));
        originalData.add(new ChartPoint(230.0, ChartPoint.Status.PAID, "OUT"));
        originalData.add(new ChartPoint(180.0, ChartPoint.Status.PAID, "NOV"));
        originalData.add(new ChartPoint(350.0, ChartPoint.Status.PENDING, "DEZ"));
        lineChart.setData(originalData);

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), originalData);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }
}
