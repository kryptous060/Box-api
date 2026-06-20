/*
 * Copyright 2025 Google LLC
 */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.hilt.application)
    // REMOVED: alias(libs.plugins.oss.licenses)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.google.ai.edge.gallery"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.box.gallery"
        minSdk = 35
        targetSdk = 36
        versionCode = 26
        versionName = "1.0.12"
        manifestPlaceholders["appAuthRedirectScheme"] = "REPLACE_WITH_YOUR_REDIRECT_SCHEME_IN_HUGGINGFACE_APP"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/io.netty.versions.properties"
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
    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.reflect)
    implementation(libs.material.icon.extended)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.datastore)
    implementation(libs.com.google.code.gson)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.webkit)
    implementation(libs.litertlm)
    implementation(libs.commonmark)
    implementation(libs.richtext)
    implementation(libs.tflite)
    implementation(libs.tflite.gpu)
    implementation(libs.tflite.support)
    implementation(libs.camerax.core)
    implementation(libs.camerax.camera2)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.view)
    implementation(libs.openid.appauth)
    implementation(libs.androidx.splashscreen)
    implementation(libs.protobuf.javalite)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    // REMOVED: implementation(libs.play.services.oss.licenses)
    
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    
    implementation(libs.androidx.exifinterface)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.sqlcipher.android)
    implementation(libs.sqlite)
    implementation(project(":smollm"))
    implementation(project(":stablediffusion"))
    implementation(project(":whisper"))
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    implementation(libs.androidx.material3.window.size)
    
    implementation("io.ktor:ktor-server-core:2.3.12")
    implementation("io.ktor:ktor-server-netty:2.3.12")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")

    ksp(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.mlkit.genai.prompt)
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:4.26.1" }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") { option("lite") }
            }
        }
    }
}

// REMOVED: The tasks.configureEach block for OssLicensesTask

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}
