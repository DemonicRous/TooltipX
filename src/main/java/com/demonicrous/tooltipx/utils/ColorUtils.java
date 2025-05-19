package com.demonicrous.tooltipx.utils;

public class ColorUtils {

    /**
     * Конвертирует HEX-строку цвета в int с указанной прозрачностью
     * @param hex HEX-строка цвета (форматы: "#RRGGBB", "RRGGBB", "#RGB", "RGB")
     * @param opacity Уровень прозрачности (0.0f - полностью прозрачный, 1.0f - полностью непрозрачный)
     * @return Цвет в формате ARGB (32-bit)
     * @throws IllegalArgumentException Если HEX-строка некорректна или opacity вне диапазона
     */
//    public static int hexToColorWithOpacity(String hex, double opacity) {
//        // Проверка корректности opacity
//        if (opacity < 0f || opacity > 1f) {
//            throw new IllegalArgumentException("Opacity must be between 0.0 and 1.0");
//        }
//
//        // Удаляем символ # если есть
//        hex = hex.replace("#", "");
//
//        // Проверяем длину HEX-строки
//        if (hex.length() != 3 && hex.length() != 6) {
//            throw new IllegalArgumentException("Invalid HEX color format. Expected #RRGGBB, RRGGBB, #RGB or RGB");
//        }
//
//        // Расширяем короткий формат (#RGB) до полного (#RRGGBB)
//        if (hex.length() == 3) {
//            hex = "" + hex.charAt(0) + hex.charAt(0) +
//                    hex.charAt(1) + hex.charAt(1) +
//                    hex.charAt(2) + hex.charAt(2);
//        }
//
//        try {
//            // Парсим компоненты цвета
//            int r = Integer.parseInt(hex.substring(0, 2), 16);
//            int g = Integer.parseInt(hex.substring(2, 4), 16);
//            int b = Integer.parseInt(hex.substring(4, 6), 16);
//
//            // Рассчитываем alpha-канал (0-255)
//            int a = (int)(opacity * 255);
//
//            // Комбинируем в ARGB (32-bit)
//            return (a << 24) | (r << 16) | (g << 8) | b;
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Invalid HEX color: " + hex, e);
//        }
//    }

    /**
     * Конвертирует HEX-строку в цвет с прозрачностью
     * @param hex HEX-строка (#RGB, #RRGGBB, #AARRGGBB)
     * @param opacity Прозрачность 0.0-1.0 (игнорируется если есть альфа-канал)
     * @return Цвет в формате 0xAARRGGBB (как int)
     * @throws IllegalArgumentException При неверном формате
     */
    public static int hexToColor(String hex, double opacity) {
        if (opacity < 0.0 || opacity > 1.0) {
            throw new IllegalArgumentException("Opacity must be between 0.0 and 1.0");
        }

        hex = hex.replace("#", "");

        switch (hex.length()) {
            case 3: // #RGB → 0xFFRRGGBB
                hex = "" + hex.charAt(0) + hex.charAt(0)
                        + hex.charAt(1) + hex.charAt(1)
                        + hex.charAt(2) + hex.charAt(2);
                break;
            case 6: // #RRGGBB → 0xFFRRGGBB
                break;
            case 8: // #AARRGGBB → 0xAARRGGBB
                return parseHexWithAlpha(hex);
            default:
                throw new IllegalArgumentException("Invalid HEX format. Use #RGB, #RRGGBB or #AARRGGBB");
        }

        return parseHexWithoutAlpha(hex, opacity);
    }

    private static int parseHexWithAlpha(String hex) {
        try {
            int a = Integer.parseInt(hex.substring(0, 2), 16);
            int r = Integer.parseInt(hex.substring(2, 4), 16);
            int g = Integer.parseInt(hex.substring(4, 6), 16);
            int b = Integer.parseInt(hex.substring(6, 8), 16);
            return (a << 24) | (r << 16) | (g << 8) | b;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid HEX color: " + hex, e);
        }
    }

    private static int parseHexWithoutAlpha(String hex, double opacity) {
        try {
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            int a = (int)(opacity * 255);
            return (a << 24) | (r << 16) | (g << 8) | b;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid HEX color: " + hex, e);
        }
    }

    /**
     * Преобразует цвет в строку формата 0xRRGGBB или 0xAARRGGBB
     * @param color Цвет в формате 0xAARRGGBB
     * @return Строковое представление
     */
    public static String colorToHexString(int color) {
        if ((color >> 24) == 0xFF) {
            return String.format("0x%06X", color & 0xFFFFFF);
        }
        return String.format("0x%08X", color);
    }

    /**
     * Разделяет цвет на компоненты
     * @param color Цвет в формате 0xAARRGGBB
     * @return Массив [a, r, g, b] (0-255)
     */
    public static int[] splitColor(int color) {
        return new int[] {
                (color >> 24) & 0xFF,
                (color >> 16) & 0xFF,
                (color >> 8) & 0xFF,
                color & 0xFF
        };
    }

    /**
     * Применяет прозрачность к существующему цвету
     * @param color Исходный цвет (0xAARRGGBB)
     * @param opacity Новая прозрачность (0.0-1.0)
     * @return Новый цвет с обновлённой прозрачностью
     */
    public static int applyOpacity(int color, float opacity) {
        int a = (int)(opacity * 255);
        return (a << 24) | (color & 0x00FFFFFF);
    }
}

