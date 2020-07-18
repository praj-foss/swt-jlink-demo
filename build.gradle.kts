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

eclipseMavenCentral {
    // Build version of the Eclipse bundle
    release("4.16.0") {
        implementationNative("org.eclipse.swt")
        useNativesForRunningPlatform()
    }
}

val moduleDir = buildDir.resolve("modules")

moditect {
    // Create module for current project
    addMainModuleInfo {
        jvmVersion.set("11")
        overwriteExistingFiles.set(true)
        module {
            mainClass = application.mainClassName
            moduleInfo {
                name     = "core"
                requires = "org.eclipse.swt"
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
                    .first { it.name.contains("org.eclipse.swt.") }
            module {
                artifact(swtDep)
                moduleInfo {
                    name    = "org.eclipse.swt"
                    exports = "*"
                }
            }
        }
    }

    // Configure JLink for custom image
    createRuntimeImage {
        modulePath.set(listOf(moduleDir))
        modules.set(listOf("core"))
        outputDirectory.set(buildDir.resolve("jlinkImage"))
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
