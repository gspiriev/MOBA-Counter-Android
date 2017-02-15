package com.spiriev.android.mobacounter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.Button;

/**
 * Created by h3moglob1n on 2/14/17.
 */

public class AnimateCounter {


    public static void animateCounter(final Button[] killButtons, ObjectAnimator colorAnimator,
                                      ObjectAnimator testSizeAnimatorIn,
                                      ObjectAnimator textSizeAnimatorOut) {
        AnimatorSet counterViewSet = new AnimatorSet();
        counterViewSet.play(textSizeAnimatorOut).after(testSizeAnimatorIn);
        counterViewSet.play(colorAnimator).after(textSizeAnimatorOut);
        counterViewSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (Button killButton: killButtons) {
                    killButton.setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                for (Button killButton: killButtons) {
                    killButton.setEnabled(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        counterViewSet.start();
    }

    public static void animateCounter(final Button[] killButtons, ObjectAnimator colorAnimator) {
        colorAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (Button killButton: killButtons) {
                    killButton.setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                for (Button killButton: killButtons) {
                    killButton.setEnabled(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        colorAnimator.start();
    }

    public static void animateCounter(final Button[] killButtons, ObjectAnimator colorAnimator,
                                      ObjectAnimator textSizeAnimator) {
        AnimatorSet counterViewSet = new AnimatorSet();
        //counterViewSet.play(colorAnimator).after(textSizeAnimator);
        counterViewSet.playTogether(textSizeAnimator, colorAnimator);
        colorAnimator.setStartDelay(230L);
        counterViewSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (Button killButton: killButtons) {
                    killButton.setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                for (Button killButton: killButtons) {
                    killButton.setEnabled(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        counterViewSet.start();
    }
}
