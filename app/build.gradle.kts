plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = AppConfig.App.APPLICATION_ID
    compileSdk = AppConfig.Sdk.COMPILE

    defaultConfig {
        applicationId = AppConfig.App.APPLICATION_ID
        minSdk = AppConfig.Sdk.MIN
        targetSdk = AppConfig.Sdk.TARGET
        versionCode = AppConfig.App.VERSION_CODE
        versionName = AppConfig.App.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.App.javaVersion
        targetCompatibility = AppConfig.App.javaVersion
    }
    kotlinOptions {
        jvmTarget = AppConfig.App.JAVA_VERSION_NAME
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}