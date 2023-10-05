plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.cognisteear"
    compileSdk = 30

    defaultConfig {
        applicationId = "com.example.cognisteear"
        minSdk = 30
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Downgrade dependencies here
    // ...
}
dependencies {
    implementation("androidx.core:core-ktx:1.6.0")  // Downgraded from 1.12.0
    implementation("com.google.android.gms:play-services-wearable:17.1.0")  // Downgraded from 18.1.0
    implementation("androidx.percentlayout:percentlayout:1.0.0")  // Remains the same
    implementation("androidx.legacy:legacy-support-v4:1.0.0")  // Remains the same
    implementation("androidx.recyclerview:recyclerview:1.2.1")  // Downgraded from 1.3.1
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")  // Downgraded from 2.6.2
    implementation("androidx.activity:activity-compose:1.3.1")  // Downgraded from 1.7.2
    
    // Compose dependencies are tricky; you might need to stick to an older version
    // implementation("androidx.compose.ui:ui:1.0.5")
    // implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")
    // implementation("androidx.compose.material:material:1.0.5")
    
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")  // Downgraded
    debugImplementation("androidx.compose.ui:ui-tooling:1.0.5")  // Downgraded
}
