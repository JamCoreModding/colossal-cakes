plugins {
    id("fabric-loom") version "0.12-SNAPSHOT"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.2.0"
    id("io.github.juuxel.loom-quiltflower") version "1.7.+"
    id("io.github.p03w.machete") version "1.+"
    id("org.cadixdev.licenser") version "0.6.+"
}

apply(from = "https://raw.githubusercontent.com/JamCoreModding/Gronk/main/publishing.gradle.kts")
apply(from = "https://raw.githubusercontent.com/JamCoreModding/Gronk/main/misc.gradle.kts")

val mod_version: String by project

group = "io.github.jamalam360"
version = mod_version

repositories {
    val mavenUrls = mapOf(
        Pair("https://maven.terraformersmc.com/releases", listOf("com.terraformersmc", "dev.emi")),
        Pair("https://api.modrinth.com/maven", listOf("maven.modrinth")),
        Pair("https://maven.jamalam.tech/releases", listOf("io.github.jamalam360")),
    )

    for (mavenPair in mavenUrls) {
        maven {
            url = uri(mavenPair.key)
            content {
                for (group in mavenPair.value) {
                    includeGroup(group)
                }
            }
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${libs.versions.minecraft.get()}+build.${libs.versions.mappings.build.get()}:v2"))
    })

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)

    modApi(libs.required.jamlib)

    modImplementation(libs.optional.emi)
    modImplementation(libs.optional.mod.menu)

    modLocalRuntime(libs.runtime.lazy.dfu)
}
