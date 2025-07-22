plugins {
    id("com.android.library")
    //id("com.android.application")  //alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android") //alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose") //version "2.1.21" //don't add version gave error of different version at compilation

    id("com.google.protobuf") version "0.9.4"
    //id("com.android.library") // For reusable UI or logic modules
    //id("com.android.application") // If it's an app module
}

android {
    namespace = "com.example.notes"
    compileSdk = 35

    defaultConfig {
        //applicationId = "com.example.notes"
        minSdk = 24
        targetSdk = 35
        //versionCode = 1
        //versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1") //implementation(libs.androidx.core.ktx)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") //implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.activity:activity-compose:1.9.0") //implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2024.05.00")) //implementation(platform(libs.androidx.compose.bom))


    implementation("androidx.compose.ui:ui")  //implementation(libs.androidx.ui)
    implementation("androidx.compose.ui:ui-graphics")  //implementation(libs.androidx.ui.graphics)
    implementation("androidx.compose.ui:ui-tooling-preview")  //implementation(libs.androidx.ui.tooling.preview)
    implementation("androidx.compose.material3:material3:1.2.1") // implementation(libs.androidx.material3)
    implementation("androidx.navigation:navigation-compose:2.7.7") //implementation(libs.androidx.navigation.compose.android)
    implementation("androidx.navigation:navigation-runtime:2.7.7") //implementation(libs.androidx.navigation.runtime.android)

    implementation("androidx.compose.ui:ui-text")  //implementation(libs.androidx.ui.text.android)
    //implementation(libs.androidx.navigation.compose.jvmstubs)
    // implementation(libs.androidx.material3.jvmstubs)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")


    //Room
    implementation ("androidx.room:room-runtime:2.7.2")
    kapt("androidx.room:room-compiler:2.7.2")
    //annotationProcessor ("androidx.room:room-compiler:2.7.2")

//    kapt("androidx.room:room-compiler:2.7.2")
//    annotationProcessor ("androidx.room:room-compiler:2.7.2")

    //Hilt ViewModel
    implementation("com.google.dagger:hilt-android:2.56")
    kapt("com.google.dagger:hilt-compiler:2.56")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //kapt("com.google.dagger:hilt-compiler:2.56")
    //implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    //DataStore
    implementation("androidx.datastore:datastore:1.1.0") //main datastore
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("com.google.protobuf:protobuf-java:3.25.3")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //for animation in splash screen
    implementation ("com.airbnb.android:lottie-compose:6.1.0")

    //retrofit for APIs
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation ("androidx.compose.material:material:1.6.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
}


protobuf {
    protoc { artifact = "com.google.protobuf:protoc:3.25.3"}
    generateProtoTasks {
        all().forEach { task -> task.builtins {
            maybeCreate("java")
            //.apply { option("lite")}
        }
        }
    }
}