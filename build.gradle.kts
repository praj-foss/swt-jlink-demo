plugins {
    java
    application
    id("com.diffplug.eclipse.mavencentral") version "3.23.0"
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "in.praj.demo.SwtApp"
}

eclipseMavenCentral {
    release("4.16.0") {
        implementationNative("org.eclipse.swt")
        useNativesForRunningPlatform()
    }
}
