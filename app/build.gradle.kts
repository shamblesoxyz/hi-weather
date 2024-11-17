plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.hiweather"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hiweather"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeCompiler {
        enableStrongSkippingMode = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core KTX library for Android
    implementation(libs.androidx.core.ktx)

    // Lifecycle runtime for Android
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose integration for activities
    implementation(libs.androidx.activity.compose)

    // Bill of Materials for Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))

    // Core UI components for Jetpack Compose
    implementation(libs.androidx.ui)

    // Graphics library for Jetpack Compose
    implementation(libs.androidx.ui.graphics)

    // Tooling support for Jetpack Compose previews
    implementation(libs.androidx.ui.tooling.preview)

    // Material Design 3 components for Jetpack Compose
    implementation(libs.androidx.material3)

    // Navigation component for Jetpack Compose
    implementation(libs.androidx.navigation.compose)

    // Google Play Services location APIs
    implementation(libs.play.services.location)

    // JUnit testing framework
    testImplementation(libs.junit)

    // AndroidX JUnit extensions
    androidTestImplementation(libs.androidx.junit)

    // Espresso testing framework for Android
    androidTestImplementation(libs.androidx.espresso.core)

    // Bill of Materials for Jetpack Compose (Android Test)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // JUnit 4 integration for Jetpack Compose tests
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Tooling support for Jetpack Compose (Debug)
    debugImplementation(libs.androidx.ui.tooling)

    // Manifest support for Jetpack Compose tests (Debug)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ConstraintLayout for Jetpack Compose
    implementation(libs.androidx.constraintlayout.compose)

    // Splash screen API for Android
    implementation(libs.androidx.core.splashscreen)

    // Hilt dependency injection library
    implementation(libs.hilt.android)

    // Hilt compiler for Kotlin Symbol Processing (KSP)
    ksp(libs.hilt.compiler)

    // Hilt navigation integration for Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Kotlin coroutines for Android
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel library for Android
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // ViewModel integration for Jetpack Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Lifecycle compiler for Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.lifecycle.compiler)

    // Retrofit library for network requests
    implementation(libs.retrofit)

    // Gson converter for Retrofit
    implementation(libs.converter.gson)

    // Room database runtime library
    implementation(libs.androidx.room.runtime)

    // Room compiler for annotation processing
    annotationProcessor(libs.androidx.room.compiler)

    // Room compiler for Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

    // Room Kotlin extensions
    implementation(libs.androidx.room.ktx)

    // Navigation runtime library for Android
    implementation(libs.androidx.navigation.runtime.ktx)

    // Coil image loading library for Jetpack Compose
    implementation(libs.coil.compose)

    // Lottie animations for Jetpack Compose
    implementation(libs.lottie.compose)
}