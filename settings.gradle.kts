// Project name
rootProject.name = "TooltipX"

// Configure plugin management
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven("https://maven.minecraftforge.net") {
            name = "forge"
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "sonatype"
        }
        maven("https://repo.spongepowered.org/maven") {
            name = "sponge"
        }
        maven("https://jitpack.io") {
            name = "jitpack"
        }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.name == "forge") {
                useModule("com.anatawa12.forge:ForgeGradle:1.2-1.1.+")
            }
        }
    }
}