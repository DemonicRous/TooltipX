package com.demonicrous.tooltipx;

import com.demonicrous.tooltipx.handlers.ConfigHandler;
import com.demonicrous.tooltipx.utils.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSION)
public class TooltipX {

    public static Logger logger = LogManager.getLogger(Reference.MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        ConfigHandler.load(event);
        ConfigHandler.loadExamples();
        ConfigHandler.getFilesList();
        logger.info("PreInit TooltipX");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Init TooltipX");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("PostInit TooltipX");
    }

}