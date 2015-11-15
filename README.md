#Audio Waves

Shows a graphic representation of the sounds captured by the microphone on Android

###GRADLE:

	repositories {
	    	...
	    	maven { url 'https://github.com/FireZenk/maven-repo/raw/master/'}
	}
	dependencies {
			...
	        compile 'org.firezenk:audiowaves:1.0'
	}

###USAGE:

1. Add the new xmls to your layout parent widget

	````
	xmlns:aw="http://schemas.android.com/apk/res-auto"
	````
2. Add AudioWeaves to your layout and configure it

	````
	<org.firezenk.audiowaves.Visualizer
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        aw:aw_format="vertical_lines"
        aw:aw_gravity="center"
        aw:aw_width="100"
        aw:aw_height="200"
        aw:aw_num_waves="10"
        aw:aw_line_with="30"
        aw:aw_line_min_with="30"
        aw:aw_line_height="30"
        aw:aw_line_min_height="30"
        aw:aw_line_spacing="10"
        aw:aw_line_border_radius="50"
        aw:aw_ball_diameter="30"
        aw:aw_color_uniform="@color/YOUR_COLOR_HERE"
        aw:aw_color_is_gradient="true"
        aw:aw_color_gradient_start="@color/YOUR_START_COLOR"
        aw:aw_color_gradient_end="@color/YOUR_END_COLOR" />
	````
3. Also you can configure AudioWeaves by code
	
	````
	void setFormat(int format);

    void setGravity(int gravity);

    void setDimens(int width, int height);

    void setWidth(int width);

    void setHeight(int height);

    void setNumWaves(int waves);

    void setLineDimens(int width, int height);

    void setLineWidth(int width);

    void setLineHeight(int height);

    void setLineMinDimens(int minWidth, int minHeight);

    void setLineMinWidth(int minWidth);

    void setLineMinHeight(int minHeight);

    void setLineSpacing(int spacing);

    void setLineBorderRadius(int borderRadius);

    void setBallDiameter(int ballDiameter);

    void setColor(int color);

    void setIsGradient(boolean isGradient);

    void setGradient(int startColor, int endColor);

    void setGradientColorStart(int color);

    void setGradientColorEnd(int color);
	````

###MORE INFO:

	Go to sample module

###LICENSE

<code id="gist-4f1d9c0ba5cc8657b40d"></code>
