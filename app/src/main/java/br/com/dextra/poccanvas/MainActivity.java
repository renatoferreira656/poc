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
        List<Double> originalData = new ArrayList<>();
        originalData.add(125.0);
        originalData.add(30.0);
        originalData.add(20.0);
        originalData.add(50.0);
        lineChart.setData(originalData);
    }
}
