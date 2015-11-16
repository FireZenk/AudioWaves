#Audio Waves

Shows a graphic representation of the sounds captured by the microphone on Android

![android_audio_waves](https://cloud.githubusercontent.com/assets/1595403/11171019/e7f21ebe-8be4-11e5-80ed-5d485dc46719.png)

###GRADLE:

	repositories {
	    	...
	    	maven { url 'https://github.com/FireZenk/maven-repo/raw/master/'}
	}
	dependencies {
			...
	        compile 'org.firezenk:audiowaves:1.1@aar'
	}

###USAGE:

1. Add permission to record audio (into your Manifest)
	
	````
	<uses-permission android:name="android.permission.RECORD_AUDIO"/>
	````

2. Add the new xmls to your layout parent widget

	````
	xmlns:aw="http://schemas.android.com/apk/res-auto"
	````
3. Add AudioWeaves to your layout and configure it

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
4. Start (and stop) the listener
	
	````
	((Visualizer) findViewById(R.id.visualizer)).startListening(); //to stop: .stopListening()
	````
5. (Optional) Also you can configure AudioWeaves by code
	
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

````
The MIT License (MIT)

Copyright (c) 2015 Jorge Garrido Oval <firezenk@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
````
