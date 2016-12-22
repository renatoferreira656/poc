package br.com.nextel.cleanversion.bill.listener;


import br.com.nextel.cleanversion.bill.chart.ChartPoint;

public interface GraphEventListener {
    public void onSwipeRight();
    public void onSwipeLeft();
    public void touch(float x, float y);
}
