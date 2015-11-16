package org.firezenk.audiowaves;

/**
 * Created by firezenk on 15/11/15.
 */
public interface IVisualizer {

    void startListening();

    void stopListening();

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

}
