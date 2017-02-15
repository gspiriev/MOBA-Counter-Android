package com.spiriev.android.mobacounter;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int kills = 0;
    private Button[] buttons;
    private final float DEFAULT_NUMBER_SIZE = 55.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface carribean = Typeface.createFromAsset(getAssets(), "Edisson.ttf");
        TextView killCounterViewRadiant = (TextView) findViewById(R.id.kill_counter_radiant_id);
        killCounterViewRadiant.setTypeface(carribean);

        buttons = new Button[]{(Button) findViewById(R.id.plus_one_radiant_kill_id),
                (Button) findViewById(R.id.plus_three_radiant_kill_id),
                (Button) findViewById(R.id.team_radiant_kill_id)};
    }

    public void increaseRadiantKillByOne(View view) {
        TextView killCounterView = (TextView) findViewById(R.id.kill_counter_radiant_id);
        killCounterView.setText("" + (++kills));
        ObjectAnimator colorAnimator = createColorAnimator(Color.RED, killCounterView, 400L);
        AnimateCounter.animateCounter(buttons, colorAnimator);
    }

    public void increaseRadiantKillByThree(View view) {
        TextView killCounterView = (TextView) findViewById(R.id.kill_counter_radiant_id);
        killCounterView.setText("" + (kills += 3));
        ObjectAnimator colorAnimator = createColorAnimator(Color.RED, killCounterView, 400L);
        ObjectAnimator textSizeAnimator = createTextSizeAnimator(killCounterView, DEFAULT_NUMBER_SIZE, 400L,
                new FastOutSlowInInterpolator());
        AnimateCounter.animateCounter(buttons, colorAnimator, textSizeAnimator);
    }

    public void increaseRadiantKillByFive(View view) {
        TextView killCounterView = (TextView) findViewById(R.id.kill_counter_radiant_id);
        killCounterView.setText("" + (kills += 5));
        ObjectAnimator colorAnimator = createColorAnimator(Color.RED, killCounterView, 400L);
        ObjectAnimator textSizeAnimatorIn = createTextSizeAnimator(killCounterView, 70.0f, 300L,
                new LinearInterpolator());
        ObjectAnimator textSizeAnimatorOut = createTextSizeAnimator(killCounterView, DEFAULT_NUMBER_SIZE, 100L,
                new LinearInterpolator());
        AnimateCounter.animateCounter(buttons, colorAnimator, textSizeAnimatorIn, textSizeAnimatorOut);
    }


    private ObjectAnimator createColorAnimator(int endColor, TextView killCounterView, long duration) {
        Property property = new Property<TextView, Integer>(int.class, "shadowColor") {
            @Override
            public Integer get(TextView object) {
                return object.getShadowColor();
            }

            @Override
            public void set(TextView object, Integer value) {
                object.setShadowLayer(20.0f, 0.0f, 0.0f, value);
            }
        };

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(killCounterView, property, endColor);

        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setInterpolator(new CycleInterpolator(0.5f));
        colorAnimator.setDuration(duration);

        return colorAnimator;
    }

    private ObjectAnimator createTextSizeAnimator(TextView killCounterView, float endSize,
                                                  long duration,
                                                  TimeInterpolator interpolator) {
        ObjectAnimator textSizeAnimator = ObjectAnimator.ofFloat(killCounterView,
                "textSize",
                endSize);
        textSizeAnimator.setEvaluator(new FloatEvaluator());
        textSizeAnimator.setInterpolator(interpolator);
        textSizeAnimator.setDuration(duration);
        return textSizeAnimator;
    }

    public void reset(View view) {
        TextView killCounterView = (TextView) findViewById(R.id.kill_counter_radiant_id);
        killCounterView.setText("" + (kills = 0));
    }
}
