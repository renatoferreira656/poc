package br.com.nextel.cleanversion.bill.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

public final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 60;
    private GraphEventListener listener;

    public GestureListener(GraphEventListener touch) {
        this.listener = touch;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return this.listener.touch(e.getX(), e.getY());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2 == null || e1 == null) {
            return false;
        }

        float diffX = e2.getX() - e1.getX();
        if (Math.abs(diffX) > SWIPE_THRESHOLD) {
            if (diffX > 0) {
                return this.listener.onSwipeRight();
            }
            return this.listener.onSwipeLeft();
        }
        return false;
    }

    public interface GraphEventListener {
        boolean onSwipeRight();

        boolean onSwipeLeft();

        boolean touch(float x, float y);
    }
}