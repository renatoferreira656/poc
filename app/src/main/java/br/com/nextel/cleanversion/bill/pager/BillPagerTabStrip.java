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
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillPagerTabStrip extends HorizontalScrollView {
    private static final String TAG = "PagerTabStrip";
    private ViewPager viewPager;

    private Integer width;
    private LinearLayout linearLayout;

    public BillPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void addViews() {
        PagerAdapter adapter = this.viewPager.getAdapter();
        int width = this.width / 5;
        linearLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(linearLayout);

        linearLayout.addView(this.newText("", width));
        linearLayout.addView(this.newText("", width));
        for (int i =0; i<adapter.getCount(); i++){
            CharSequence text = adapter.getPageTitle(i);
            linearLayout.addView(this.newText(text, width));
        }
        linearLayout.addView(this.newText("", width));
        linearLayout.addView(this.newText("", width));
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

    public BillPagerTabStrip setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(40, 255, 255, 255));
        float halfHeight = getHeight() / 2;
        float half = (getWidth() / 2);
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
    private TextView newText(CharSequence text, int textWidth) {
        TextView child = new TextView(this.getContext());
        child.setText(text);
        child.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(textWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        child.setLayoutParams(lp);
        child.setGravity(Gravity.CENTER);
        return child;
    }

    public int scrollChild(int position) {
        if(this.linearLayout == null){
            return 0;
        }
        View childAt = this.linearLayout.getChildAt(position);
        if(childAt == null){
            return 0;
        }
        return childAt.getLeft();
    }
}
