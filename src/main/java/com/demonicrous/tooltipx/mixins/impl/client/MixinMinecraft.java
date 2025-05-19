package com.demonicrous.tooltipx.mixins.impl.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "startGame", at = @At("HEAD"))
    // Test mixin to check the work of the loader
    private void onStartGame(CallbackInfo info) {
        // Log Java version and architecture
        System.out.println("Java: " + System.getProperty("java.version") + " (" + System.getProperty("os.arch") + ")");

        // Log the amount of heap memory used by the JVM in megabytes
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        System.out.println("Memory: " + usedMemory / (1024 * 1024) + "MB/" + totalMemory / (1024 * 1024) + "MB (" + freeMemory / (1024 * 1024) + "MB)");

        // Log operating system information
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));

        // Log the number of available processors (cores)
        System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());

        // Log the maximum amount of memory that the JVM will attempt to use
        System.out.println("Maximum memory: " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");

        // Throw a new RuntimeException and print the stack trace
        new RuntimeException("Hello from stack!").fillInStackTrace().printStackTrace();
    }
}
