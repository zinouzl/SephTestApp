import org.gradle.api.JavaVersion


object AppConfig {

    object Sdk {
        const val COMPILE = 35
        const val TARGET = 35
        const val MIN = 26
    }

    object App {
        const val APPLICATION_ID = "io.seph.test"
        const val VERSION_NAME = "1.0.0"
        const val VERSION_CODE = 1

        val javaVersion = JavaVersion.VERSION_17
        const val JAVA_VERSION_NAME = "17"
    }

}