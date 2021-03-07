import org.gradle.api.JavaVersion

object BuildTypes {
    const val DEBUG: String = "debug"
    const val RELEASE: String = "release"
}

object SourceSets {
    const val MAIN: String = "main"
    const val TEST: String = "test"
}

object Apps {
    val sourceCompatibility: JavaVersion = JavaVersion.VERSION_1_8
    val targetCompatibility: JavaVersion = JavaVersion.VERSION_1_8
    const val JVM_TARGET: String = "1.8"
    const val COMPILE_SDK: Int = 29
    const val ID: String = "com.project.baetory_rx_validation"
    const val MIN_SDK: Int = 23
    const val TARGET_SDK: Int = 29
    const val BUILD_TOOL_VERSION: String = "29.0.3"
    const val VERSION_CODE: Int = 1
    const val VERSION_NAME: String = "1.0.0"
    const val TEST_INSTRUMENTATION_RUNNER: String = "androidx.test.runner.AndroidJUnitRunner"
}

object Versions {
    const val GRADLE: String = "4.0.2"
    const val KOTLIN: String = "1.3.72"

    const val CONSTRAINT_LAYOUT: String = "2.0.0-beta7"
    const val CORE_KTX: String = "1.3.0"
    const val APP_COMPAT: String = "1.1.0"
    const val ANNOTATION: String = "1.1.0"
    const val MATERIAL: String = "1.3.0-alpha01"
    const val FRAGMENT_KTX = "1.2.5"

    //Rx
    const val RX_JAVA2: String = "2.2.13"
    const val RX_ANDROID: String = "2.1.1"
    const val RX_KOTLIN: String = "2.4.0"
    const val RX_BINDING: String = "2.2.0"
    const val LIFECYCLE_VIEWMODEL_KTX = "2.2.0"

    //Room
    const val ROOM: String = "2.2.5"
    const val ROOM_ARC_PERSISTENCE = "1.1.1"

    //DI
    const val HILT: String = "2.28.3-alpha"
    const val HILT_LIFECYCLE: String = "1.0.0-alpha01"
    const val LOGGER: String = "2.2.0"

    //Test
    const val TEST: String = "1.3.0"
    const val MOKITO: String = "3.2.4"
    const val JUNIT: String = "4.12"
    const val JUNIT_EXT: String = "1.1.1"
    const val ESPRESSO: String = "3.2.0"
}

object Libs {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val ANNOTATION = "androidx.annotation:annotation:${Versions.ANNOTATION}"
    const val RX_JAVA2 = "io.reactivex.rxjava2:rxjava:${Versions.RX_JAVA2}"
    const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.RX_ANDROID}"
    const val RX_KOTLIN = "io.reactivex.rxjava2:rxkotlin:${Versions.RX_KOTLIN}"
    const val RX_BINDING = "com.jakewharton.rxbinding2:rxbinding:${Versions.RX_BINDING}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_RXJAVA2 = "androidx.room:room-rxjava2:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_ARC_PERSISTENCE_RUNTIME = "android.arch.persistence.room:runtime:${Versions.ROOM_ARC_PERSISTENCE}"
    const val ROOM_ARC_PERSISTENCE_COMPILER = "android.arch.persistence.room:compiler:${Versions.ROOM_ARC_PERSISTENCE}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VIEWMODEL_KTX}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_COMMON = "androidx.hilt:hilt-common:${Versions.HILT_LIFECYCLE}"
    const val HILT_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.HILT_LIFECYCLE}"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.HILT_LIFECYCLE}"
    const val LOGGER = "com.orhanobut:logger:${Versions.LOGGER}"
}

object TestLibs {
    const val TEST_RUNNER = "androidx.test:runner:${Versions.TEST}"
    const val TEST_RULES = "androidx.test:rules:${Versions.TEST}"
    const val TEST_CORE = "androidx.test:core:${Versions.TEST}"
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:${Versions.ESPRESSO}"
}