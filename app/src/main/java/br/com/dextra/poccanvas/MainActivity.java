package br.com.dextra.poccanvas;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

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
        final LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        final List<ChartPoint> originalData = new ArrayList<>();
        originalData.add(new ChartPoint(300.0, ChartPoint.Status.PAID, "JUL"));
        originalData.add(new ChartPoint(100.0, ChartPoint.Status.PAID, "AGO"));
        originalData.add(new ChartPoint(200.0, ChartPoint.Status.OVERDUE, "SET"));
        originalData.add(new ChartPoint(230.0, ChartPoint.Status.PAID, "OUT"));
        originalData.add(new ChartPoint(180.0, ChartPoint.Status.PAID, "NOV"));
        originalData.add(new ChartPoint(350.0, ChartPoint.Status.PENDING, "DEZ"));
        lineChart.setData(originalData);

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), lineChart.getPoints());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


        final HorizontalScrollView horiz = (HorizontalScrollView) findViewById(R.id.scroll_line_chart);
        horiz.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = horiz.getScrollX();
                System.out.println("scroll - x: " + scrollX);
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ChartPoint chartPoint = originalData.get(position);
                float max = maxX(originalData);
                int x = discoverScrollX(chartPoint.getX() - lineChart.padding(), max, lineChart.width());
                horiz.smoothScrollTo(x, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int discoverScrollX(float actual, float maxValue, float maxScroll){
        return (int) (actual * maxScroll / maxValue);
    }

    public float maxX(List<ChartPoint> originalData){
        float max = 0;
        for(ChartPoint point : originalData){
            if(max <  point.getX()){
                max = point.getX();
            }
        }
        return max;
    }
}
