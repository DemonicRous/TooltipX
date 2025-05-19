package com.demonicrous.tooltipx.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.StatCollector;

import java.util.Map;

public class TabsUtils {

    public static Map<String, TabStyle> tabsMap = null; // <unlocalizedName, tabName>

    public static String getUnlocalizedNameFromTabName(String tabName) {
        // Перебираем все зарегистрированные CreativeTabs
        for (CreativeTabs tab : CreativeTabs.creativeTabArray) {
            if (tab != null) {
                // Получаем название вкладки (переведённое)
                String translatedTabName = StatCollector.translateToLocal(tab.getTranslatedTabLabel());
                // Если переведённое название совпадает с искомым, возвращаем unlocalizedName
                if (translatedTabName.equals(tabName)) {
                    return tab.getTranslatedTabLabel(); // или tab.getTabLabel(), зависит от версии
                }
            }
        }
        if (StatCollector.translateToLocal("inventory.binSlot").equals(tabName)) {
            return "inventory.binSlot";
        }
        return null; // Если вкладка не найдена
    }

    class TabStyle {

        private int colorBorder = 0;
        private int colorBackground = 0;
        private int colorTitle = 0;

        private int colorBorderStart = 0;
        private int colorBorderEnd = 0;
        private int colorBackgroundStart = 0;
        private int colorBackgroundEnd = 0;
        private int colorTitleStart = 0;
        private int colorTitleEnd = 0;

        private float opacityBorder = 0;
        private float opacityBackground = 0;
        private float opacityTitle = 0;

        public TabStyle(int colorBorder, int colorBackground, int colorTitle, float opacityBorder, float opacityBackground, float opacityTitle) {
            this.colorBorder = colorBorder;
            this.colorBackground = colorBackground;
            this.colorTitle = colorTitle;
        }

        public TabStyle(int colorBorderStart, int colorBorderEnd, int colorBackgroundStart, int colorBackgroundEnd, int colorTitleStart, int colorTitleEnd) {
            this.colorBorderStart = colorBorderStart;
            this.colorBorderEnd = colorBorderEnd;
            this.colorBackgroundStart = colorBackgroundStart;
            this.colorBackgroundEnd = colorBackgroundEnd;
            this.colorTitleStart = colorTitleStart;
            this.colorTitleEnd = colorTitleEnd;
        }

    }

}
