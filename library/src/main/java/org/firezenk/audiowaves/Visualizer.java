package org.firezenk.audiowaves;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * Created by firezenk on 14/11/15.
 */
public class Visualizer extends LinearLayout implements IVisualizer {

    private int FORMAT = 0;
    private int VISUALIZER_WIDTH = 100;
    private int VISUALIZER_HEIGHT = 200;
    private int VISUALIZER_NUM_WAVES = 15;
    private int VISUALIZER_GRAVITY = 0;

    private int LINE_WIDTH = 20;
    private int LINE_MIN_WIDTH = 20;
    private int LINE_HEIGHT = 20;
    private int LINE_MIN_HEIGHT = 20;
    private int LINE_SPACING = 10;
    private int LINE_BORDER_RADIUS = 50;
    private int BALL_DIAMETER = 20;

    private int COLOR_UNIFORM = android.R.color.black;
    private boolean COLOR_IS_GRADIENT = false;
    private int COLOR_GRADIENT_START = android.R.color.white;
    private int COLOR_GRADIENT_END = android.R.color.black;

    private Context context;
    private Random randomNum = new Random();
    private Handler uiThread = new Handler();
    private LinearLayout.LayoutParams params;
    private ArrayList<View> waveList = new ArrayList<>();
    private Thread listeningThread;

    public Visualizer(Context context) {
        super(context);
        this.context = context;
        this.init();
    }

    public Visualizer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        this.attributes(attrs);
        this.init();
    }

    public Visualizer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        this.attributes(attrs);
        this.init();
    }

    private void attributes(AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.audiowaves__style,
                0, 0);

        try {
            FORMAT = a.getInteger(R.styleable.audiowaves__style_aw_format, FORMAT);
            VISUALIZER_HEIGHT = a.getInteger(R.styleable.audiowaves__style_aw_height, VISUALIZER_HEIGHT);
            VISUALIZER_WIDTH = a.getInteger(R.styleable.audiowaves__style_aw_width, VISUALIZER_WIDTH);
            VISUALIZER_NUM_WAVES = a.getInteger(R.styleable.audiowaves__style_aw_num_waves, VISUALIZER_NUM_WAVES);
            VISUALIZER_GRAVITY = a.getInteger(R.styleable.audiowaves__style_aw_gravity, VISUALIZER_GRAVITY);

            LINE_WIDTH = a.getInteger(R.styleable.audiowaves__style_aw_line_with, LINE_WIDTH);
            LINE_MIN_WIDTH = a.getInteger(R.styleable.audiowaves__style_aw_line_min_with, LINE_MIN_WIDTH);
            LINE_HEIGHT = a.getInteger(R.styleable.audiowaves__style_aw_line_height, LINE_HEIGHT);
            LINE_MIN_HEIGHT = a.getInteger(R.styleable.audiowaves__style_aw_line_min_height, LINE_MIN_HEIGHT);
            LINE_SPACING = a.getInteger(R.styleable.audiowaves__style_aw_line_spacing, LINE_SPACING);
            LINE_BORDER_RADIUS = a.getInteger(R.styleable.audiowaves__style_aw_line_border_radius, LINE_BORDER_RADIUS);
            BALL_DIAMETER = a.getInteger(R.styleable.audiowaves__style_aw_ball_diameter, BALL_DIAMETER);

            COLOR_UNIFORM = a.getColor(R.styleable.audiowaves__style_aw_color_uniform, getResources().getColor(COLOR_UNIFORM));
            COLOR_IS_GRADIENT = a.getBoolean(R.styleable.audiowaves__style_aw_color_is_gradient, COLOR_IS_GRADIENT);
            COLOR_GRADIENT_START = a.getColor(R.styleable.audiowaves__style_aw_color_gradient_start, getResources().getColor(COLOR_GRADIENT_START));
            COLOR_GRADIENT_END = a.getColor(R.styleable.audiowaves__style_aw_color_gradient_end, getResources().getColor(COLOR_GRADIENT_END));
        } finally {
            a.recycle();
        }
    }

    private void init() {
        this.setLayoutParams(
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        switch (FORMAT) {
            case 0:
            case 1:
                this.setOrientation(VERTICAL);
                break;
            case 2:
            case 3:
                this.setOrientation(HORIZONTAL);
                break;
        }

        switch (VISUALIZER_GRAVITY) {
            case 0:
                this.setGravity(Gravity.CENTER);
                break;
            case 1:
                this.setGravity(Gravity.CENTER_VERTICAL|Gravity.START);
                break;
            case 2:
                this.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
                break;
            case 3:
                this.setGravity(Gravity.CENTER_VERTICAL|Gravity.END);
                break;
            case 4:
                this.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
                break;
        }

        this.addWaves();
        this.prepare();
    }

    private void addWaves() {
        params = new LinearLayout.LayoutParams(LINE_MIN_WIDTH, LINE_MIN_HEIGHT);
        params.setMargins(LINE_SPACING, 0, LINE_SPACING, 0);

        for (int i = 0; i < VISUALIZER_NUM_WAVES; i++) {
            View v = new View(context);
            v.setLayoutParams(params);
            this.setBackground(v);
            waveList.add(v);
            this.addView(v);
        }
    }

    private void setBackground(View v) {
        if (android.os.Build.VERSION.SDK_INT < 16)
            setBackgroundAPI15(v);
        else
            setBackgroundAPI16(v);
    }

    @TargetApi(15) private void setBackgroundAPI15(View v) {
        GradientDrawable gd = null;

        if (COLOR_IS_GRADIENT) {
            switch (FORMAT) {
                case 0:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.LEFT_RIGHT,
                            new int[]{COLOR_GRADIENT_START, COLOR_GRADIENT_END});
                    gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                    break;
                case 2:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{COLOR_GRADIENT_START, COLOR_GRADIENT_END});
                    gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                    break;
                case 1:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.LEFT_RIGHT,
                            new int[]{COLOR_GRADIENT_START, COLOR_GRADIENT_END});
                    gd.setGradientType(GradientDrawable.OVAL);
                    break;
                case 3:
                    gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{COLOR_GRADIENT_START, COLOR_GRADIENT_END});
                    gd.setGradientType(GradientDrawable.OVAL);
                    break;
            }
        } else {
            gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{COLOR_UNIFORM, COLOR_UNIFORM});
        }

        gd.setCornerRadius(LINE_BORDER_RADIUS);
        gd.setGradientRadius(90.f);
        v.setBackgroundDrawable(gd);
    }

    @TargetApi(16) private void setBackgroundAPI16(View v) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(LINE_BORDER_RADIUS);
        gd.setGradientRadius(90.f);

        if (COLOR_IS_GRADIENT) {
            gd.setColors(new int[]{COLOR_GRADIENT_START, COLOR_GRADIENT_END});

            switch (FORMAT) {
                case 0:
                    gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    break;
                case 2:
                    gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                    break;
                case 1:
                    gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    break;
                case 3:
                    gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    gd.setGradientType(GradientDrawable.OVAL);
                    break;
            }
        } else {
            gd.setColors(new int[]{COLOR_UNIFORM, COLOR_UNIFORM});
        }
        v.setBackground(gd);
    }

    private void prepare() {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();
                uiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        int pitch =  pitchInHz > 0 ? (int) pitchInHz : 1;

                        for (int i = 0; i < waveList.size(); i++) {
                            int random = randomNum.nextInt(10 - 1) + 1;
                            int size = pitch/random;

                            switch (FORMAT) {
                                case 0:
                                    params = new LinearLayout.LayoutParams(
                                            size < LINE_MIN_WIDTH ? LINE_MIN_WIDTH : size,
                                            LINE_HEIGHT);
                                    params.setMargins(0, LINE_SPACING, 0, LINE_SPACING);
                                    break;
                                case 1:
                                    params = new LinearLayout.LayoutParams(
                                            size < LINE_MIN_WIDTH ? LINE_MIN_WIDTH : size/2,
                                            size < LINE_MIN_HEIGHT ? LINE_MIN_HEIGHT : size/2);
                                    params.setMargins(0, LINE_SPACING, 0, LINE_SPACING);
                                    break;
                                case 2:
                                    params = new LinearLayout.LayoutParams(
                                            LINE_WIDTH,
                                            size < LINE_MIN_HEIGHT ? LINE_MIN_HEIGHT : size);
                                    params.setMargins(LINE_SPACING, 0, LINE_SPACING, 0);
                                    break;
                                case 3:
                                    params = new LinearLayout.LayoutParams(
                                            size < LINE_MIN_WIDTH ? LINE_MIN_WIDTH : size/2,
                                            size < LINE_MIN_HEIGHT ? LINE_MIN_HEIGHT : size/2);
                                    params.setMargins(LINE_SPACING, 0, LINE_SPACING, 0);
                                    break;
                            }

                            waveList.get(i).setLayoutParams(params);
                        }
                    }
                });
            }
        };

        AudioProcessor p = new PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        listeningThread = new Thread(dispatcher);
    }

    @Override
    public void startListening() {
        listeningThread.start();
    }

    @Override
    public void stopListening() {
        listeningThread.interrupt();
    }

    @Override public void setFormat(int format) {
        this.FORMAT = format;
    }

    @Override public void setDimens(int width, int height) {
        this.VISUALIZER_WIDTH = width;
        this.VISUALIZER_HEIGHT = height;
    }

    @Override public void setWidth(int width) {
        this.VISUALIZER_WIDTH = width;
    }

    @Override public void setHeight(int height) {
        this.VISUALIZER_HEIGHT = height;
    }

    @Override public void setNumWaves(int waves) {
        this.VISUALIZER_NUM_WAVES = waves;
    }

    @Override public void setLineDimens(int width, int height) {
        this.LINE_WIDTH = width;
        this.LINE_HEIGHT = height;
    }

    @Override public void setLineWidth(int width) {
        this.LINE_WIDTH = width;
    }

    @Override public void setLineHeight(int height) {
        this.LINE_HEIGHT = height;
    }

    @Override public void setLineMinDimens(int minWidth, int minHeight) {
        this.LINE_MIN_WIDTH = minWidth;
        this.LINE_MIN_HEIGHT = minHeight;
    }

    @Override public void setLineMinWidth(int minWidth) {
        this.LINE_MIN_WIDTH = minWidth;
    }

    @Override public void setLineMinHeight(int minHeight) {
        this.LINE_MIN_HEIGHT = minHeight;
    }

    @Override public void setLineSpacing(int spacing) {
        this.LINE_SPACING = spacing;
    }

    @Override public void setLineBorderRadius(int borderRadius) {
        this.LINE_BORDER_RADIUS = borderRadius;
    }

    @Override public void setBallDiameter(int ballDiameter) {
        this.BALL_DIAMETER = ballDiameter;
    }

    @Override public void setColor(int color) {
        this.COLOR_UNIFORM = color;
    }

    @Override public void setIsGradient(boolean isGradient) {
        this.COLOR_IS_GRADIENT = isGradient;
    }

    @Override public void setGradient(int startColor, int endColor) {
        this.COLOR_GRADIENT_START = startColor;
        this.COLOR_GRADIENT_END = endColor;
    }

    @Override public void setGradientColorStart(int color) {
        this.COLOR_GRADIENT_START = color;
    }

    @Override public void setGradientColorEnd(int color) {
        this.COLOR_GRADIENT_END = color;
    }
}
