package com.demonicrous.tooltipx.utils.config;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TooltipConfig {
    @SerializedName("active")
    private boolean active = true;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type = "border";

    @SerializedName("items")
    private List<String> items;

    // Геттеры
    public boolean isActive() { return active; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public List<String> getItems() { return items; }

    // Сеттеры
    public void setActive(boolean active) { this.active = active; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
    public void setItems(List<String> items) { this.items = items; }

    public static TooltipConfig defaultExample(boolean active, String name,
                                               String description, String type,
                                               List<String> items) {
        TooltipConfig config = new TooltipConfig();
        config.setActive(active);
        config.setName(name);
        config.setDescription(description);
        config.setType(type);
        config.setItems(items);
        return config;
    }
}