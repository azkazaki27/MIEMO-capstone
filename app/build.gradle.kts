plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.capstone.miemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.capstone.miemo"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.core:core-ktx:1.13.0-alpha02")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation ("androidx.activity:activity-ktx:1.9.0-alpha01")
    implementation ("androidx.savedstate:savedstate-ktx:1.2.1")
    implementation ("androidx.datastore:datastore-preferences:1.1.0-alpha04")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("androidx.camera:camera-camera2:1.3.0-alpha07")
    implementation ("androidx.camera:camera-lifecycle:1.3.0-alpha07")
    implementation ("androidx.camera:camera-view:1.3.0-alpha07")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("androidx.room:room-paging:2.6.0-alpha01")
    implementation ("androidx.room:room-ktx:2.6.0-alpha01")
    implementation ("androidx.paging:paging-runtime-ktx:3.2.0-alpha06")
    implementation ("androidx.arch.core:core-testing:2.2.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation ("com.google.code.gson:gson:2.9.1")

    kapt ("androidx.room:room-compiler:2.5.1")
    testImplementation ("androidx.arch.core:core-testing:2.2.0") // InstantTaskExecutorRule
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1") //TestDispatcher
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:5.3.1")
    testImplementation ("org.mockito:mockito-inline:5.2.0")
    testImplementation ("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.10.0-M1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //implementation("org.tensorflow:tensorflow-lite:0.4.0")
    implementation("org.tensorflow:tensorflow-lite-task-text:0.4.0")
    implementation ("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:0.4.0")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.9.0")

}