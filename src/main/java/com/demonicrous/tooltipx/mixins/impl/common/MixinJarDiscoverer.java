package com.demonicrous.tooltipx.mixins.impl.common;

import cpw.mods.fml.common.discovery.JarDiscoverer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = JarDiscoverer.class, remap = false)
public class MixinJarDiscoverer {
    @Redirect(method = {"discover", "findClassesASM"}, at = @At(value = "INVOKE", target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z"))
    private boolean shouldSkip(String entry, String originalPattern) {
        // Do not try to look for mods in the Java 9 version specific directory (kotlin includes one).
        // Forge cannot read those and LaunchWrapper cannot load them, so there's no point trying and spamming
        // the log with warnings about them being "corrupt" (seems like forge never considered forwards-compatibility).
        // If the entry starts with "META-INF/versions/9/" or the original pattern, the method will return true and skip the entry.
        return entry.startsWith("META-INF/versions/9/") || entry.startsWith(originalPattern);
    }
}