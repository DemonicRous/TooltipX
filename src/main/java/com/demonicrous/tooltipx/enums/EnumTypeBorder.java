package com.demonicrous.tooltipx.enums;

public enum EnumTypeBorder {
    BORDER("border"),
    BORDER_ANIMATED("border_animated"),
    FRAME("frame"),
    FRAME_ANIMATED("frame_animated");

    EnumTypeBorder(String type) {
        this.type = type;
    }

    public final String type;

    public String getType() {
        return type;
    }
}
