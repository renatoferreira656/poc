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
import br.com.nextel.cleanversion.bill.fragment.BillDescriptionFragment;
import br.com.nextel.cleanversion.bill.listener.PagerGraphListener;
import br.com.nextel.cleanversion.bill.pager.BillPagerAdapter;
import br.com.nextel.cleanversion.bill.chart.LineChart;
import br.com.dextra.cleanversion.R;

public class MainActivity extends AppCompatActivity {

    private View graphHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphHolderView = findViewById(R.id.bill_graph_holder);
        getSupportActionBar().setTitle("Fatura Digital");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6F00")));
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.setData(hardCodedData());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        HorizontalScrollView horiz = (HorizontalScrollView) findViewById(R.id.scroll_line_chart);
        PagerGraphListener listener = new PagerGraphListener(this, mViewPager, lineChart, horiz);
        horiz.getViewTreeObserver().addOnScrollChangedListener(listener);
        horiz.setOnTouchListener(listener);

        BillPagerAdapter pagerAdpater = new BillPagerAdapter(getSupportFragmentManager(), lineChart.getPoints());
        mViewPager.setAdapter(pagerAdpater);
        mViewPager.addOnPageChangeListener(listener);
        mViewPager.setCurrentItem(pagerAdpater.getCount());

        lineChart.scrollView(listener);
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
