import net.minecraftforge.gradle.delayed.DelayedBase
import net.minecraftforge.gradle.tasks.user.reobf.ReobfTask
import net.minecraftforge.gradle.user.patch.ForgeUserPlugin

// Mod Information
val modId: String by project
val modName: String by project
val modDescription: String by project
val modVersion: String by project
val mcVersion: String by project
val modAuthors: String by project
val modCredits: String by project
val modIcon: String by project

// Information for gradle and srg
val modArchivesBaseName: String by project
val modGroup: String by project
val forgeVersion: String by project
val minecraftDir: String by project

// Configure buildscript with dependencies
buildscript {
    dependencies {
        classpath("com.anatawa12.forge:ForgeGradle:1.2-1.1.+") {
            isChanging = true
        }
    }
}

// Setup libraries
repositories {
    maven { url = uri("https://repo.spongepowered.org/maven") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.tox1cozZ:mixin-booter-legacy:1.2.1")
    annotationProcessor("com.github.tox1cozZ:mixin-booter-legacy:1.2.1:processor")
}

repositories {
    flatDir {
        dirs("libs")
    }
}

// Apply plugins
apply(plugin = "forge")
apply(plugin = "java")
apply(plugin = "idea")

plugins {
    idea
    java
    id("forge")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

// Extra
version = modVersion
group = modGroup

minecraft {
    version = "$mcVersion-$forgeVersion-$mcVersion"
    runDir = minecraftDir
    mappings = "stable_12"
}

idea.module.inheritOutputDirs = true
sourceSets {
    main {
        java.srcDir("src/main/java")
    }
}

tasks {
    jar.configure {
        manifest {
            attributes["FMLAT"] = "${modId}_at.cfg"
            attributes["FMLCorePlugin"] = "${modGroup}.${modId}.mixins.${modName}MixinLoaderPlugin"
            attributes["FMLCorePluginContainsFMLMod"] = "true"
        }
    }

    findByPath("prepareKotlinBuildScriptModel")?.dependsOn("copySrgs", "mixin")

    register<Copy>("copySrgs") {
        dependsOn("genSrgs")
        from(DelayedBase.resolve("{SRG_DIR}", project, ForgeUserPlugin()))
        include("**/*.srg")
        into(layout.buildDirectory.file("srgs"))
    }

    register<MixinTask>("mixin") {
        // Set custom refmap name. By default, using project name.
        mixinRefMapName.set("mixin.$modId.refmap.json")
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"

        dependsOn(getByName<Copy>("copySrgs"), getByName<MixinTask>("mixin"))
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from(sourceSets["main"].resources.srcDirs) {
            include("mcmod.info")
            expand(
                "modId" to modId,
                "modName" to modName,
                "modDescription" to modDescription,
                "modVersion" to modVersion,
                "mcVersion" to mcVersion,
                "modAuthors" to modAuthors,
                "modCredits" to modCredits,
                "modIcon" to modIcon
            )
        }

        from(sourceSets["main"].resources.srcDirs) {
            exclude("mcmod.info")
        }
    }
}

afterEvaluate {
    val refmap = tasks.getByName<MixinTask>("mixin").mixinRefMapName.get()
    tasks.processResources {
        inputs.property("mixin_refmap", refmap)
        from(sourceSets["main"].resources.srcDirs) {
            include("*.json")
            expand(mapOf("mixin_refmap" to refmap))
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}

abstract class MixinTask : DefaultTask() {

    // Abstract property mixinRefMapName
    @get:Input
    abstract val mixinRefMapName: Property<String>

    // Late-initialized variables mixinSrg and mixinRefMap
    @Internal
    lateinit var mixinSrg: File

    @Internal
    lateinit var mixinRefMap: File

    // Initialization of the mixinRefMapName property
    init {
        mixinRefMapName.convention("mixin.${project.name.replace(Regex("[_\\-.]"), "").lowercase()}.refmap.json")
    }

    // Action to be performed when the task is run
    @TaskAction
    fun action() {
        val mixinDir = createMixinDirectory()
        val srgFile = File(project.buildDir, "srgs/mcp-srg.srg")

        mixinSrg = createMixinSrgFile(mixinDir)
        mixinRefMap = File(mixinDir, mixinRefMapName.get())

        addExtraSrgFileToReobfTask()
        addCompilerArgsToJavaCompile(srgFile)
        addMixinRefMapToJar()
    }

    // Create a directory for mixin
    private fun createMixinDirectory(): File {
        return File(project.buildDir, "mixins").apply {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    // Create a mixinSrg file
    private fun createMixinSrgFile(mixinDir: File): File {
        return File(mixinDir, "${mixinRefMapName.get()}.srg").apply {
            if (!exists()) {
                createNewFile()
            }
        }
    }

    // Add an extra Srg file to the Reobf task
    private fun addExtraSrgFileToReobfTask() {
        project.tasks.getByName<ReobfTask>("reobf") {
            addExtraSrgFile(mixinSrg)
        }
    }

    // Add compiler arguments to the JavaCompile task
    private fun addCompilerArgsToJavaCompile(srgFile: File) {
        project.tasks.getByName<JavaCompile>("compileJava") {
            options.compilerArgs.addAll(listOf(
                "-Xlint:-processing",
                "-AoutSrgFile=${mixinSrg.canonicalPath}",
                "-AoutRefMapFile=${mixinRefMap.canonicalPath}",
                "-AreobfSrgFile=${srgFile.canonicalPath}"
            ))
        }
    }

    // Add mixinRefMap to the Jar task
    private fun addMixinRefMapToJar() {
        project.tasks.getByName<Jar>("jar") {
            from(mixinRefMap)
        }
    }
}
