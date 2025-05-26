import android.databinding.tool.ext.classSpec

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pr21m"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pr21m"
        minSdk = 26
        targetSdk = 34
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

    packagingOptions {
        exclude ("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE.md")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/LICENSE")
    }
}

dependencies {
    implementation ("com.sun.mail:android-mail:1.6.7")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}