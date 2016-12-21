package br.com.dextra.poccanvas;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Fatura Digital");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6F00")));
        LineChart lineChart =  (LineChart) findViewById(R.id.line_chart);
        List<ChartPoint> originalData = new ArrayList<>();
        originalData.add(new ChartPoint(100.0 , ChartPoint.Status.PAID));
        originalData.add(new ChartPoint(200.0 , ChartPoint.Status.OVERDUE));
        originalData.add(new ChartPoint(230.0 , ChartPoint.Status.PAID));
        originalData.add(new ChartPoint(180.0 , ChartPoint.Status.PAID));
        originalData.add(new ChartPoint(350.0 , ChartPoint.Status.PENDING));
        lineChart.setData(originalData);
    }
}
