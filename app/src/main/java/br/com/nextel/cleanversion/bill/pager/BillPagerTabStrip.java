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
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.nextel.cleanversion.bill.listener.PagerGraphListener;
import br.com.nextel.cleanversion.bill.listener.ScrollListener;

public class BillPagerTabStrip extends HorizontalScrollView {
    private static final String TAG = "PagerTabStrip";
    private ViewPager viewPager;

    private Integer width;
    private LinearLayout linearLayout;
    private int position;
    private PagerGraphListener pagerListener;
    private ScrollListener scrollListener;

    public BillPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void addViews() {
        linearLayout = new LinearLayout(this.getContext());
        PagerAdapter adapter = this.viewPager.getAdapter();
        int width = getTextWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(linearLayout);

        linearLayout.addView(this.newText("", width, -1));
        linearLayout.addView(this.newText("", width, -1));
        for (int i =0; i<adapter.getCount(); i++){
            CharSequence text = adapter.getPageTitle(i);
            linearLayout.addView(this.newText(text, width, i));
        }
        linearLayout.addView(this.newText("", width, -1));
        linearLayout.addView(this.newText("", width, -1));
    }

    private int getTextWidth() {
        return this.width / 5;
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
            this.scrollListener.tab(true);
        }
    }

    public BillPagerTabStrip setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(40, 255, 255, 255));
        float halfHeight = getHeight() / 2;
        float half = (getTextWidth() * (position + 2)) + (getTextWidth() / 2);
        int i = convertDpToPixel(40f).intValue();
        int i1 = convertDpToPixel(22f).intValue();
        int r = convertDpToPixel(30f).intValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(half - i, halfHeight - i + i1,half + i,halfHeight + i - i1, r, r,  paint);
        } else {
            canvas.drawRect(half - i, halfHeight - i + i1,half + i,halfHeight + i - i1,  paint);
        }
        invalidate();
    }

    private Float convertDpToPixel(Float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, this.getResources().getDisplayMetrics());
    }

    @NonNull
    private TextView newText(CharSequence text, int textWidth, final int pos) {
        TextView child = new TextView(this.getContext());
        child.setText(text);
        child.setTextColor(Color.WHITE);
        child.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.setCurrentItem(pos, true);
                return false;
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(textWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        child.setLayoutParams(lp);
        child.setGravity(Gravity.CENTER);
        return child;
    }

    public Integer scrollChild(int position) {
        if(this.linearLayout == null){
            return null;
        }
        View childAt = this.linearLayout.getChildAt(position);
        if(childAt == null){
            return this.width * position;
        }
        this.position = position;
        return getTextWidth() * position;
    }

    public void scrollLister(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }
}
