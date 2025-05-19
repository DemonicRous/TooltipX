package com.demonicrous.tooltipx.enums;

import com.demonicrous.tooltipx.utils.Reference;

public enum EnumConfigFolder {
    CONFIG_FOLDER(Reference.CONFIG_FOLDER_PATH),
    CONFIG_FOLDER_RARITY(Reference.CONFIG_FOLDER_RARITY_PATH),
    CONFIG_FOLDER_TOOLTIPS(Reference.CONFIG_FOLDER_TOOLTIPS_PATH),
    CONFIG_FOLDER_TABS(Reference.CONFIG_FOLDER_TABS_PATH);

    public final String path;

    EnumConfigFolder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
