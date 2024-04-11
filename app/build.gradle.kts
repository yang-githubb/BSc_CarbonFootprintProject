plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.carbonfootprint"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.carbonfootprint"
        minSdk = 25
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2")
    implementation("com.google.android.material:material:1.1.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("org.json:json:20210307")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    implementation("com.github.anastr:speedviewlib:1.6.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}