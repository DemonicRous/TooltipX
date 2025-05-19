package com.demonicrous.tooltipx.utils.config;

import com.demonicrous.tooltipx.utils.config.setting.ColorSetting;
import com.google.gson.annotations.SerializedName;

public class TabConfig {
    @SerializedName("active")
    private boolean active = true;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type = "border";

    @SerializedName("unlocalized_name")
    private String unlocalizedName = "itemGroup.default";

    @SerializedName("icon")
    private String icon = "minecraft:stone";

    @SerializedName("text")
    private ColorSetting colorTextSetting;

    @SerializedName("border")
    private ColorSetting colorBorderSetting;

    // Геттеры
    public boolean isActive() { return active; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getUnlocalizedName() { return unlocalizedName; }
    public String getIcon() { return icon; }
    public ColorSetting getColorTextSetting() { return colorTextSetting; }
    public ColorSetting getColorBorderSetting() { return colorBorderSetting; }

    // Сеттеры
    public void setActive(boolean active) { this.active = active; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
    public void setUnlocalizedName(String unlocalizedName) { this.unlocalizedName = unlocalizedName; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setColorTextSetting(ColorSetting colorSetting) { this.colorTextSetting = colorSetting; }
    public void setColorBorderSetting(ColorSetting colorSetting) { this.colorBorderSetting = colorSetting; }

    public static TabConfig defaultExample(boolean active, String name,
                                           String description, String type, String unlocalizedName, String icon) {
        TabConfig config = new TabConfig();
        config.setActive(active);
        config.setName(name);
        config.setDescription(description);
        config.setType(type);
        config.setUnlocalizedName(unlocalizedName);
        config.setIcon(icon);
        config.setColorTextSetting(new ColorSetting().createTextDefault());
        config.setColorBorderSetting(new ColorSetting().createBorderDefault());
        return config;
    }
}