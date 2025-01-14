plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.seph.presentation"
    compileSdk = AppConfig.Sdk.COMPILE

    defaultConfig {
        minSdk = AppConfig.Sdk.MIN

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    viewBinding {
        enable = true
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

    //modules
    implementation(project(AppConfig.Modules.DOMAIN))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    implementation(libs.androidx.core.splashscreen)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.core)

    //glide
    implementation(libs.glide)

    //lottie
    implementation(libs.lottie)

    //test
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.jetbrains.kotlin.test)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}