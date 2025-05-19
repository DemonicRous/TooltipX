package com.demonicrous.tooltipx.utils;

public class ColorUtils {

    /**
     * Конвертирует HEX-строку цвета в int с указанной прозрачностью
     * @param hex HEX-строка цвета (форматы: "#RRGGBB", "RRGGBB", "#RGB", "RGB")
     * @param opacity Уровень прозрачности (0.0f - полностью прозрачный, 1.0f - полностью непрозрачный)
     * @return Цвет в формате ARGB (32-bit)
     * @throws IllegalArgumentException Если HEX-строка некорректна или opacity вне диапазона
     */
    public static int hexToColorWithOpacity(String hex, double opacity) {
        // Проверка корректности opacity
        if (opacity < 0f || opacity > 1f) {
            throw new IllegalArgumentException("Opacity must be between 0.0 and 1.0");
        }

        // Удаляем символ # если есть
        hex = hex.replace("#", "");

        // Проверяем длину HEX-строки
        if (hex.length() != 3 && hex.length() != 6) {
            throw new IllegalArgumentException("Invalid HEX color format. Expected #RRGGBB, RRGGBB, #RGB or RGB");
        }

        // Расширяем короткий формат (#RGB) до полного (#RRGGBB)
        if (hex.length() == 3) {
            hex = "" + hex.charAt(0) + hex.charAt(0) +
                    hex.charAt(1) + hex.charAt(1) +
                    hex.charAt(2) + hex.charAt(2);
        }

        try {
            // Парсим компоненты цвета
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);

            // Рассчитываем alpha-канал (0-255)
            int a = (int)(opacity * 255);

            // Комбинируем в ARGB (32-bit)
            return (a << 24) | (r << 16) | (g << 8) | b;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid HEX color: " + hex, e);
        }
    }

}
