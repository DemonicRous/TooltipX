package com.demonicrous.tooltipx.mixins.impl.client;

import com.demonicrous.tooltipx.TooltipX;
import com.demonicrous.tooltipx.utils.ColorUtils;
import com.demonicrous.tooltipx.utils.TabsUtils;
import com.demonicrous.tooltipx.utils.config.TabConfig;
import com.demonicrous.tooltipx.utils.json.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen extends Gui {

    @Shadow protected static RenderItem itemRender;

    @Shadow protected FontRenderer fontRendererObj;

    @Shadow protected int height;

    @Shadow protected int width;

    @Shadow protected Minecraft mc;

    @Unique private ItemStack itemStack = null;

    /**
     * @author DemonicRose
     * @reason This entire class is a complete overwrite, so we don't need to call the super method.
     * @param itemIn The item to render a tooltip for.
     * @param x The x-coordinate of the tooltip.
     * @param y The y-coordinate of the tooltip.
     */
    @Overwrite(remap = false)
    protected void renderToolTip(ItemStack itemIn, int x, int y) {

        List<String> textLines = itemIn.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        itemStack = itemIn;

        for(int lineIndex = 0; lineIndex < textLines.size(); ++lineIndex) {
            if (lineIndex == 0) {
                textLines.set(lineIndex, itemIn.getRarity().rarityColor + textLines.get(lineIndex));
            } else {
                textLines.set(lineIndex, EnumChatFormatting.GRAY + textLines.get(lineIndex));
            }
        }

        FontRenderer font = itemIn.getItem().getFontRenderer(itemIn);
        this.drawHoveringText(textLines, x, y, font == null ? this.fontRendererObj : font);

    }

    /**
     * Renders a tooltip for a creative tab at the given mouse coordinates.
     * This method is a complete overwrite, and does not call the super method.
     * @author DemonicRuse
     * @reason This entire class is a complete overwrite, so we don't need to call the super method.
     * @param tabName The name of the tab for which to render the tooltip.
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     */
    @Overwrite(remap = false)
    protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
        String unlocalizedName = TabsUtils.getUnlocalizedNameFromTabName(tabName);
        TooltipX.logger.info("UnlocalizedName: {}", unlocalizedName);

        TabConfig config = JsonParser.tabConfigMap.get(unlocalizedName);
        //TooltipX.logger.info("Config: {}", config.getIcon());
        if (config != null && config.isActive()) {
            // Применить стиль из конфига
            List<String> textLines = Arrays.asList(
                    config.getName(),
                    config.getDescription()
            );
            TooltipX.logger.info("TextLines: {}", textLines.toString());
            this.drawCreativeTabHoveringText(textLines, mouseX, mouseY, this.fontRendererObj, config);
        } else {
            // Стандартный рендер
            this.drawHoveringText(Collections.singletonList(tabName), mouseX, mouseY, this.fontRendererObj);
        }


    }

    @Unique
    private void drawCreativeTabHoveringText(List<String> textLines, int mouseX, int mouseY, FontRenderer fontRendererObj, TabConfig config) {
        if (!textLines.isEmpty()) {
            glDisable(GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);

            int maxWidth = 0;
            for (String textLine : textLines) {
                int lineWidth = fontRendererObj.getStringWidth(textLine);
                if (lineWidth > maxWidth) {
                    maxWidth = lineWidth;
                }
            }

            int tooltipX = mouseX + 12;
            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;
            if (textLines.size() > 1) {
                tooltipHeight += 2 + (textLines.size() - 1) * 10;
            }

            if (tooltipX + maxWidth > this.width) {
                tooltipX -= 28 + maxWidth;
            }

            if (tooltipY + tooltipHeight + 6 > this.height) {
                tooltipY = this.height - tooltipHeight - 6;
            }

            this.zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int backgroundColor = 0xCC000000; // Черный цвет фона с прозрачностью
            int borderColorStart = 0x80FFFFFF; // Начальный цвет рамки
            int borderColorEnd = 0x00000000;   // Конечный цвет рамки

            // Отрисовка фона и рамки
            drawGradientRect(tooltipX - 3, tooltipY - 4, tooltipX + maxWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX + maxWidth + 3, tooltipY - 3, tooltipX + maxWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(tooltipX + maxWidth + 2, tooltipY - 3 + 1, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + maxWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

            // Отрисовка текста с градиентом
            for (int i = 0; i < textLines.size(); ++i) {
                String text = textLines.get(i);
                int textX = tooltipX + maxWidth / 2 - fontRendererObj.getStringWidth(text) / 2;
                fontRendererObj.drawString(text, textX, tooltipY, 0xFFFFFF);

                TooltipX.logger.info("Color HEX: {}, Color: {}", config.getColorTextSetting().getColor(), ColorUtils.hexToColor(config.getColorTextSetting().getColor(), config.getColorTextSetting().getOpacity()));
                TooltipX.logger.info(ColorUtils.hexToColor("#FF0000", 1.0) );

                if (i == 0) {
                    tooltipY += 2;
                }
                tooltipY += 10;
            }

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            glEnable(GL_LIGHTING);
            glEnable(GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            glEnable(GL_RESCALE_NORMAL);
        }
    }



    /**
     * Renders a tooltip at the given coordinates, with the given text lines.
     * This is a complete overwrite, and does not call the super method.
     * @author DemonicRous
     * @reason This entire class is a complete overwrite, so we don't need to call the super method.
     * @param textLines The lines of text to render in the tooltip.
     * @param x The x-coordinate of the tooltip.
     * @param y The y-coordinate of the tooltip.
     * @param font The font to use for rendering the text.
     */
    @Overwrite(remap = false)
    protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
        if (!textLines.isEmpty()) {
            glDisable(GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);

            int maxWidth = 0;
            for (String textLine : textLines) {
                int lineWidth = font.getStringWidth(textLine);
                if (lineWidth > maxWidth) {
                    maxWidth = lineWidth;
                }
            }

            int tooltipX = x + 12;
            int tooltipY = y - 12;
            int tooltipHeight = 8;
            if (textLines.size() > 1) {
                tooltipHeight += 2 + (textLines.size() - 1) * 10;
            }

            if (tooltipX + maxWidth > this.width) {
                tooltipX -= 28 + maxWidth;
            }

            if (tooltipY + tooltipHeight + 6 > this.height) {
                tooltipY = this.height - tooltipHeight - 6;
            }

            this.zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int backgroundColor = 0xCC000000; // Черный цвет фона с прозрачностью
            int borderColorStart = 0x80FFFFFF; // Начальный цвет рамки
            int borderColorEnd = 0x00000000;   // Конечный цвет рамки

            // Отрисовка фона и рамки
            drawGradientRect(tooltipX - 3, tooltipY - 4, tooltipX + maxWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX + maxWidth + 3, tooltipY - 3, tooltipX + maxWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(tooltipX + maxWidth + 2, tooltipY - 3 + 1, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + maxWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

            // Отрисовка текста с градиентом
            for (int i = 0; i < textLines.size(); ++i) {
                String text = textLines.get(i);
                int textX = tooltipX + maxWidth / 2 - font.getStringWidth(text) / 2;
                font.drawStringWithShadow(text, textX, tooltipY, 0xFFFFFF);

                if (i == 0) {
                    tooltipY += 2;
                }
                tooltipY += 10;
            }

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            glEnable(GL_LIGHTING);
            glEnable(GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            glEnable(GL_RESCALE_NORMAL);
        }
    }


//    @Shadow protected static RenderItem itemRender;
//
//    @Shadow public int height;
//
//    @Shadow public int width;
//
//    @Overwrite(remap = false)
//    protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
//        if (!textLines.isEmpty()) {
//            glDisable(GL_RESCALE_NORMAL);
//            RenderHelper.disableStandardItemLighting();
//            glDisable(GL_LIGHTING);
//            glDisable(GL_DEPTH_TEST);
//
//            int maxWidth = 0;
//            for (String textLine : textLines) {
//                int lineWidth = font.getStringWidth(textLine);
//                if (lineWidth > maxWidth) {
//                    maxWidth = lineWidth;
//                }
//            }
//
//            int tooltipX = x + 12;
//            int tooltipY = y - 12;
//            int tooltipHeight = 8;
//            if (textLines.size() > 1) {
//                tooltipHeight += 2 + (textLines.size() - 1) * 10;
//            }
//
//            if (tooltipX + maxWidth > this.width) {
//                tooltipX -= 28 + maxWidth;
//            }
//
//            if (tooltipY + tooltipHeight + 6 > this.height) {
//                tooltipY = this.height - tooltipHeight - 6;
//            }
//
//            this.zLevel = 300.0F;
//            itemRender.zLevel = 300.0F;
//            int backgroundColor = 0xCC000000; // Черный цвет фона с прозрачностью
//            int borderColorStart = 0x80FFFFFF; // Начальный цвет рамки
//            int borderColorEnd = 0x00000000;   // Конечный цвет рамки
//
//            // Отрисовка фона и рамки
//            drawGradientRect(tooltipX - 3, tooltipY - 4, tooltipX + maxWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
//            drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
//            drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
//            drawGradientRect(tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
//            drawGradientRect(tooltipX + maxWidth + 3, tooltipY - 3, tooltipX + maxWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
//            drawGradientRect(tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
//            drawGradientRect(tooltipX + maxWidth + 2, tooltipY - 3 + 1, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
//            drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + maxWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
//            drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + maxWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);
//
//            // Отрисовка текста с градиентом
//            for (int i = 0; i < textLines.size(); ++i) {
//                String text = removeFormattingCodes(textLines.get(i));
//                int textX = tooltipX + maxWidth / 2 - font.getStringWidth(text) / 2;
//                drawGradientString(font, text, textX, tooltipY, 0xFFFFAA00, 0xFF55FFFF); // Оранжевый в светло-голубой
//
//                if (i == 0) {
//                    tooltipY += 2;
//                }
//                tooltipY += 10;
//            }
//
//            this.zLevel = 0.0F;
//            itemRender.zLevel = 0.0F;
//            glEnable(GL_LIGHTING);
//            glEnable(GL_DEPTH_TEST);
//            RenderHelper.enableStandardItemLighting();
//            glEnable(GL_RESCALE_NORMAL);
//        }
//    }
//
//    private void drawGradientString(FontRenderer fontRenderer, String text, int x, int y, int colorStart, int colorEnd) {
//        int length = text.length();
//        for (int i = 0; i < length; i++) {
//            float ratio = (float) i / (float) length;
//            int color = interpolateColor(colorStart, colorEnd, ratio);
//            fontRenderer.drawString(text.substring(i, i + 1), x, y, color);
//            x += fontRenderer.getCharWidth(text.charAt(i));
//        }
//    }
//
//    private int interpolateColor(int startColor, int endColor, float ratio) {
//        int alpha = (int)(((startColor >> 24) & 0xFF) * (1 - ratio) + ((endColor >> 24) & 0xFF) * ratio);
//        int red = (int)(((startColor >> 16) & 0xFF) * (1 - ratio) + ((endColor >> 16) & 0xFF) * ratio);
//        int green = (int)(((startColor >> 8) & 0xFF) * (1 - ratio) + ((endColor >> 8) & 0xFF) * ratio);
//        int blue = (int)((startColor & 0xFF) * (1 - ratio) + (endColor & 0xFF) * ratio);
//        return (alpha << 24) | (red << 16) | (green << 8) | blue;
//    }
//
//    private String removeFormattingCodes(String text) {
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < text.length(); ++i) {
//            char c = text.charAt(i);
//            if (c == '§' && i + 1 < text.length()) {
//                i++;
//            } else {
//                result.append(c);
//            }
//        }
//        return result.toString();
//    }
}