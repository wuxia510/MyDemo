package com.wuxia.mydemo.utils;

import android.animation.Animator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * 作者： Administrator on 2017/11/9 0009 12:43
 * 邮箱：1137451819@qq.com
 * 说明：
 */
public class CircleAnimateUtils {
    public static void handleAnimate(final View animateView) {

        // 显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * createCircularReveal 方法参数
             * view 执行动画的view
             * centerX 圆心横坐标
             * centerY 圆心纵坐标
             * startRadius 动画开始时圆的半径
             * endRadius 动画结束时圆的半径
             */
            final Animator animator = ViewAnimationUtils.createCircularReveal(animateView,
                    animateView.getWidth() / 2,
                    animateView.getHeight() / 2,
                    0,
                    (float) Math.hypot(animateView.getWidth(), animateView.getHeight()));
            // Math.hypot确定圆的半径（算长宽的斜边长，这样半径不会太短也不会很长效果比较舒服）

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    animateView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setDuration(5000);
            animator.start();
        } else {
            animateView.setVisibility(View.VISIBLE);
        }
        animateView.setEnabled(true);

//        Animator animator = CircularRevealLayout.Builder.on(animateView)
//                .centerX(animateView.getWidth() / 2)
//                .centerY(animateView.getHeight() / 2)
//                .startRadius(0)
//                .endRadius((float) Math.hypot(animateView.getWidth(), animateView.getHeight()))
//                .create();
//
//        animator.setDuration(5000);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                animateView.setVisibility(View.VISIBLE);
//                super.onAnimationStart(animation);
//            }
//        });
//        animator.start();
    }
}
