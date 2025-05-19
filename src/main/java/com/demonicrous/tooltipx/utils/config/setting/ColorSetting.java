package com.demonicrous.tooltipx.utils.config.setting;

import com.google.gson.annotations.SerializedName;

public class ColorSetting {
    @SerializedName("color")
    private String color; // HEX формат (#RRGGBB)

    @SerializedName("gradient_start")
    private String gradientStart; // HEX формат

    @SerializedName("gradient_end")
    private String gradientEnd; // HEX формат

    @SerializedName("opacity")
    private float opacity = 1.0f;

    @SerializedName("use_gradient")
    private boolean useGradient = false;

    // Геттеры и сеттеры
    public String getColor() { return color; }
    public String getGradientStart() { return gradientStart; }
    public String getGradientEnd() { return gradientEnd; }
    public float getOpacity() { return opacity; }
    public boolean useGradient() { return useGradient; }

    public static ColorSetting createBorderDefault() {
        ColorSetting settings = new ColorSetting();
        settings.color = "#FFFFFF";
        settings.gradientStart = "#FFFFFF";
        settings.gradientEnd = "#AAAAAA";
        settings.opacity = 0.8f;
        settings.useGradient = false;
        return settings;
    }

    public static ColorSetting createTextDefault() {
        ColorSetting settings = new ColorSetting();
        settings.color = "#FFFFFF";
        settings.gradientStart = "#FFAA00";
        settings.gradientEnd = "#FFFF55";
        settings.opacity = 1.0f;
        settings.useGradient = false;
        return settings;
    }
}