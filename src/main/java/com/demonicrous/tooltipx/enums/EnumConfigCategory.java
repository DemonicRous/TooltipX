package com.demonicrous.tooltipx.enums;

public enum EnumConfigCategory {
    GENERAL("General", "General settings for TooltipX mod"),
    APPEARANCE("Appearance", "Tooltip appearance settings"),
    POSITION("Position", "Tooltip position settings"),
    EXAMPLES("Examples", "Example files generation settings"),
    ADVANCED("Advanced", "Advanced settings");

    private String nameCategory;
    private String commentCategory;

    EnumConfigCategory(String nameCategory, String commentCategory) {
        this.nameCategory = nameCategory;
        this.commentCategory = commentCategory;
    }

    public String getName() {
        return nameCategory;
    }

    public String getComment() {
        return commentCategory;
    }

}
