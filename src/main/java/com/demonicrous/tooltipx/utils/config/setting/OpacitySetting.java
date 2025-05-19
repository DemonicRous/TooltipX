package com.demonicrous.tooltipx.utils.config.setting;

import com.google.gson.annotations.SerializedName;

public class OpacitySetting {

    @SerializedName("opacity_border")
    private double opacityBorder = 1.0;

    @SerializedName("opacity_background")
    private double opacityBackground = 1.0;

    @SerializedName("opacity_text")
    private double opacityText = 1.0;

    public double getOpacityBorder() { return opacityBorder; }
    public double getOpacityBackground() { return opacityBackground; }
    public double getOpacityText() { return opacityText; }

    public OpacitySetting(double opacityBorder, double opacityBackground, double opacityText) {
        this.opacityBorder = opacityBorder;
        this.opacityBackground = opacityBackground;
        this.opacityText = opacityText;
    }

}
