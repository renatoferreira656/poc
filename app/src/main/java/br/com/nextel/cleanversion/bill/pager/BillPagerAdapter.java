package br.com.nextel.cleanversion.bill.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import br.com.nextel.cleanversion.bill.chart.ChartPoint;
import br.com.nextel.cleanversion.bill.fragment.BillPagerFragment;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class BillPagerAdapter extends FragmentStatePagerAdapter {
    private List<ChartPoint> points;

    public BillPagerAdapter(FragmentManager fm, List<ChartPoint> points) {
        super(fm);
        this.points = points;
    }

    @Override
    public Fragment getItem(int i) {
        ChartPoint chartPoint = this.points.get(i);
        if(chartPoint == null){
            return new BillPagerFragment();
        }
        return new BillPagerFragment().init(chartPoint);
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
