plugins {
    java
    application
    id("com.diffplug.eclipse.mavencentral") version "3.23.0"
    id("org.moditect.gradleplugin") version "1.0.0-rc1"
}

group   = "in.praj.demo"
version = "0.0.1"

repositories {
    mavenCentral()
}

application {
    mainClassName = "in.praj.demo.SwtApp"
}

val swtBundleId = "org.eclipse.swt"
val moduleDir   = buildDir.resolve("modules")
val imageDir    = buildDir.resolve("runtimeImage")

eclipseMavenCentral {
    // Build version of the Eclipse bundle
    release("4.16.0") {
        implementationNative(swtBundleId)
        useNativesForRunningPlatform()
    }
}

moditect {
    // Create module for current project
    addMainModuleInfo {
        jvmVersion.set("11")
        overwriteExistingFiles.set(true)
        module {
            mainClass = application.mainClassName
            moduleInfo {
                name     = "core"
                requires = swtBundleId
                exports  = "in.praj.demo"
            }
        }
    }

    // Create module for SWT
    addDependenciesModuleInfo {
        outputDirectory.set(moduleDir)
        overwriteExistingFiles.set(true)
        modules {
            val swtDep = configurations["implementation"].dependencies
                    .first { it.name.contains("$swtBundleId.") }
            module {
                artifact(swtDep)
                moduleInfo {
                    name    = swtBundleId
                    exports = "*"
                }
            }
        }
    }

    // Configure custom runtime image
    createRuntimeImage {
        modulePath.set(listOf(moduleDir))
        modules.set(listOf("core"))
        outputDirectory.set(imageDir)
        ignoreSigningInformation.set(true)

        launcher {
            name   = "demo"
            module = "core"
        }
        stripDebug.set(true)
        noManPages.set(true)
        noHeaderFiles.set(true)
        compression.set(2)
    }
}

// Compress custom runtime image into zip
tasks.register<Zip>("imageZip") {
    dependsOn("createRuntimeImage")
    from(fileTree(imageDir))
    destinationDirectory.set(buildDir)
    archiveFileName.set("demo.zip")
}
