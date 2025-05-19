package com.demonicrous.tooltipx.utils.json;

import com.demonicrous.tooltipx.TooltipX;
import com.demonicrous.tooltipx.utils.config.RarityConfig;
import com.demonicrous.tooltipx.utils.config.TabConfig;
import com.demonicrous.tooltipx.utils.config.TooltipConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    public static Map<String, RarityConfig> rarityConfigMap = new HashMap<>();
    public static Map<String, TabConfig> tabConfigMap = new HashMap<>();
    public static Map<String, TooltipConfig> tooltipConfigMap = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void parseConfig(String key, File file) {
        if (!file.getName().endsWith(".json")) {
            return;
        }

        try {
            if (key.contains("tooltip")) {
                TooltipConfig config = parseTooltipConfig(file);
                if (config != null) {
                    tooltipConfigMap.put(FilenameUtils.removeExtension(file.getName()), config);
                    TooltipX.logger.info("Loaded tooltip config: {}", file.getName());
                }
            } else if (key.contains("rarity")) {
                RarityConfig config = parseRarityConfig(file);
                if (config != null) {
                    rarityConfigMap.put(FilenameUtils.removeExtension(file.getName()), config);
                    TooltipX.logger.info("Loaded rarity config: {}", file.getName());
                }
            } else if (key.contains("tab")) {
                TabConfig config = parseTabConfig(file);
                if (config != null) {
                    tabConfigMap.put(FilenameUtils.removeExtension(file.getName()), config);
                    TooltipX.logger.info("Loaded tab config: {}", file.getName());
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            TooltipX.logger.error("Failed to parse config: {}", file.getName());
            e.printStackTrace();
        }
    }

    public static TooltipConfig parseTooltipConfig(File file) throws IOException, JsonSyntaxException {
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, TooltipConfig.class);
        }
    }

    public static RarityConfig parseRarityConfig(File file) throws IOException, JsonSyntaxException {
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, RarityConfig.class);
        }
    }

    public static TabConfig parseTabConfig(File file) throws IOException, JsonSyntaxException {
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, TabConfig.class);
        }
    }
}