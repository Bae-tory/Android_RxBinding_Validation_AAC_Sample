plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Apps.COMPILE_SDK)
    buildToolsVersion(Apps.BUILD_TOOL_VERSION)

    defaultConfig {
        minSdkVersion(Apps.MIN_SDK)
        targetSdkVersion(Apps.TARGET_SDK)
        versionCode = Apps.VERSION_CODE
        versionName = Apps.VERSION_NAME
        testInstrumentationRunner = Apps.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = Apps.sourceCompatibility
        targetCompatibility = Apps.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = Apps.JVM_TARGET
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Kotlin
    implementation(Libs.KOTLIN)

    //Standard
    implementation(Libs.APP_COMPAT)
    implementation(Libs.CORE_KTX)
    implementation(Libs.CONSTRAINT_LAYOUT)

    // Room
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RXJAVA2)
    kapt(Libs.ROOM_COMPILER)
    implementation(Libs.ROOM_ARC_PERSISTENCE_RUNTIME)
    kapt(Libs.ROOM_ARC_PERSISTENCE_COMPILER)

    //Rx
    implementation(Libs.RX_ANDROID)
    implementation(Libs.RX_KOTLIN)
    implementation(Libs.RX_BINDING)

    //DI
    implementation(Libs.HILT_ANDROID)
    kapt(Libs.HILT_ANDROID_COMPILER)
    implementation(Libs.HILT_VIEWMODEL)
    implementation(Libs.HILT_COMMON)
    kapt(Libs.HILT_COMPILER)
    implementation(Libs.FRAGMENT_KTX)

    //Log
    implementation(Libs.LOGGER)

    //Test
    implementation(TestLibs.JUNIT)
    implementation(TestLibs.JUNIT_EXT)
    implementation(TestLibs.ESPRESSO)
    implementation(TestLibs.TEST_RUNNER)
    implementation(TestLibs.TEST_RULES)
    implementation(TestLibs.TEST_CORE)
    implementation(TestLibs.JUNIT)
    implementation(TestLibs.JUNIT_EXT)
    implementation(TestLibs.ESPRESSO)
    implementation(TestLibs.ESPRESSO_INTENTS)
}