package br.com.nextel.cleanversion.bill.pager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;

public class BillPagerTabStrip extends PagerTabStrip {
    private static final String TAG = "PagerTabStrip";


    public BillPagerTabStrip(Context context) {
        this(context, null);
    }

    public BillPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(40, 255, 255, 255));
        final int height = getHeight();

        float halfHeight = height / 2;

        int width = getWidth();
        float half = (width / 2);


        int i = 80;
        int i1 = 45;
        int r = 60;
        canvas.drawRoundRect(half - i, halfHeight - i + i1,half + i,halfHeight + i - i1, r, r,  paint);
    }
}