package com.spiriev.android.mobacounter;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.Display;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int killsRadiant = 0;
    private int killsDire = 0;
    private Button[] buttonsRadiant;
    private Button[] buttonsDire;
    private final float DEFAULT_NUMBER_SIZE = 62.0f;
    private TextView killCounterViewRadiant;
    private TextView killCounterViewDire;
    private TextView radiantName;
    private TextView direName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface edisson = Typeface.createFromAsset(getAssets(), "Edisson.ttf");
        Typeface bremen = Typeface.createFromAsset(getAssets(), "BREMEN_3.TTF");
        killCounterViewRadiant = (TextView) findViewById(R.id.kill_counter_radiant_id);
        killCounterViewDire = (TextView) findViewById(R.id.kill_counter_dire_id);
        radiantName = (TextView) findViewById(R.id.nature_text_id);
        direName = (TextView) findViewById(R.id.monsters_text_id);

        killCounterViewRadiant.setTypeface(edisson);
        killCounterViewDire.setTypeface(edisson);
        radiantName.setTypeface(bremen);
        direName.setTypeface(bremen);

        buttonsRadiant = new Button[]{(Button) findViewById(R.id.plus_one_radiant_kill_id),
                (Button) findViewById(R.id.plus_three_radiant_kill_id),
                (Button) findViewById(R.id.team_radiant_kill_id),
                (Button) findViewById(R.id.reset_id)};

        buttonsDire = new Button[]{(Button) findViewById(R.id.plus_one_dire_kill_id),
                (Button) findViewById(R.id.plus_three_dire_kill_id),
                (Button) findViewById(R.id.team_dire_kill_id)};

        for (Button button : buttonsRadiant) {
            button.setTypeface(edisson);
            button.setOnClickListener(this);
        }
        for (Button button : buttonsDire) {
            button.setTypeface(edisson);
            button.setOnClickListener(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArray("killCounters", new String[] {killCounterViewRadiant.getText().toString(),
                                                              killCounterViewDire.getText().toString()});
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String[] killValues = savedInstanceState.getStringArray("killCounters");
        killCounterViewRadiant.setText(killValues[0]);
        killCounterViewDire.setText(killValues[1]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus_one_radiant_kill_id:
                increaseKillByOne(Color.BLUE, killCounterViewRadiant, buttonsRadiant, ++killsRadiant);
                break;
            case R.id.plus_three_radiant_kill_id:
                increaseKillByThree(Color.BLUE, killCounterViewRadiant, buttonsRadiant, killsRadiant += 3);
                break;
            case R.id.team_radiant_kill_id:
                increaseKillByFive(Color.BLUE, killCounterViewRadiant, buttonsRadiant, killsRadiant += 5);
                break;
            case R.id.reset_id:
                reset(killsRadiant = 0, killsDire = 0);
                break;
            case R.id.plus_one_dire_kill_id:
                increaseKillByOne(Color.RED, killCounterViewDire, buttonsDire, ++killsDire);
                break;
            case R.id.plus_three_dire_kill_id:
                increaseKillByThree(Color.RED, killCounterViewDire, buttonsDire, killsDire += 3);
                break;
            case R.id.team_dire_kill_id:
                increaseKillByFive(Color.RED, killCounterViewDire, buttonsDire, killsDire += 5);
                break;
        }
    }

    public void increaseKillByOne(int color, TextView view, Button[] buttons, int kills) {
        String killsString = "" + kills;
        view.setText(killsString);
        ObjectAnimator colorAnimator = createColorAnimator(color, view, 400L);
        AnimateCounter.animateCounter(buttons, colorAnimator);
    }

    public void increaseKillByThree(int color, TextView view, Button[] buttons, int kills) {
        String killsString = "" + kills;
        view.setText(killsString);
        ObjectAnimator colorAnimator = createColorAnimator(color, view, 400L);
        ObjectAnimator textSizeAnimator = createTextSizeAnimator(view, DEFAULT_NUMBER_SIZE, 400L,
                new FastOutSlowInInterpolator());
        AnimateCounter.animateCounter(buttons, colorAnimator, textSizeAnimator);
    }

    public void increaseKillByFive(int color, TextView view, Button[] buttons, int kills) {
        String killsString = "" + kills;
        view.setText(killsString);
        ObjectAnimator colorAnimator = createColorAnimator(color, view, 400L);
        ObjectAnimator textSizeAnimatorIn = createTextSizeAnimator(view, 82.0f, 300L,
                new LinearInterpolator());
        ObjectAnimator textSizeAnimatorOut = createTextSizeAnimator(view, DEFAULT_NUMBER_SIZE, 100L,
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
                object.setShadowLayer(24.0f, 0.0f, 0.0f, value);
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

    public void reset(int killsRadiant, int killsDire) {
        String killsDireString = "" + killsDire;
        String killsRadiantString = "" + killsRadiant;
        killCounterViewRadiant.setText(killsRadiantString);
        killCounterViewDire.setText(killsDireString);
    }
    /*
    private void centerTeamTextProper(TextView tv) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        tv.setPadding((width / 8), 0, 0, 0);

    }
    */
}
