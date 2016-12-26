package br.com.nextel.cleanversion.bill.util;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.view.View;
import android.widget.HorizontalScrollView;

import br.com.nextel.cleanversion.bill.chart.ChartPointStatus;

/**
 * Created by renato.soares on 12/26/16.
 */

public class AnimationUtil {
    public static void animateScroll(HorizontalScrollView scrollView, Integer xPosition) {
        ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollX", xPosition);
        animator.setDuration(20);
        animator.start();
    }

    public static TransitionDrawable transition(View view, ChartPointStatus from, ChartPointStatus to){
        Drawable[] drawable = new Drawable[2];
        drawable[0] = from.background(view.getContext());
        drawable[1] = to.background(view.getContext());
        TransitionDrawable transition = new TransitionDrawable(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(transition);
        } else{
            view.setBackgroundDrawable(transition);
        }
        transition.startTransition(200);
        return transition;
    }
}
