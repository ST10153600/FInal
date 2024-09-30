plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.exams"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exams"
        minSdk = 34
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase dependencies for authentication and database
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    // Unit testing dependencies
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // JUnit for Android tests
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose testing
    androidTestImplementation(libs.androidx.ui.test.junit4) // Jetpack Compose UI testing

    // Debug dependencies for UI tool previews and testing
    debugImplementation(libs.androidx.ui.tooling) // UI Tooling for preview
    debugImplementation(libs.androidx.ui.test.manifest) // UI Test Manifest

      // Mockito dependencies for mocking
    testImplementation("org.mockito:mockito-core:5.4.0") // Mockito core library
    testImplementation("org.mockito:mockito-inline:5.4.0") // Mockito for mocking final classes and methods
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0") // Mockito Kotlin extensions
}