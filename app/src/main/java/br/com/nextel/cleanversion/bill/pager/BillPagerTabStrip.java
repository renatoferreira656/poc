package br.com.nextel.cleanversion.bill.pager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillPagerTabStrip extends LinearLayout {
    private static final String TAG = "PagerTabStrip";
    private ViewPager viewPager;

    private final PageListener mPageListener = new PageListener();

    public BillPagerTabStrip(Context context) {
        this(context, null);
    }

    public BillPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateText(0);
    }


    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(40, 255, 255, 255));
        final int height = getHeight();

        float halfHeight = height / 2;

        int width = getWidth();
        float half = (width / 2);


        int i = convertDpToPixel(40f).intValue();
        int i1 = convertDpToPixel(22f).intValue();
        int r = convertDpToPixel(30f).intValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(half - i, halfHeight - i + i1,half + i,halfHeight + i - i1, r, r,  paint);
        } else {
            canvas.drawRect(half - i, halfHeight - i + i1,half + i,halfHeight + i - i1,  paint);
        }
    }

    private Float convertDpToPixel(Float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, this.getResources().getDisplayMetrics());
    }

    public void updateText(int position){
        this.addView(newText("A"));
        this.addView(newText("C"));
        this.addView(newText("D"));
        this.addView(newText("E"));
        this.addView(newText("F"));
    }

    @NonNull
    private TextView newText(String text) {
        TextView child = new TextView(this.getContext());
        child.setText(text);
        child.setTextColor(Color.WHITE);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 5;
        child.setLayoutParams(lp);
        child.setGravity(Gravity.CENTER);
        return child;
    }

    private class PageListener implements  ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
