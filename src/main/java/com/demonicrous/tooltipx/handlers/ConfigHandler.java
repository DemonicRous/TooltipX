package com.demonicrous.tooltipx.handlers;

import com.demonicrous.tooltipx.TooltipX;
import com.demonicrous.tooltipx.utils.Reference;
import com.demonicrous.tooltipx.utils.json.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import com.demonicrous.tooltipx.utils.config.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.demonicrous.tooltipx.enums.EnumConfigCategory.*;

public class ConfigHandler {

    private static File CONFIG_DIR = null;
    private static boolean examplesGenerated = false;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // General settings
    public static boolean enableMod = true;
    public static boolean showDebugInfo = false;

    // Appearance settings
    // public static String primaryColor = "#FF0000";

    // Position settings
    public static int xOffset = 10;
    public static int yOffset = 10;

    // Examples settings
    public static boolean generateExampleFiles = true;

    public static void load(FMLPreInitializationEvent event) {

        CONFIG_DIR = event.getModConfigurationDirectory();

        Configuration config = new Configuration(new File(CONFIG_DIR, Reference.CONFIG_FILE_PATH), Reference.CONFIG_VERSION, true);
        config.load();


        // General category
        config.addCustomCategoryComment(GENERAL.getName(), GENERAL.getComment());
        enableMod = config.getBoolean("Enable Mod", GENERAL.getName(), true,
                "Enable or disable the mod completely");
        showDebugInfo = config.getBoolean("Show Debug Info", GENERAL.getName(), false,
                "Show debug information in tooltips");


        // Appearance category
        config.addCustomCategoryComment(APPEARANCE.getName(), APPEARANCE.getComment());

        // Appearance child categories
        // String appearanceColors = APPEARANCE.getName() + ".colors"; // "appearance.colors"
        // config.addCustomCategoryComment(appearanceColors, "Color settings");
        // primaryColor = config.getString("Primary Color", appearanceColors, "#FF0000",
        //        "Primary color used in the mod");

        // Position category
        config.addCustomCategoryComment(POSITION.getName(), POSITION.getComment());


        // Examples category
        config.addCustomCategoryComment(EXAMPLES.getName(), EXAMPLES.getComment());
        generateExampleFiles = config.getBoolean("Generate Example Files", "Examples", true, "Generate example config files on first launch");


        if (config.hasChanged()) {
            config.save();
        }

    }

    public static void loadExamples() {

        if (generateExampleFiles && !examplesGenerated) {
            createCategory(Reference.CONFIG_FOLDER_RARITY_PATH, getRarityDefaults());
            createCategory(Reference.CONFIG_FOLDER_TABS_PATH, getTabsDefaults());
            createCategory(Reference.CONFIG_FOLDER_TOOLTIPS_PATH, getTooltipsDefaults());
            examplesGenerated = true;
        }

    }

    private static void createCategory(String categoryPath, Map<String, Object> files) {
        File categoryDir = null;
        try {
            categoryDir = new File(CONFIG_DIR + "/" + categoryPath);
            if (!categoryDir.exists() && !categoryDir.mkdirs()) {
                TooltipX.logger.error("Failed to create directory: {}", categoryPath);
                return;
            }

            for (Map.Entry<String, Object> entry : files.entrySet()) {
                File configFile = new File(categoryDir, entry.getKey());
                if (!configFile.exists()) {
                    try (FileWriter writer = new FileWriter(configFile)) {
                        GSON.toJson(entry.getValue(), writer);
                    }
                }
            }
        } catch (Exception e) {
            TooltipX.logger.error("Error creating config category: {}", categoryDir);
            e.printStackTrace();
        }
    }

    private static Map<String, Object> getRarityDefaults() {
        Map<String, Object> files = new HashMap<>();
        files.put("common.json", RarityConfig.defaultExample(true, "common", "Rarity: common", "border"));
        files.put("uncommon.json", RarityConfig.defaultExample(true, "uncommon", "Rarity: uncommon", "border"));
        files.put("rare.json", RarityConfig.defaultExample(true, "rare", "Rarity: rare", "border"));
        files.put("epic.json", RarityConfig.defaultExample(true, "epic", "Rarity: epic", "glow"));
        return files;
    }

    private static Map<String, Object> getTabsDefaults() {
        Map<String, Object> files = new HashMap<>();
        files.put("tools.json", TabConfig.defaultExample(true, "tools", "Tab: Tools", "border", "itemGroup.tools", "minecraft:coal"));
        files.put("building_blocks.json", TabConfig.defaultExample(true, "building_blocks", "Tab: Building Blocks", "border", "itemGroup.buildingBlocks", "minecraft:coal"));
        return files;
    }

    private static Map<String, Object> getTooltipsDefaults() {
        Map<String, Object> files = new HashMap<>();
        files.put("example.json", TooltipConfig.defaultExample(
                true,
                "example",
                "Example tooltip",
                "border",
                Collections.singletonList("minecraft:stick")
        ));
        return files;
    }

    public static void getFilesList() throws IOException {
        Map<String, Path> dirs = new HashMap<>();
        dirs.put("rarity", Paths.get(CONFIG_DIR + "/" + Reference.CONFIG_FOLDER_RARITY_PATH));
        dirs.put("tabs", Paths.get(CONFIG_DIR + "/" + Reference.CONFIG_FOLDER_TABS_PATH));
        dirs.put("tooltips", Paths.get(CONFIG_DIR + "/" + Reference.CONFIG_FOLDER_TOOLTIPS_PATH));

        for (Map.Entry<String, Path> entry : dirs.entrySet()) {
            String key = entry.getKey();
            Path dir = entry.getValue();
            TooltipX.logger.info("Key: {}, Dir: {}", key, dir);
            if (!Files.exists(dir)) {
                continue;
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {
                        TooltipX.logger.info(file.getFileName());
                        JsonParser.parseConfig(key, file.toFile());

                        // Получаем имя файла без расширения
                        String fileName = FilenameUtils.removeExtension(file.getFileName().toString());

                        // В зависимости от категории получаем нужную конфигурацию
                        if (key.equals("tooltips")) {
                            TooltipConfig config = JsonParser.tooltipConfigMap.get(fileName);
                            TooltipX.logger.info(config.getName());
                        } else if (key.equals("rarity")) {
                            RarityConfig config = JsonParser.rarityConfigMap.get(fileName);
                            TooltipX.logger.info(config.getName());
                        } else if (key.equals("tabs")) {
                            TabConfig config = JsonParser.tabConfigMap.get(fileName);
                            TooltipX.logger.info(config.getName());
                        }
                    }
                }
            }
        }
    }

}
