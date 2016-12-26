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
        this.listener.touch(e.getX(), e.getY());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2 == null || e1 == null) {
            return true;
        }

        float diffX = e2.getX() - e1.getX();
        if (Math.abs(diffX) > SWIPE_THRESHOLD) {
            if (diffX > 0) {
                this.listener.onSwipeRight();
                return true;
            }
            this.listener.onSwipeLeft();
        }
        return true;
    }

    public interface GraphEventListener {
        void onSwipeRight();

        void onSwipeLeft();

        void touch(float x, float y);
    }
}