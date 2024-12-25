 plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android) // Add Hilt plugin
    kotlin("kapt")
}

android {
    namespace = "com.example.myapplication.callingapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication.callingapi"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true // Ensure this is enabled
    }

    viewBinding {
        enable = true
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

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Hilt dependencies
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.work) // WorkManager with Hilt integration
    kapt(libs.dagger.hiltx.compiler)

    // Retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson) // Corrected alias for the Gson converter
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Gson (if using Gson for JSON serialization)
    implementation(libs.gson)

    // Hawk Library
    implementation(libs.hawk)

    // Work Manager Library
    implementation(libs.workmanager)

    // RXJAVA2 Android
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.adapterRxjava2)

    // Timber JakeWharton Library
    implementation(libs.timber)

    // RecyclerView Library
    implementation(libs.recyclerview)
}