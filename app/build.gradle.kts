plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.adminside"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.adminside"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

    dependencies {
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.constraintlayout)
        implementation(libs.navigation.fragment)
        implementation(libs.navigation.ui)
        implementation("com.android.volley:volley:1.2.1")
        implementation(libs.recyclerview)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        implementation("com.squareup.okhttp3:okhttp:4.9.3")
        implementation("com.google.firebase:firebase-database:20.2.2")
        implementation("com.google.firebase:firebase-messaging:23.1.1")
    }
