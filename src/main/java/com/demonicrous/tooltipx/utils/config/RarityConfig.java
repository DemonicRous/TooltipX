package com.demonicrous.tooltipx.utils.config;

import com.google.gson.annotations.SerializedName;

public class RarityConfig {
    @SerializedName("active")
    private boolean active = true;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type = "border";

    @SerializedName("color")
    private String color = "#FFFFFF";

    @SerializedName("glow")
    private boolean glow = false;

    // Геттеры
    public boolean isActive() { return active; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getColor() { return color; }
    public boolean hasGlow() { return glow; }

    // Сеттеры
    public void setActive(boolean active) { this.active = active; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
    public void setColor(String color) { this.color = color; }
    public void setGlow(boolean glow) { this.glow = glow; }

    public static RarityConfig defaultExample(boolean active, String name,
                                              String description, String type) {
        RarityConfig config = new RarityConfig();
        config.setActive(active);
        config.setName(name);
        config.setDescription(description);
        config.setType(type);
        return config;
    }
}