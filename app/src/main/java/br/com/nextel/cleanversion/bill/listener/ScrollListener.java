package br.com.nextel.cleanversion.bill.listener;

import android.support.v4.view.ViewPager;

/**
 * Created by renato.soares on 12/26/16.
 */

public class ScrollListener {

    private Boolean graphMeasured = false;
    private Boolean tabMeasured  = false;
    private ViewPager viewPager;
    private Integer position;

    public ScrollListener(ViewPager viewPager,Integer position){
        this.viewPager = viewPager;
        this.position = position - 1;
    }

    public void graph(Boolean graph){
        graphMeasured = graph;
        update();
    }

    private void update() {
        if(graphMeasured && this.tabMeasured){
            viewPager.setCurrentItem(this.position);
        }
    }

    public void tab(Boolean graph){
        tabMeasured = graph;
        update();
    }
}
