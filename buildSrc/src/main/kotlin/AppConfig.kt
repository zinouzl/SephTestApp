import org.gradle.api.JavaVersion


object AppConfig {

    object Sdk {
        const val compile = 35
        const val target = 35
        const val min = 26
    }

    object App {
        const val applicationId = "io.seph.test"
        const val versionName = "1.0.0"
        const val versionCode = 1

        val javaVersion = JavaVersion.VERSION_17
        val javaVersionName = "17"
    }

}