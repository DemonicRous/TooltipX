package com.demonicrous.tooltipx.mixins;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import io.github.tox1cozz.mixinbooterlegacy.IEarlyMixinLoader;

import java.util.*;

import static com.demonicrous.tooltipx.utils.Reference.MOD_ID;

@MCVersion("1.7.10")
public class TooltipXMixinLoaderPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
    @Override
    // Return a list of classes that implements the IClassTransformer interface
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    // Return a class name that implements "ModContainer" for injection into the mod list
    public String getModContainerClass() {
        return null;
    }

    @Override
    // Return the class name of an implementor of "IFMLCallHook", that will be run, in the main thread, to perform any additional setup this coremod may require.
    public String getSetupClass() {
        return null;
    }

    @Override
    // Inject coremod data into this coremod
    public void injectData(Map<String, Object> data) {
    }

    @Override
    // Return an optional access transformer class for this coremod. It will be injected post-deobf so ensure your ATs conform to the new srgnames scheme.
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    // Mixin configurations to be queued and sent to Mixin library.
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixin." + MOD_ID + ".json");
    }
}