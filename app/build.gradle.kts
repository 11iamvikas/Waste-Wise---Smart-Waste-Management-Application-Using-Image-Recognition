plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
   // id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")
}


android {
    namespace = "app.waste2wealth.com"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.waste2wealth.com"
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    kapt {
        javacOptions {
            option("-target", "21") // Match JVM target for annotation processing
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        // Use version compatible with Kotlin 1.9.22
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md,DEPENDENCIES,INDEX.LIST}"
        }
    }
}

dependencies {
    //Compose Bom
    implementation(platform(libs.compose.bom))

    //Compose
    implementation("androidx.core:core-ktx:1.13.0")
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation(libs.ui.graphics)
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    //implementation("androidx.compose.material3:material3:1.1.2")
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.material3.android)
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")

    //Navigation Compose
    implementation(libs.navigation.compose)
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    // Lottie Animation
    implementation(libs.lottie)

    //ViewModel Compose
    implementation(libs.viewmodel.compose)

    //SystemUIController
    implementation(libs.system.uicontroller)

    //Coil
    implementation(libs.coilx)

    //Permissions
    implementation(libs.permissions)

    // Location Services
    implementation(libs.location.services)

    //Material Extended
    implementation(libs.material.icons.extended)
    implementation("io.github.vanpra.compose-material-dialogs:core:0.9.0")

    // Camera
    implementation(libs.camera.android)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    implementation(libs.androidx.material3)
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(libs.androidx.datastore.core)
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation(libs.dagger.hilt.navigation)

    //Firebase
    implementation(libs.firebase.bom)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.jet.firestore)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage)

    //Stacked Cards
    implementation(libs.stacked.cards)

    //Ktor-Client
    implementation("io.ktor:ktor-client-core:1.6.3")
    implementation("io.ktor:ktor-client-cio:1.6.3")
    implementation("io.ktor:ktor-client-serialization:1.6.3")
    implementation("io.ktor:ktor-client-websockets:1.6.3")
    implementation("io.ktor:ktor-client-logging:1.6.3")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-client-android:1.6.3")
    //implementation("io.ktor:ktor-client-serialization:1.6.3")
    implementation("io.ktor:ktor-client-logging-jvm:1.6.3")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("io.ktor:ktor-client-gson:1.6.3")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation(libs.camera.core)
    implementation(libs.camera.camera2)

    //Barcode
    implementation(libs.google.mlkit)

    //TFLITE
    implementation("org.tensorflow:tensorflow-lite:2.17.0") // or latest compatible
}
