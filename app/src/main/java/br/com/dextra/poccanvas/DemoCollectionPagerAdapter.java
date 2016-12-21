package br.com.dextra.poccanvas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    private List<ChartPoint> points;

    public DemoCollectionPagerAdapter(FragmentManager fm, List<ChartPoint>  points) {
        super(fm);
        this.points = points;
    }

    @Override
    public Fragment getItem(int i) {
        ChartPoint chartPoint = this.points.get(i);
        if(chartPoint == null){
            return new DemoObjectFragment();
        }
        return new DemoObjectFragment().init(chartPoint);
    }

    @Override
    public int getCount() {
        return this.points.size();
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return this.points.get(i).getTitle();
    }
}
