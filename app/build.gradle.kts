plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = AppConfig.App.applicationId
    compileSdk = AppConfig.Sdk.compile

    defaultConfig {
        applicationId = AppConfig.App.applicationId
        minSdk = AppConfig.Sdk.min
        targetSdk = AppConfig.Sdk.target
        versionCode = AppConfig.App.versionCode
        versionName = AppConfig.App.versionName

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
        jvmTarget = AppConfig.App.javaVersionName
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