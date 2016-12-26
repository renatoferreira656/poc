package br.com.nextel.cleanversion.bill.pager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillPagerTabStrip extends HorizontalScrollView {
    private static final String TAG = "PagerTabStrip";
    private ViewPager viewPager;

    private final PageListener mPageListener = new PageListener();
    private Integer width;

    public BillPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void addViews() {
        PagerAdapter adapter = this.viewPager.getAdapter();
        int width = this.width / 5;
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        System.out.println("franguinho" + width);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(linearLayout);

        for (int i =0; i<adapter.getCount(); i++){
            CharSequence text = adapter.getPageTitle(i);
            linearLayout.addView(this.newText(text, width));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(widthMeasureSpec <= 0){
            return;
        }
        if(width == null){
            width = (MeasureSpec.getSize(widthMeasureSpec));
            addViews();
        }
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

    @NonNull
    private TextView newText(CharSequence text, int textWidth) {
        TextView child = new TextView(this.getContext());
        child.setText(text);
        child.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(textWidth, LinearLayout.LayoutParams.MATCH_PARENT);
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
