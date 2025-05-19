package com.demonicrous.tooltipx.configs;

import com.demonicrous.tooltipx.utils.Reference;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class OldConfig {
    private static final String GENERAL = "General";
    private static final String APPEARANCE = "Appearance";
    private static final String POSITION = "Position";
    private static final String EXAMPLES = "Examples";

    // General settings
    public static boolean enableMod = true;
    public static boolean showDebugInfo = false;

    // Appearance settings


    // Position settings
    public static int xOffset = 10;
    public static int yOffset = 10;

    // Examples settings
    public static boolean generateExampleFiles = true;

    public static void load(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), Reference.CONFIG_FILE_PATH), "1.0", true);
        config.load();

        // General category
        config.addCustomCategoryComment(GENERAL, "General settings for TooltipX mod");
        enableMod = config.getBoolean("Enable Mod", GENERAL, true,
                "Enable or disable the mod completely");
        showDebugInfo = config.getBoolean("Show Debug Info", GENERAL, false,
                "Show debug information in tooltips");

        // Appearance category
        config.addCustomCategoryComment(APPEARANCE, "Tooltip appearance settings");


        // Position category
        config.addCustomCategoryComment(POSITION, "Tooltip position settings");
        xOffset = config.getInt("XOffset", POSITION, 10, -1000, 1000,
                "Horizontal offset from mouse position");
        yOffset = config.getInt("YOffset", POSITION, 10, -1000, 1000,
                "Vertical offset from mouse position");

        // Examples category
        config.addCustomCategoryComment(EXAMPLES, "Example files generation settings");
        generateExampleFiles = config.getBoolean("Generate Example Files", "Examples", true, "Generate example config files on first launch"
        );

        if (config.hasChanged()) {
            config.save();
        }

        //ConfigHandler.init(event);

    }
}
