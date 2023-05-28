plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    // kotlinx serialization
    kotlin("plugin.serialization") version "1.8.10"
}

android {
    namespace = "woowacourse.movie"
    compileSdk = 33

    defaultConfig {
        applicationId = "woowacourse.movie"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    kotlinOptions {
        jvmTarget = "11"
    }
    dataBinding {
        enable = true
    }
    // mockk 충돌문제
    packagingOptions {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }
    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("androidx.test:core:1.5.0")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.0")

    // Mockk
    testImplementation("io.mockk:mockk:1.13.5")
    // Mockk for android test
    androidTestImplementation("io.mockk:mockk-android:1.13.5")
    androidTestImplementation("io.mockk:mockk-agent:1.13.5")

    // BottomSheet
    implementation("com.google.android.material:material:1.5.0-alpha02")

    // kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    // fragment Test
    debugImplementation("androidx.fragment:fragment-ktx:1.6.0-beta01")
    debugImplementation("androidx.fragment:fragment-testing:1.5.7")
}
